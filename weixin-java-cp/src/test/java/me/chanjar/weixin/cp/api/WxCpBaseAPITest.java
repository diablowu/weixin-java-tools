package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;

import org.junit.Assert;

import com.google.inject.Inject;

/**
 * 基础API测试
 * @author Daniel Qian
 *
 */
public class WxCpBaseAPITest {

  @Inject
  protected WxCpServiceImpl wxService;

  public void testRefreshAccessToken() throws WxErrorException {
    WxCpConfigStorage configStorage = wxService.wxCpConfigStorage;
    String before = configStorage.getAccessToken();
    wxService.accessTokenRefresh();

    String after = configStorage.getAccessToken();

    Assert.assertNotEquals(before, after);
    Assert.assertTrue(StringUtils.isNotBlank(after));
  }

}
