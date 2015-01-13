package me.chanjar.weixin.cp.api;

import org.junit.Before;

import me.chanjar.weixin.common.exception.WxErrorException;

public class WxAPIBaseTest {
    protected WxCpServiceImpl wxService;
    
    protected WxCpConfigStorage storage =  null;
    
    @Before
    protected void init(){
        storage = WxCpReloadMemConfigStorage.get();
    }
    
    

}
