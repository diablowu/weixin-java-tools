package me.chanjar.weixin.cp.massage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class FileMessageHandlerFactory {
    
    private File configFile;
    
    public FileMessageHandlerFactory(String path){
        this.configFile = new File(path);
    }

    

}
