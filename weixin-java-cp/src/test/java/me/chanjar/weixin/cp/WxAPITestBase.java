package me.chanjar.weixin.cp;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpReloadMemConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;

import org.junit.Before;

public class WxAPITestBase {
    protected WxCpService wxCpService;
    
    protected WxCpConfigStorage storage =  null;
    
    
    @Before
    public void init(){
        storage = WxCpReloadMemConfigStorage.get();
        WxAccessToken token = AccessTokenTestHolder.getToken();
        storage.updateAccessToken(token);
        wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(storage);
    }
    
    

}
