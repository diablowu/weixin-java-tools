package me.chanjar.weixin.cp.bean;

import java.io.FileNotFoundException;
import java.io.IOException;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.WxAPITestBase;

import org.junit.Assert;
import org.junit.Test;

public class WxCpXmlMessageTest extends WxAPITestBase {

    
  @Test
  public void testFromXml() {

    String xml = "<xml>"
                + "<ToUserName><![CDATA[toUser]]></ToUserName>"
                + "<FromUserName><![CDATA[fromUser]]></FromUserName> "
                + "<CreateTime>1348831860</CreateTime>"
                + "<MsgType><![CDATA[text]]></MsgType>"
                + "<Content><![CDATA[this is a test]]></Content>"
                + "<MsgId>1234567890123456</MsgId>"
                + "<PicUrl><![CDATA[this is a url]]></PicUrl>"
                + "<MediaId><![CDATA[media_id]]></MediaId>"
                + "<Format><![CDATA[Format]]></Format>"
                + "<ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>"
                + "<Location_X>23.134521</Location_X>"
                + "<Location_Y>113.358803</Location_Y>"
                + "<Scale>20</Scale>"
                + "<Label><![CDATA[位置信息]]></Label>"
                + "<Description><![CDATA[公众平台官网链接]]></Description>"
                + "<Url><![CDATA[url]]></Url>"
                + "<Title><![CDATA[公众平台官网链接]]></Title>"
                + "<Event><![CDATA[subscribe]]></Event>"
                + "<EventKey><![CDATA[qrscene_123123]]></EventKey>"
                + "<Ticket><![CDATA[TICKET]]></Ticket>"
                + "<Latitude>23.137466</Latitude>"
                + "<Longitude>113.352425</Longitude>"
                + "<Precision>119.385040</Precision>"
                + "<ScanCodeInfo>"
                + " <ScanType><![CDATA[qrcode]]></ScanType>"
                + " <ScanResult><![CDATA[1]]></ScanResult>"
                + "</ScanCodeInfo>"
                + "<SendPicsInfo>"
                + " <Count>1</Count>\n"
                + " <PicList>"
                + "  <item>"
                + "   <PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>"
                + "  </item>"
                + " </PicList>"
                + "</SendPicsInfo>"
                + "<SendLocationInfo>"
                + "  <Location_X><![CDATA[23]]></Location_X>\n"
                + "  <Location_Y><![CDATA[113]]></Location_Y>\n"
                + "  <Scale><![CDATA[15]]></Scale>\n"
                + "  <Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>\n"
                + "  <Poiname><![CDATA[wo de poi]]></Poiname>\n"
                + "</SendLocationInfo>"
                + "</xml>";
    WxCpXmlMessage wxMessage = WxCpXmlMessage.fromXml(xml);
    Assert.assertEquals(wxMessage.getToUserName(), "toUser");
    Assert.assertEquals(wxMessage.getFromUserName(), "fromUser");
    Assert.assertEquals(wxMessage.getCreateTime(), new Long(1348831860l));
    Assert.assertEquals(wxMessage.getMsgType(), WxConsts.XML_MSG_TEXT);
    Assert.assertEquals(wxMessage.getContent(), "this is a test");
    Assert.assertEquals(wxMessage.getMsgId(), new Long(1234567890123456l));
    Assert.assertEquals(wxMessage.getPicUrl(), "this is a url");
    Assert.assertEquals(wxMessage.getMediaId(), "media_id");
    Assert.assertEquals(wxMessage.getFormat(), "Format");
    Assert.assertEquals(wxMessage.getThumbMediaId(), "thumb_media_id");
    Assert.assertEquals(wxMessage.getLocationX(), new Double(23.134521d));
    Assert.assertEquals(wxMessage.getLocationY(), new Double(113.358803d));
    Assert.assertEquals(wxMessage.getScale(), new Double(20));
    Assert.assertEquals(wxMessage.getLabel(), "位置信息");
    Assert.assertEquals(wxMessage.getDescription(), "公众平台官网链接");
    Assert.assertEquals(wxMessage.getUrl(), "url");
    Assert.assertEquals(wxMessage.getTitle(), "公众平台官网链接");
    Assert.assertEquals(wxMessage.getEvent(), "subscribe");
    Assert.assertEquals(wxMessage.getEventKey(), "qrscene_123123");
    Assert.assertEquals(wxMessage.getTicket(), "TICKET");
    Assert.assertEquals(wxMessage.getLatitude(), new Double(23.137466));
    Assert.assertEquals(wxMessage.getLongitude(), new Double(113.352425));
    Assert.assertEquals(wxMessage.getPrecision(), new Double(119.385040));
    Assert.assertEquals(wxMessage.getScanCodeInfo().getScanType(), "qrcode");
    Assert.assertEquals(wxMessage.getScanCodeInfo().getScanResult(), "1");
    Assert.assertEquals(wxMessage.getSendPicsInfo().getCount(), new Long(1l));
    Assert.assertEquals(wxMessage.getSendPicsInfo().getPicList().get(0).getPicMd5Sum(), "1b5f7c23b5bf75682a53e7b6d163e185");
    Assert.assertEquals(wxMessage.getSendLocationInfo().getLocationX(), "23");
    Assert.assertEquals(wxMessage.getSendLocationInfo().getLocationY(), "113");
    Assert.assertEquals(wxMessage.getSendLocationInfo().getScale(), "15");
    Assert.assertEquals(wxMessage.getSendLocationInfo().getLabel(), " 广州市海珠区客村艺苑路 106号");
    Assert.assertEquals(wxMessage.getSendLocationInfo().getPoiname(), "wo de poi");
  }
  
  String enXml = "<xml><ToUserName><![CDATA[wx8464f7ea09a20903]]></ToUserName>"
+"<Encrypt><![CDATA[uWIL6hjwzk6EK0goxgBI/6H1VmXgWPtS2cxbi8spLE3KIbO620CxJy1bwSeARy1/LW4gsfLES8PTolwfGMX5uvqfujFfBASFATrUJh1kuhQ29ZYK2Wu5p71riWD6I8e8IBAyPIHFyo1cmY0XEfheH+yNXqInipVqmO5/Jsgz04sEM4ys+3AfLJji3EiWsiZr8P80/DCMr0p4teqp0hHQwv95kCM11RUE7ic/1OyVhibh71ALK5GKbsPpvdHR8FiOyzl2CmIE3BCajCJSl7KzLXdUzudDlYcU+6eMMgU2jVhA5YkPCIWrIzg66+B3LhqqvQZDXfhuzk1RaoCwkHX13e3z9phdsVAmJweOKHyhdGm1vYuLfsnwjUVh4hfHAwxPlQ0GSY8gAmMPmGDtdUvPL8966G1RGqbmGRJzvsGpxIQCPdqUanUit0vgcBI7LXlspi9qQYl7M3El3oqHcgkElA==]]></Encrypt>"
+"<AgentID><![CDATA[1]]></AgentID></xml>";
  
  @Test
  public void testCryptMsg() throws FileNotFoundException, IOException{
      String timestamp = "1421655193";
      String nonce = "2001091623";
      String sign = "59a6f5cb2cc45827053350689149602a4f623593";
      WxCpXmlMessage m =    WxCpXmlMessage.fromEncryptedXml(enXml, this.config.getAgentConfig("1"), timestamp, nonce, sign);
      
      System.out.println(m.getContent());
  }
}
