package me.chanjar.weixin.cp.massage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class FileMessageHandlerFactory implements MessageHandlerFactory {
    
    private File configFile;
    
    public FileMessageHandlerFactory(String path){
        this.configFile = new File(path);
    }

    
    @Override
    public void init() throws Exception {
        FileInputStream fis = new FileInputStream(configFile);
        List<String> lines = IOUtils.readLines(fis, Charset.forName("UTF-8"));
        
        
    }

    @Override
    public WxCpMessageProcessor findHandler(MatchRule rule) {
        // TODO Auto-generated method stub
        return null;
    }

}
