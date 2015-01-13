package me.chanjar.weixin.cp.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;

import org.apache.http.client.ClientProtocolException;


/**
 * 覆盖了一些原有方法的功能，不提供自动刷新token功能，需要手工刷新
 * @author Diablo Wu
 * @Date 2015年1月13日 下午3:42:24
 */
public class WxCpNAServiceImpl extends WxCpServiceImpl {

    @Override
    public void accessTokenRefresh() throws WxErrorException {
        //nothing to do.
    }
    
    /**
     * 去除自动刷新token
     * 
     * @param executor
     * @param uri
     * @param data
     * @return
     * @throws WxErrorException
     */
    @Override
    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
        String accessToken = wxCpConfigStorage.getAccessToken();

        String uriWithAccessToken = uri;
        uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token="
                + accessToken : "&access_token=" + accessToken;

        try {
            return executor.execute(getHttpclient(), uriWithAccessToken, data);
        } catch (WxErrorException e) {
            WxError error = e.getError();
            /*
             * 发生以下情况时尝试刷新access_token 40001
             * have a rest and try it.
             */
            if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
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

}
