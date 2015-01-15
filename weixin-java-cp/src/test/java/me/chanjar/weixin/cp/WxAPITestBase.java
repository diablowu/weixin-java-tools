package me.chanjar.weixin.cp;

import me.chanjar.weixin.common.util.AccessTokenHolder;
import me.chanjar.weixin.common.util.storage.SimpleDataStorage;
import me.chanjar.weixin.common.util.storage.StorageStrategy;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpNAServiceImpl;
import me.chanjar.weixin.cp.api.WxCpReloadMemConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.util.FileSimpleDataStrategy;

import org.junit.Before;

public class WxAPITestBase {
    protected WxCpService wxCpService;

    protected WxCpConfigStorage storage = null;

    @Before
    public void init() {
        storage = WxCpReloadMemConfigStorage.get();

        String filePath = System.getProperty("user.home") + "/.access_token";
        StorageStrategy ss = new FileSimpleDataStrategy(filePath);
        SimpleDataStorage sds = new SimpleDataStorage(ss);
        sds.loadData(false);
        AccessTokenHolder.load(sds.getData(), 7200);

        wxCpService = new WxCpNAServiceImpl();
        wxCpService.setWxCpConfigStorage(storage);
    }

}
