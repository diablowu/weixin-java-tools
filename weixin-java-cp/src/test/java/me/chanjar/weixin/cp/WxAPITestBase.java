package me.chanjar.weixin.cp;

import me.chanjar.weixin.common.util.AccessTokenHolder;
import me.chanjar.weixin.common.util.storage.SimpleDataStorage;
import me.chanjar.weixin.common.util.storage.StorageStrategy;
import me.chanjar.weixin.cp.api.WxCpConfig;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpSpringServiceImpl;
import me.chanjar.weixin.cp.util.FileSimpleDataStrategy;

import org.junit.Before;

public class WxAPITestBase {
    protected WxCpService wxCpService;

    protected WxCpConfig config = null;

    @Before
    public void init() {
//        storage = WxCpReloadMemConfigStorage.get();
        WxCpConfig config = WxCpConfig.loadXml("");
        String filePath = System.getProperty("user.home") + "/.access_token";
        StorageStrategy ss = new FileSimpleDataStrategy(filePath, config);
        SimpleDataStorage sds = new SimpleDataStorage(ss);
        sds.loadData(false);
        AccessTokenHolder.load(sds.getData(), 7200);
        
        config = new WxCpConfig();

        wxCpService = new WxCpSpringServiceImpl();
        wxCpService.setWxCpConfig(config);
    }

}
