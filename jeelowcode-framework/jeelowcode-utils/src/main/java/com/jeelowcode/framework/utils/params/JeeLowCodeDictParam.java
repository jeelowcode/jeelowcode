/*					
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.utils.params;

import java.util.List;
import java.util.Map;

public class JeeLowCodeDictParam {

    //字典编码
    List<String> dictCodeList;

    private Map<String,DictCodeDataParam> paramMap;

    public static class DictCodeDataParam{

        //字段数据key
        List<String> dictDataLabelList;

        //字段数据value
        List<String> dictDataValueList;


        public List<String> getDictDataLabelList() {
            return dictDataLabelList;
        }

        public void setDictDataLabelList(List<String> dictDataLabelList) {
            this.dictDataLabelList = dictDataLabelList;
        }

        public List<String> getDictDataValueList() {
            return dictDataValueList;
        }

        public void setDictDataValueList(List<String> dictDataValueList) {
            this.dictDataValueList = dictDataValueList;
        }
    }

    public List<String> getDictCodeList() {
        return dictCodeList;
    }

    public void setDictCodeList(List<String> dictCodeList) {
        this.dictCodeList = dictCodeList;
    }

    public Map<String, DictCodeDataParam> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, DictCodeDataParam> paramMap) {
        this.paramMap = paramMap;
    }
}
