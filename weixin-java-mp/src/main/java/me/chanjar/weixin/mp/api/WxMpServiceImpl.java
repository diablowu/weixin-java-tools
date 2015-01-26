package me.chanjar.weixin.mp.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.UUID;

import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.AccessTokenHolder;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.common.util.http.MediaDownloadRequestExecutor;
import me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.common.util.http.URIUtil;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpGroup;
import me.chanjar.weixin.mp.bean.WxMpMassGroupMessage;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassVideo;
import me.chanjar.weixin.mp.bean.WxMpSemanticQuery;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpSemanticQueryResult;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.util.http.QrCodeRequestExecutor;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class WxMpServiceImpl implements WxMpService {


    protected WxMpConfig wxMpConfig;

    protected final ThreadLocal<Integer> retryTimes = new ThreadLocal<Integer>();

    protected CloseableHttpClient httpClient;


    public boolean checkSignature(String timestamp, String nonce, String signature) {
        try {
            return SHA1.gen(wxMpConfig.getToken(), timestamp, nonce).equals(signature);
        } catch (Exception e) {
            return false;
        }
    }
    

    public void customMessageSend(WxMpCustomMessage message)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
        execute(new SimplePostRequestExecutor(), url, message.toJson());
    }

    public void menuCreate(WxMenu menu) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
        execute(new SimplePostRequestExecutor(), url, menu.toJson());
    }

    public void menuDelete() throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete";
        execute(new SimpleGetRequestExecutor(), url, null);
    }

    public WxMenu menuGet() throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get";
        try {
            String resultContent = execute(new SimpleGetRequestExecutor(), url,
                    null);
            return WxMenu.fromJson(resultContent);
        } catch (WxErrorException e) {
            // 46003 不存在的菜单数据
            if (e.getError().getErrorCode() == 46003) {
                return null;
            }
            throw e;
        }
    }

    public WxMediaUploadResult mediaUpload(String mediaType, String fileType,
            InputStream inputStream) throws WxErrorException, IOException {
        return mediaUpload(mediaType, FileUtils.createTmpFile(inputStream, UUID
                .randomUUID().toString(), fileType));
    }

    public WxMediaUploadResult mediaUpload(String mediaType, File file)
            throws WxErrorException {
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?type="
                + mediaType;
        return execute(new MediaUploadRequestExecutor(), url, file);
    }

    public File mediaDownload(String media_id) throws WxErrorException {
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get";
        return execute(new MediaDownloadRequestExecutor(), url, "media_id="
                + media_id);
    }

    public WxMpMassUploadResult massNewsUpload(WxMpMassNews news)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                news.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    public WxMpMassUploadResult massVideoUpload(WxMpMassVideo video)
            throws WxErrorException {
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/uploadvideo";
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                video.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    public WxMpMassSendResult massGroupMessageSend(WxMpMassGroupMessage message)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                message.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    public WxMpMassSendResult massOpenIdsMessageSend(
            WxMpMassOpenIdsMessage message) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                message.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    public WxMpGroup groupCreate(String name) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/create";
        JsonObject json = new JsonObject();
        JsonObject groupJson = new JsonObject();
        json.add("group", groupJson);
        groupJson.addProperty("name", name);

        String responseContent = execute(new SimplePostRequestExecutor(), url,
                json.toString());
        return WxMpGroup.fromJson(responseContent);
    }

    public List<WxMpGroup> groupGet() throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/get";
        String responseContent = execute(new SimpleGetRequestExecutor(), url,
                null);
        /*
         * 操蛋的微信API，创建时返回的是 { group : { id : ..., name : ...} } 查询时返回的是 { groups
         * : [ { id : ..., name : ..., count : ... }, ... ] }
         */
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(
                new StringReader(responseContent)));
        return WxMpGsonBuilder.INSTANCE.create().fromJson(
                tmpJsonElement.getAsJsonObject().get("groups"),
                new TypeToken<List<WxMpGroup>>() {
                }.getType());
    }

    public long userGetGroup(String openid) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/getid";
        JsonObject o = new JsonObject();
        o.addProperty("openid", openid);
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                o.toString());
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(
                new StringReader(responseContent)));
        return GsonHelper.getAsLong(tmpJsonElement.getAsJsonObject().get(
                "groupid"));
    }

    public void groupUpdate(WxMpGroup group) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/update";
        execute(new SimplePostRequestExecutor(), url, group.toJson());
    }

    public void userUpdateGroup(String openid, long to_groupid)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update";
        JsonObject json = new JsonObject();
        json.addProperty("openid", openid);
        json.addProperty("to_groupid", to_groupid);
        execute(new SimplePostRequestExecutor(), url, json.toString());
    }

    public void userUpdateRemark(String openid, String remark)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
        JsonObject json = new JsonObject();
        json.addProperty("openid", openid);
        json.addProperty("remark", remark);
        execute(new SimplePostRequestExecutor(), url, json.toString());
    }

    public WxMpUser userInfo(String openid, String lang)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
        lang = lang == null ? "zh_CN" : lang;
        String responseContent = execute(new SimpleGetRequestExecutor(), url,
                "openid=" + openid + "&lang=" + lang);
        return WxMpUser.fromJson(responseContent);
    }

    public WxMpUserList userList(String next_openid) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/get";
        String responseContent = execute(new SimpleGetRequestExecutor(), url,
                next_openid == null ? null : "next_openid=" + next_openid);
        return WxMpUserList.fromJson(responseContent);
    }

    public WxMpQrCodeTicket qrCodeCreateTmpTicket(int scene_id,
            Integer expire_seconds) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
        JsonObject json = new JsonObject();
        json.addProperty("action_name", "QR_SCENE");
        if (expire_seconds != null) {
            json.addProperty("expire_seconds", expire_seconds);
        }
        JsonObject actionInfo = new JsonObject();
        JsonObject scene = new JsonObject();
        scene.addProperty("scene_id", scene_id);
        actionInfo.add("scene", scene);
        json.add("action_info", actionInfo);
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                json.toString());
        return WxMpQrCodeTicket.fromJson(responseContent);
    }

    public WxMpQrCodeTicket qrCodeCreateLastTicket(int scene_id)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
        JsonObject json = new JsonObject();
        json.addProperty("action_name", "QR_LIMIT_SCENE");
        JsonObject actionInfo = new JsonObject();
        JsonObject scene = new JsonObject();
        scene.addProperty("scene_id", scene_id);
        actionInfo.add("scene", scene);
        json.add("action_info", actionInfo);
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                json.toString());
        return WxMpQrCodeTicket.fromJson(responseContent);
    }

    public File qrCodePicture(WxMpQrCodeTicket ticket) throws WxErrorException {
        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
        return execute(new QrCodeRequestExecutor(), url, ticket);
    }

    public String shortUrl(String long_url) throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl";
        JsonObject o = new JsonObject();
        o.addProperty("action", "long2short");
        o.addProperty("long_url", long_url);
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                o.toString());
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(
                new StringReader(responseContent)));
        return tmpJsonElement.getAsJsonObject().get("short_url").getAsString();
    }

    public String templateSend(WxMpTemplateMessage templateMessage)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                templateMessage.toJson());
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(
                new StringReader(responseContent)));
        return tmpJsonElement.getAsJsonObject().get("msgid").getAsString();
    }

    public WxMpSemanticQueryResult semanticQuery(WxMpSemanticQuery semanticQuery)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/semantic/semproxy/search";
        String responseContent = execute(new SimplePostRequestExecutor(), url,
                semanticQuery.toJson());
        return WxMpSemanticQueryResult.fromJson(responseContent);
    }

    @Override
    public String oauth2buildAuthorizationUrl(String scope, String state, String redirectURI) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
        url += "appid=" + wxMpConfig.getAppId();
        url += "&redirect_uri="
                + URIUtil.encodeURIComponent(redirectURI);
        url += "&response_type=code";
        url += "&scope=" + scope;
        if (state != null) {
            url += "&state=" + state;
        }
        url += "#wechat_redirect";
        return url;
    }

    @Override
    public WxMpOAuth2AccessToken oauth2getAccessToken(String code)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
        url += "appid=" + wxMpConfig.getAppId();
        url += "&secret=" + wxMpConfig.getAppSecret();
        url += "&code=" + code;
        url += "&grant_type=authorization_code";

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            String responseText = executor.execute(getHttpclient(), url, null);
            return WxMpOAuth2AccessToken.fromJson(responseText);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken)
            throws WxErrorException {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";
        url += "appid=" + wxMpConfig.getAppId();
        url += "&grant_type=refresh_token";
        url += "&refresh_token=" + refreshToken;

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            String responseText = executor.execute(getHttpclient(), url, null);
            return WxMpOAuth2AccessToken.fromJson(responseText);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken oAuth2AccessToken,
            String lang) throws WxErrorException {
        String url = "https://api.weixin.qq.com/sns/userinfo?";
        url += "access_token=" + oAuth2AccessToken.getAccessToken();
        url += "&openid=" + oAuth2AccessToken.getOpenId();
        if (lang == null) {
            url += "&lang=zh_CN";
        } else {
            url += "&lang=" + lang;
        }

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            String responseText = executor.execute(getHttpclient(), url, null);
            return WxMpUser.fromJson(responseText);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean oauth2validateAccessToken(
            WxMpOAuth2AccessToken oAuth2AccessToken) {
        String url = "https://api.weixin.qq.com/sns/auth?";
        url += "access_token=" + oAuth2AccessToken;
        url += "&openid=" + oAuth2AccessToken.getOpenId();

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            executor.execute(getHttpclient(), url, null);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WxErrorException e) {
            return false;
        }
        return true;
    }

    public String get(String url, String queryParam) throws WxErrorException {
        return execute(new SimpleGetRequestExecutor(), url, queryParam);
    }

    public String post(String url, String postData) throws WxErrorException {
        return execute(new SimplePostRequestExecutor(), url, postData);
    }

    /**
     * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
     * 
     * @param executor
     * @param uri
     * @param data
     * @return
     * @throws WxErrorException
     */
    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data)
            throws WxErrorException {
        String accessToken = AccessTokenHolder.get().getAccessToken();

        String uriWithAccessToken = uri;
        uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token="
                + accessToken : "&access_token=" + accessToken;

        try {
            return executor.execute(getHttpclient(), uriWithAccessToken, data);
        } catch (WxErrorException e) {
            WxError error = e.getError();
            /*
             * 发生以下情况时尝试刷新access_token 40001
             * 获取access_token时AppSecret错误，或者access_token无效 42001 access_token超时
             */
            if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001) {
                return execute(executor, uri, data);
            }
            /**
             * -1 系统繁忙, 1000ms后重试
             */
            if (error.getErrorCode() == -1) {
                if (retryTimes.get() == null) {
                    retryTimes.set(0);
                }
                if (retryTimes.get() > 4) {
                    retryTimes.set(0);
                    throw new RuntimeException("微信服务端异常，超出重试次数");
                }
                int sleepMillis = 1000 * (1 << retryTimes.get());
                try {
                    System.out.println("微信系统繁忙，" + sleepMillis + "ms后重试");
                    Thread.sleep(sleepMillis);
                    retryTimes.set(retryTimes.get() + 1);
                    return execute(executor, uri, data);
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }
            }
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            return null;
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected CloseableHttpClient getHttpclient() {
        return HttpClients.createDefault();
    }

    
    
    @Override
    public WxMpConfig getWxMpConfig() {
        return this.wxMpConfig;
    }
    
    @Override
    public void setWxMpConfig(WxMpConfig wxMpConfig) {
        this.wxMpConfig = wxMpConfig;
    }

}
