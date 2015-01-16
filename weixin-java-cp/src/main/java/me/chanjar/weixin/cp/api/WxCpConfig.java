package me.chanjar.weixin.cp.api;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
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
    
    private Map<String, AgentConfig> configs = new HashMap<String, AgentConfig>();
    
    public WxCpConfig(){}
    
    public WxCpConfig(String path){
        WxCpConfig _c = load(path);
        this.corpId = _c.corpId;
        this.corpSecret = _c.corpSecret;
        this.agents = _c.agents;
        for (AgentConfig agentConfig : _c.agents) {
            configs.put(agentConfig.getAgentId(), agentConfig);
        }
    }
    
    public AgentConfig getAgentConfig(String agentId){
        return this.configs.get(agentId);
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
    
    
    private WxCpConfig load(String path){
        try {
            Unmarshaller um = JAXBContext.newInstance(WxCpConfig.class).createUnmarshaller();
            FileInputStream fis = new FileInputStream(path);
            InputSource inputSource = new InputSource(fis);
            inputSource.setEncoding("utf-8");
            WxCpConfig cpconfig = (WxCpConfig) um.unmarshal(inputSource);
            fis.close();
            for(AgentConfig ac : cpconfig.getAgents()){
                ac.setCorp(cpconfig);
            }
            return cpconfig;
        } catch (Exception e) {
            throw new RuntimeException("从xml加载配置异常", e);
        }
    }
    
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("corpid : "+this.corpId+"\n").append("corpsec : "+this.corpSecret+"\n");
        for (Entry<String, AgentConfig> ee : this.configs.entrySet()) {
            sb.append(ee.getKey()+":"+ee.getValue().toString()).append("\n");
        }
        return sb.toString();
    }

    

}
