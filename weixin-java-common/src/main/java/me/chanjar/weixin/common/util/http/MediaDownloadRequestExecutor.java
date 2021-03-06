package me.chanjar.weixin.common.util.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.common.util.fs.FileUtils;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * 下载媒体文件请求执行器，请求的参数是String, 返回的结果是File
 * 
 * @author Daniel Qian
 * 
 */
public class MediaDownloadRequestExecutor implements
        RequestExecutor<File, String> {

    @Override
    public File execute(CloseableHttpClient httpclient, String uri, String queryParam) throws WxErrorException,
            ClientProtocolException, IOException {
        //queryParam添加到uri尾部
        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }

        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpResponse response = httpclient.execute(httpGet);

        Header[] contentTypeHeader = response.getHeaders("Content-Type");
        if (contentTypeHeader != null && contentTypeHeader.length > 0) {
            // 下载媒体文件出错
            if (ContentType.TEXT_PLAIN.getMimeType().equals(
                    contentTypeHeader[0].getValue())) {
                String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
                throw new WxErrorException(WxError.fromJson(responseContent));
            }
        }
        InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);

        // 视频文件不支持下载
        String fileName = getFileName(response);
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        String[] name_ext = fileName.split("\\.");
        File localFile = FileUtils.createTmpFile(inputStream, name_ext[0],name_ext[1]);
        return localFile;
    }

    /**
     * 从response 中读取下载文件名
     * @param response
     * @return
     */
    protected String getFileName(CloseableHttpResponse response) {
        Header[] contentDispositionHeader = response.getHeaders("Content-disposition");
        Pattern p = Pattern.compile(".*filename=\"(.*)\"");
        Matcher m = p.matcher(contentDispositionHeader[0].getValue());
        m.matches();
        String fileName = m.group(1);
        return fileName;
    }

}
