package me.chanjar.weixin.cp.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OAuth方式获取的用户信息
 * @author Diablo Wu
 * @Date 2015年1月26日 下午1:11:34
 */
public class OAuthUser {

    private String userId;
    private String deviceId;
    
    private static final String COOKIE_USER_KEY = "oauth_user";
//    private static final String COOKIE_KEY_SIGN = "oauth_sign";
    
    private static final int EXPIRED_SECONDS = 21600;//6 hours
    
    
    public static OAuthUser checkCookie(HttpServletRequest request){
        OAuthUser u = null;
        Cookie[] cks = request.getCookies();
        String userStr = null;
        long curr = System.currentTimeMillis() / 1000;
        for (Cookie cookie : cks) {
            if(COOKIE_USER_KEY.equals(cookie.getName())){
                userStr = cookie.getValue();
                break;
            }
        }
        
        if(userStr != null){
            String[] part = userStr.split("_");
            String userId = part[0];
            String deviceId = part[1];
            String ts = part[2];
            if((Long.valueOf(ts) + EXPIRED_SECONDS) > curr){
                u = new OAuthUser();
                u.setUserId(userId);
                u.setDeviceId(deviceId);
            }
        }
        
        return u;
    }
    
    public void setCookie(HttpServletResponse resp){
        String ts = String.valueOf(System.currentTimeMillis()/1000);
        String userStr = this.userId+ "_" + this.deviceId + "_" + ts;
        Cookie cookie = new Cookie(COOKIE_USER_KEY, userStr);
        cookie.setMaxAge(EXPIRED_SECONDS);
        resp.addCookie(cookie);
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getDeviceId() {
        return deviceId;
    }


    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
}
