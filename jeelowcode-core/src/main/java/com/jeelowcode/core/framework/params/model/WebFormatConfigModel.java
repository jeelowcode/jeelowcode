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
package com.jeelowcode.core.framework.params.model;

import lombok.Data;

import java.util.List;


@Data
public class WebFormatConfigModel {
    private String formatType;
    private Json formatJson;


    @Data
    public static class Json{
        private SqlModel sql;
        private JavaModel java;
        private String fun;
    }



    @Data
    public static class SqlModel{
        private String valueType;
        private String group;
        private String custom;
    }

    @Data
    public static class JavaModel{
        private String valueType;
        private List<JavaGroup> group;
        private JavaCustom custom;//自定义
    }
    @Data
    public static class JavaGroup{
        private String value;
        private String type;
    }

    @Data
    public static class JavaCustom{
        private String javaPath;
    }
}
