package me.chanjar.weixin.cp.api;

import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.JSApiTicketHolder;
import me.chanjar.weixin.common.util.JsApiSdkConfig;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.cp.WxAPITestBase;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试部门接口
 *
 * @author Daniel Qian
 */
public class WxCpDepartAPITest extends WxAPITestBase {



  @Test
  public void testDepartCreate() throws WxErrorException {
    WxCpDepart depart = new WxCpDepart();
    depart.setName("子部门" + System.currentTimeMillis());
    depart.setParentId(2);
    depart.setOrder(1);
    Integer departId = wxCpService.departCreate(depart);
    System.out.println(departId);
  }

  @Test
  public void testDepartGet() throws WxErrorException {
    System.out.println("=================获取部门");
    List<WxCpDepart> departList = wxCpService.departGet();
    Assert.assertNotNull(departList);
    Assert.assertTrue(departList.size() > 0);
    for (WxCpDepart depart : departList) {
      System.out.println(depart.getId() + ":" + depart.getName());
      Assert.assertNotNull(depart.getName());
    }
  }

  @Test
  public void testDepartUpdate() throws WxErrorException {
    System.out.println("=================更新部门");
    WxCpDepart depart = new WxCpDepart();
    depart.setName("子部门改名" + System.currentTimeMillis());
    wxCpService.departUpdate(depart);
  }

  @Test
  public void testDepartDelete() throws WxErrorException {
      WxCpDepart depart = new WxCpDepart();
      depart.setId(5);
      
    System.out.println("=================删除部门");
    System.out.println(depart.getId() + ":" + depart.getName());
    wxCpService.departDelete(depart.getId());
  }
  
  
  @Test
  public void testCheck() throws Exception{
      String sign = "cb7f4119109f0f93fe284af24b3ab143680789a2";
      String ts = "1422074184";
      String nonce = "1675084844";
      String echoStr = URLDecoder.decode("%2FzH79bOyR717CCDqNxpy1%2BQDr%2BmSFidMNiP8Q4wy7tHIcIVrB1XIc5Wz5bUJ9ARK3wUwS9KujpwAs3ggpm7WhA%3D%3D","utf-8");
      String echoStrPlaint = "11111111111111111111111";
      Assert.assertTrue(wxCpService.checkSignature("1", ts, nonce, echoStr,sign));
      
      WxCpCryptUtil cryptUtil = new WxCpCryptUtil(this.config.getAgentConfig("1"));
      String plainText = cryptUtil.decrypt(echoStr);
      Assert.assertEquals(echoStrPlaint, plainText);
      
  }
  
  @Test
  public void test() throws Exception, NoSuchPaddingException{
      byte[] aesKey = Base64.decodeBase64("2eLYdbJvlWM627RztQqEUiOQUp1lljgFpEvdEeHdjBA=");
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
      IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
      cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
  }
  
  
  @Test
  public void testOUt(){
      WxCpXmlOutMessage textMsg = WxCpXmlOutMessage.TEXT().content("hello").toUser("wubo").fromUser("adfadf").build();
      String xml = textMsg.toEncryptedXml(this.wxCpService.getWxCpConfig().getAgentConfig("1"));
      System.out.println(xml);
  }
  
  
  @Test
  public void testJSAPI() throws NoSuchAlgorithmException{
      JsApiSdkConfig cfg = new JsApiSdkConfig("http://a.com/adfads.do?adsfas=1&234234=2");
      Assert.assertNotNull(cfg);
      System.out.println(cfg.getNonceStr());
      System.out.println(cfg.getTimestamp());
      System.out.println(cfg.getSignature());
      System.out.println(JSApiTicketHolder.get().getTickct());
  }


}
