/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 *
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

/**
 * 针对org.apache.commons.codec.binary.Base64，
 * 需要导入架包commons-codec-1.9（或commons-codec-1.8等其他版本）
 * 官方下载地址：http://commons.apache.org/proper/commons-codec/download_codec.cgi
 */
package me.chanjar.weixin.cp.util.crypto;

import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import me.chanjar.weixin.cp.api.AgentConfig;

import org.apache.commons.codec.binary.Base64;

public class WxCpCryptUtil extends WxCryptUtil {

  /**
   * 构造函数
   *
   * @param wxCpConfigStorage
   */
  public WxCpCryptUtil(AgentConfig config) {
    /*
     * @param token          公众平台上，开发者设置的token
     * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
     * @param appidOrCorpid          公众平台appid
     */
    String encodingAesKey = config.getAesKey();
    String token = config.getToken();
    String corpId = config.getCorp().getCorpId();

    this.token = token;
    this.appidOrCorpid = corpId;
    this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
  }


}
