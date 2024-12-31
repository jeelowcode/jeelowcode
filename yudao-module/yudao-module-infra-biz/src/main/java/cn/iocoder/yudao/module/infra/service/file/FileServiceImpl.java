package cn.iocoder.yudao.module.infra.service.file;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.io.FileUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.file.core.client.FileClient;
import cn.iocoder.yudao.framework.file.core.client.s3.FilePresignedUrlRespDTO;
import cn.iocoder.yudao.framework.file.core.utils.FileTypeUtils;
import cn.iocoder.yudao.module.infra.controller.admin.file.vo.file.FileCreateReqVO;
import cn.iocoder.yudao.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import cn.iocoder.yudao.module.infra.controller.admin.file.vo.file.FilePresignedUrlRespVO;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;
import cn.iocoder.yudao.module.infra.dal.mysql.file.FileMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.FILE_NOT_EXISTS;

/**
 * 文件 Service 实现类
 *
 * @author 芋道源码
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileConfigService fileConfigService;

    @Resource
    private FileMapper fileMapper;

    @Override
    public PageResult<FileDO> getFilePage(FilePageReqVO pageReqVO) {
        return fileMapper.selectPage(pageReqVO);
    }

    @Override
    @SneakyThrows
    public String createFile(String name, String path, byte[] content) {
        // 计算默认的 path 名
        String type = FileTypeUtils.getMineType(content, name);
        if (StrUtil.isEmpty(path)) {
            path = FileUtils.generatePath(content, name);
        }
        // 如果 name 为空，则使用 path 填充
        if (StrUtil.isEmpty(name)) {
            name = path;
        }

        // 上传到文件存储器
        FileClient client = fileConfigService.getMasterFileClient();
        Assert.notNull(client, "客户端(master) 不能为空");
        String url = client.upload(content, path, type);

        // 保存到数据库
        FileDO file = new FileDO();
        file.setConfigId(client.getId());
        file.setName(name);
        file.setPath(path);
        file.setUrl(url);
        file.setType(type);
        file.setSize(content.length);
        fileMapper.insert(file);
        return url;
    }

    @Override
    public Long createFile(FileCreateReqVO createReqVO) {
        FileDO file = BeanUtils.toBean(createReqVO, FileDO.class);
        fileMapper.insert(file);
        return file.getId();
    }

    @Override
    public void deleteFile(Long id) throws Exception {
        // 校验存在
        FileDO file = validateFileExists(id);

        // 从文件存储器中删除
        FileClient client = fileConfigService.getFileClient(file.getConfigId());
        Assert.notNull(client, "客户端({}) 不能为空", file.getConfigId());
        client.delete(file.getPath());

        // 删除记录
        fileMapper.deleteById(id);
    }

    private FileDO validateFileExists(Long id) {
        FileDO fileDO = fileMapper.selectById(id);
        if (fileDO == null) {
            throw exception(FILE_NOT_EXISTS);
        }
        return fileDO;
    }

    @Override
    public byte[] getFileContent(Long configId, String path) throws Exception {
        FileClient client = fileConfigService.getFileClient(configId);
        Assert.notNull(client, "客户端({}) 不能为空", configId);
        return client.getContent(path);
    }

    @Override
    public FilePresignedUrlRespVO getFilePresignedUrl(String path) throws Exception {
        FileClient fileClient = fileConfigService.getMasterFileClient();
        FilePresignedUrlRespDTO presignedObjectUrl = fileClient.getPresignedObjectUrl(path);
        return BeanUtils.toBean(presignedObjectUrl, FilePresignedUrlRespVO.class,
                object -> object.setConfigId(fileClient.getId()));
    }

    //获取当天唯一名称
    @Override
    public String getUniqueFileName(String fileName,String path){
        LambdaQueryWrapper<FileDO> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(FileDO::getPath,path);
        List<FileDO> fileDOList = fileMapper.selectList(wrapper);
        if(fileDOList==null || fileDOList.size()==0){//当天不存在同名，可用
            return fileName;
        }

        //存在同名，找出全部
        path=path.substring(0,path.lastIndexOf("."));

        LambdaQueryWrapper<FileDO> likeWrapper=new LambdaQueryWrapper<>();
        likeWrapper.likeRight(FileDO::getPath,path);
        List<FileDO> list = fileMapper.selectList(likeWrapper);
        int fileIndex=1;//序号
        for(FileDO tmpFileDO:list){
            String name = tmpFileDO.getName();
            if(name.equals(fileName)){
                continue;
            }else if(!name.contains("_")){
                continue;
            }else if(!name.contains(".")){
                continue;
            }
            String substring = name.substring(name.lastIndexOf("_")+1, name.lastIndexOf("."));
            int nowIndex = 0;
            try {
                nowIndex = Integer.parseInt(substring);
            } catch (NumberFormatException ignored){}
            if(nowIndex>fileIndex){
                fileIndex = nowIndex;
            }
        }
        fileIndex++;


        String left = fileName.substring(0,fileName.lastIndexOf("."));
        String right = fileName.substring(fileName.lastIndexOf(".")+1);

        String newFileName=left+"_"+fileIndex+"."+right;

        return newFileName;

    }

/*    public static void main(String[] args) {
        String str="微信图片_20230905094700_1.png";
        String left = str.substring(0,str.lastIndexOf("."));
        String right = str.substring(str.lastIndexOf(".")+1);
        System.out.println(left+"."+right);

    }*/

}
