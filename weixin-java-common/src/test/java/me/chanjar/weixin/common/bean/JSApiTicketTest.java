package me.chanjar.weixin.common.bean;

import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import org.junit.Assert;
import org.junit.Test;


/**
 * 
 * @author Diablo Wu 
 * 
 */

public class JSApiTicketTest {
    
    @org.junit.Test
    public void testFromJSON(){
        String json = "{\"errcode\":0,\"errmsg\":\"ok\",\"ticket\":\"ticket\",\"expires_in\":7200}";
        System.out.println(json);
        JSApiTicket t = WxGsonBuilder.create().fromJson(json, JSApiTicket.class);
        
        System.out.println(t.getTickct());
        
    }
    
    @Test
    public void testFromJson1() {

        String json = "{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200}";
        WxAccessToken wxError = WxAccessToken.fromJson(json);
        Assert.assertEquals(wxError.getAccessToken(), "ACCESS_TOKEN");
        Assert.assertTrue(wxError.getExpiresIn() == 7200);

      }
}
