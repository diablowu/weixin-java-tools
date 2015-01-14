package me.chanjar.weixin.cp.api;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.xml.sax.InputSource;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxCpReloadMemConfigStorage extends WxCpInMemoryConfigStorage {

    private static final WxCpConfigStorage _CONFIG = fromXml();

    private static WxCpReloadMemConfigStorage fromXml() {
        try {
            InputStream is = WxCpReloadMemConfigStorage.class
                    .getResourceAsStream("/mp.config.xml");
            Unmarshaller um = JAXBContext.newInstance(
                    WxCpReloadMemConfigStorage.class).createUnmarshaller();

            InputSource inputSource = new InputSource(is);
            inputSource.setEncoding("utf-8");
            return (WxCpReloadMemConfigStorage) um.unmarshal(inputSource);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static WxCpConfigStorage get(){
        return _CONFIG;
    }

}
