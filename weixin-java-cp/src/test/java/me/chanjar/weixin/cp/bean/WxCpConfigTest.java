package me.chanjar.weixin.cp.bean;

import me.chanjar.weixin.cp.api.WxCpConfig;

import org.junit.Assert;
import org.junit.Test;

public class WxCpConfigTest {

    
    
    @Test
    public void testLoad(){
        WxCpConfig cfg = new WxCpConfig("mp-config.xml");
        Assert.assertNotNull(cfg);
        Assert.assertEquals("2eLYdbJvlWM627RztQqEUiOQUp1lljgFpEvdEeHdjBA", cfg.getAgentConfig("1").getAesKey());
        Assert.assertEquals("wx8464f7ea09a20903", cfg.getAgentConfig("1").getCorp().getCorpId());
    }
}
