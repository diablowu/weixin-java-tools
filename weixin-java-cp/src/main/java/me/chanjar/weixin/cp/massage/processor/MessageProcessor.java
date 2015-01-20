package me.chanjar.weixin.cp.massage.processor;

import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

public interface MessageProcessor {

    public WxCpXmlOutMessage process(final WxCpMessage message);
}
