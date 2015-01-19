package me.chanjar.weixin.cp.massage;

import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutImageMessage;

public interface WxCpMessageProcessor {

    
    public WxCpXmlOutImageMessage process(final WxCpMessage message);
}
