package me.chanjar.weixin.common.util;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import me.chanjar.weixin.common.util.crypto.SHA1;


/**
 * JsApiSdk配置参数,会自动生成noncestr、ts和摘要
 * @author Diablo Wu
 * @Date 2015年1月26日 上午9:53:22
 */
public class JsApiSdkConfig {
    private String url;
    private String nonceStr;
    private String timestamp;
    private String signature;
    
    
    public JsApiSdkConfig(String url) throws NoSuchAlgorithmException{
        this.url = url;
        
        String ticket = JSApiTicketHolder.get().getTickct();
        this.nonceStr = genNonceStr();
        this.timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        this.signature = sign(ticket);
    }

    private String sign(String ticket) throws NoSuchAlgorithmException {
        String str = "jsapi_ticket=" + ticket+
                "&noncestr=" + nonceStr + 
                "&timestamp=" + this.timestamp + 
                "&url="+ this.url;

        return SHA1.gen(str);


    }


    private String genNonceStr() {
        return UUID.randomUUID().toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
