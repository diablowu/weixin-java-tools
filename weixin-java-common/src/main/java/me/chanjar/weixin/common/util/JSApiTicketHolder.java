package me.chanjar.weixin.common.util;

import me.chanjar.weixin.common.bean.JSApiTicket;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * accesstoken的就有者，作为web环境下全局唯一引用，acesstoken在刷新时不用考虑同步问题
 * @author Diablo Wu
 * @Date 2015年1月15日 上午10:46:18
 */
public class JSApiTicketHolder {
    
    
    private static final JSApiTicket _TICKET = new JSApiTicket();
    
    public static void load(final String ticket, final int expired){
        _TICKET.setTickct(ticket);
        _TICKET.setExpiresIn(expired);
    }
    
    
    public static JSApiTicket get(){
        return _TICKET;
    }
    
    
    public synchronized static final String requestToken(){
        String token = AccessTokenHolder.get().getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(httpGet);
            String resultContent = new BasicResponseHandler().handleResponse(response);
            WxError error = WxError.fromJson(resultContent);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            JSApiTicket ticket = JSApiTicket.fromJson(resultContent);
            return ticket.getTickct();
        }catch (Exception e) {
            return null;
        }
    }
    

}
