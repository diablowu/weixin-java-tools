package me.chanjar.weixin.common.bean;

import me.chanjar.weixin.common.util.json.WxGsonBuilder;


/**
 * jsapi ticket
 * @author Diablo Wu 
 * 
 */
public class JSApiTicket {
    private String errCode;
    private String errMsg;
    private String tickct;
    private int expiresIn = -1;


    public int getExpiresIn() {
      return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
      this.expiresIn = expiresIn;
    }

    public static JSApiTicket fromJson(String json) {
      return WxGsonBuilder.create().fromJson(json, JSApiTicket.class);
    }

    
    /**
     * @return the tickct
     */
    public String getTickct() {
        return tickct;
    }

    
    /**
     * @param tickct the tickct to set
     */
    public void setTickct(String tickct) {
        this.tickct = tickct;
    }

    
    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    
    /**
     * @param errCode the errCode to set
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    
    /**
     * @return the errMsg
     */
    public String getErrMsg() {
        return errMsg;
    }

    
    /**
     * @param errMsg the errMsg to set
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
}
