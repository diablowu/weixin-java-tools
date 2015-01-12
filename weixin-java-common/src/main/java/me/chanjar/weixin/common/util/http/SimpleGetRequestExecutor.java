package me.chanjar.weixin.common.util.http;

import java.io.IOException;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 * 
 * @author Daniel Qian
 * 
 */
public class SimpleGetRequestExecutor implements
        RequestExecutor<String, String> {

    @Override
    public String execute(CloseableHttpClient httpclient, String uri, String queryParam) throws WxErrorException,
            ClientProtocolException, IOException {
        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }
        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpResponse response = httpclient.execute(httpGet);
        String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
        WxError error = WxError.fromJson(responseContent);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }
        return responseContent;
    }

}
