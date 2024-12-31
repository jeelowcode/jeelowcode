
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
package com.jeelowcode.framework.utils.component.mybatis;

import com.jeelowcode.framework.utils.utils.FuncBase;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

public class CustomWrapper extends MapWrapper {

    public CustomWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        if(FuncBase.isEmpty(name)){
            return name;
        }
        String nameUpper = name.toUpperCase();
        if(FuncBase.equals(name,nameUpper)){//将全大写转为小写
            return name.toLowerCase();
        }
        return name;
    }
}
