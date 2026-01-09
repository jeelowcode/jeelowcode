package com.jeelowcode.service.infra.api.impl;

import com.jeelowcode.service.infra.api.FileApi;
import com.jeelowcode.service.infra.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 文件 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FileApiImpl implements FileApi {

    @Resource
    private IFileService fileService;

    @Override
    public String createFile(String name, String path, byte[] content) {
        return fileService.createFile(name, path, content);
    }

}
