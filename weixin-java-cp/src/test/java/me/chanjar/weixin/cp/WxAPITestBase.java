package me.chanjar.weixin.cp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import me.chanjar.weixin.common.util.AccessTokenHolder;
import me.chanjar.weixin.common.util.storage.SimpleDataStorage;
import me.chanjar.weixin.common.util.storage.StorageStrategy;
import me.chanjar.weixin.cp.api.WxCpConfig;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpSpringServiceImpl;
import me.chanjar.weixin.cp.util.FileSimpleDataStrategy;

import org.apache.commons.io.IOUtils;
import org.junit.Before;

public class WxAPITestBase {
    protected WxCpService wxCpService;

    protected WxCpConfig config = null;

    @Before
    public void init() {
        config = new WxCpConfig("x:/config.xml");
        
        System.out.println(config.toString());
        
        String filePath = System.getProperty("user.home") + "/.access_token";
        StorageStrategy ss = new FileSimpleDataStrategy(filePath, config);
        SimpleDataStorage sds = new SimpleDataStorage(ss);
        sds.loadData(false);
        AccessTokenHolder.load(sds.getData(), 7200);
        

        wxCpService = new WxCpSpringServiceImpl();
        wxCpService.setWxCpConfig(config);
    }
    
    
    protected String loadFile(String path){
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(path);
            List<String> lines = IOUtils.readLines(fis);
            fis.close();
            for (String string : lines) {
                sb.append(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
