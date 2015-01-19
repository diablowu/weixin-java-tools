package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.WxAPITestBase;
import me.chanjar.weixin.cp.bean.WxCpUser;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试用户接口
 *
 * @author Daniel Qian
 */
public class WxCpUserAPITest extends WxAPITestBase {



  public void testUserCreate() throws WxErrorException {
    WxCpUser user = new WxCpUser();
    user.setUserId("xiaohe.yang");
    user.setName("杨宝");
    user.setDepartIds(new Integer[] { 9, 8 });
    user.setEmail("yangxiaohe@ddd.com");
    user.setGender("女");
    user.setMobile("13564684979");
    user.setPosition("老婆");
    user.setTel("3300393");
    user.addExtAttr("爱好", "老公");
//    wxCpService.userCreate(user);
  }


  @Test
  public void testUserGet() throws WxErrorException {
    WxCpUser user = wxCpService.userGet("wubo");
    Assert.assertNotNull(user);
    System.out.println(user.getAvatar());
  }
}
