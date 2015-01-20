package me.chanjar.weixin.common.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * accesstoken的就有者，作为web环境下全局唯一引用，acesstoken在刷新时不用考虑同步问题
 * @author Diablo Wu
 * @Date 2015年1月15日 上午10:46:18
 */
public class AccessTokenHolder {
    
    
    private static final WxAccessToken _TOKEN = new WxAccessToken();
    
    public static TokenType MP_TYPE;
    
    @Deprecated
    /**
     * @param token
     * @param expired
     */
    public static void load(final String token, final int expired){
        _TOKEN.setAccessToken(token);
        _TOKEN.setExpiresIn(expired);
    }
    
    
    public static void load(final String token, final int expired, TokenType tokenType){
        _TOKEN.setAccessToken(token);
        _TOKEN.setExpiresIn(expired);
        MP_TYPE = tokenType;
    }    
    
    
    public static WxAccessToken get(){
        return _TOKEN;
    }
    

    
    public synchronized static final String requestToken(final String id,final String secret,final TokenType type){
        String url = "";
        MP_TYPE = type;
        if(type.equals(TokenType.CP)){
            url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?"
                    + "&corpid=" + id
                    + "&corpsecret=" + secret;
        }else{
            url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
                    + "&appid="
                    + id
                    + "&secret="
                    + secret;
        }
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(httpGet);
            String resultContent = new BasicResponseHandler().handleResponse(response);
            WxError error = WxError.fromJson(resultContent);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
            return accessToken.getAccessToken();
        }catch (Exception e) {
            return null;
        }
    }
    
    public static enum TokenType{
        MP,
        CP
    }

}
