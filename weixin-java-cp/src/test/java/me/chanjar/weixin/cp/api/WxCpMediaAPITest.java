package me.chanjar.weixin.cp.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.WxAPITestBase;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class WxCpMediaAPITest extends WxAPITestBase {


    @Test
    public void testUploadMedia() throws WxErrorException, IOException {
        InputStream inputStream = ClassLoader
                .getSystemResourceAsStream("./mm.jpeg");
        WxMediaUploadResult res = wxCpService.mediaUpload(WxConsts.MEDIA_IMAGE,
                WxConsts.FILE_JPG, inputStream);
        Assert.assertNotNull(res.getType());
        Assert.assertNotNull(res.getCreatedAt());
        System.out.println(res.getMediaId());

    }

    public Object[][] uploadMedia() {
        return new Object[][] {
                new Object[] { WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG,
                        "mm.jpeg" },
                new Object[] { WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3,
                        "mm.mp3" },
                new Object[] { WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4,
                        "mm.mp4" },
                new Object[] { WxConsts.MEDIA_FILE, WxConsts.FILE_JPG,
                        "mm.jpeg" } };
    }

    @Test
    public void testDownloadMedia() throws Exception {
        File tmpFile = wxCpService
                .mediaDownload("1BgJTUOOAgLk7j63lTcTTSZz1LIOwEYQcO5KMxnKfyYhG9ID2LkDTGLp0TpU8eYoZ");
        FileUtils.copyFile(tmpFile, new File("c:/1.jgp"));

    }


}
