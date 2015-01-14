package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.WxAPITestBase;
import me.chanjar.weixin.cp.bean.WxCpMessage;

import org.junit.Test;

/***
 * 测试发送消息
 * 
 * @author Daniel Qian
 *
 */
public class WxCpMessageAPITest extends WxAPITestBase {

    @Test
    public void testSendCustomMessage() throws WxErrorException {
        WxCpMessage message1 = new WxCpMessage();
        message1.setAgentId(storage.getAgentId());
        message1.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
        message1.setToUser("wubo");
        message1.setContent("欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");
        wxCpService.messageSend(message1);

        WxCpMessage message2 = WxCpMessage
                .TEXT()
                .agentId(storage.getAgentId())
                .toUser("wubo")
                .content(
                        "欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>")
                .build();
        wxCpService.messageSend(message2);

    }

}
