package me.chanjar.weixin.cp.api;

import java.io.File;

import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutImageMessage;
import me.chanjar.weixin.cp.massage.WxCpMessageProcessor;

public class TestWxCpFileMessageProcessor implements WxCpMessageProcessor {

    private File ruleFile;
    
    
    public TestWxCpFileMessageProcessor(String ruleFilePath){
        this.ruleFile = new File(ruleFilePath);
    }
    
    @Override
    public WxCpXmlOutImageMessage process(WxCpMessage message) {
        return null;
    }

}
