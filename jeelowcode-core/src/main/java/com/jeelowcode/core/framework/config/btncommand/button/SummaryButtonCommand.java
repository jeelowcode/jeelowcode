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
package com.jeelowcode.core.framework.config.btncommand.button;

import com.jeelowcode.core.framework.config.btncommand.receiver.IButtonCommandReceiver;

import java.util.Map;

/**
 * 具体命令-统计按钮命令
 */
public class SummaryButtonCommand implements IButtonCommand<Map> {
    //命令执行者
    private IButtonCommandReceiver<Map> recevier;

    private String name = "统计";

    /**
     * 绑定执行者
     *
     * @param recevier 执行者
     */
    public SummaryButtonCommand(IButtonCommandReceiver recevier) {
        this.recevier = recevier;
    }

    /**
     * 执行命令
     *
     * @return
     */
    @Override
    public Map execute() {
        return recevier.receiver();
    }


}
