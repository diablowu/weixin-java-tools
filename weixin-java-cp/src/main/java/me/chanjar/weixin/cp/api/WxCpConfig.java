package me.chanjar.weixin.cp.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.xml.sax.InputSource;

/**
 * 微信客户端配置存储
 * 
 */
@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxCpConfig {

    private String corpId;
    private String corpSecret;

    @XmlElement(name = "agent",type = AgentConfig.class)
    private List<AgentConfig> agents = new ArrayList<AgentConfig>();
    
    
    public WxCpConfig(){}
    
    public WxCpConfig(String path){
        WxCpConfig _c = _load(path);
        this.corpId = _c.corpId;
        this.corpSecret = _c.corpSecret;
        this.agents = _c.agents;
    }

    public List<AgentConfig> getAgents() {
        return agents;
    }

    public void setAgents(List<AgentConfig> agents) {
        this.agents = agents;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }
    
    
    private static WxCpConfig _load(String path){
        try {
            Unmarshaller um = JAXBContext.newInstance(WxCpConfig.class).createUnmarshaller();
            FileInputStream fis = new FileInputStream(path);
            InputSource inputSource = new InputSource(fis);
            inputSource.setEncoding("utf-8");
            WxCpConfig c = (WxCpConfig) um.unmarshal(inputSource);
            fis.close();
            for(AgentConfig ac : c.getAgents()){
                ac.setCorp(c);
            }
            return c;
        } catch (Exception e) {
            throw new RuntimeException("从xml加载配置异常", e);
        }
    }
    
    public static WxCpConfig loadXml(String path){
        return _load(path);
    }
    
    public static void main(String[] args) throws Exception {
        WxCpConfig c = WxCpConfig.loadXml("c:/config.xml");
        System.out.println(c.corpId);
        for (AgentConfig config : c.getAgents()) {
            System.out.println(config.getAgentId() + " : " +config.getCorp().getCorpSecret()) ;
            
        }
    }

}
