package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.bean.JSApiTicket;
import me.chanjar.weixin.common.bean.WxAccessToken;

/**
 * 微信客户端配置存储
 * @author Daniel Qian
 *
 */
public interface WxCpConfigStorage {

  public void updateAccessToken(WxAccessToken accessToken);
  
  public void updateAccessToken(String accessToken, int expiresIn);
  
  public void updateJSApiTicket(JSApiTicket ticket);
  
  public void updateJSApiTicket(String ticket, int expiresIn);
  
  public String getAccessToken();
  
  public String getJSApiTicket();
  
  public int getJSApiTicketExpiresIn();
  
  public String getCorpId();
  
  public String getCorpSecret();

  public String getAgentId();

  public String getToken();

  public String getAesKey();

  public int getExpiresIn();

  public String getOauth2redirectUri();


}
