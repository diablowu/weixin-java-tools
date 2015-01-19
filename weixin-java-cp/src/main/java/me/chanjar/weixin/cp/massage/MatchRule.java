package me.chanjar.weixin.cp.massage;

import me.chanjar.weixin.common.api.InEventType;
import me.chanjar.weixin.common.api.InMessageType;

public class MatchRule {

    private InMessageType msgType;

    private InEventType event;

    private String eventKey;

    private String content;

    private TextMatchMode textMatchMode;

    private Integer agentId;



    public InMessageType getMsgType() {
        return msgType;
    }

    public void setMsgType(InMessageType msgType) {
        this.msgType = msgType;
    }

    public InEventType getEvent() {
        return event;
    }

    public void setEvent(InEventType event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextMatchMode getTextMatchMode() {
        return textMatchMode;
    }

    public void setTextMatchMode(TextMatchMode textMatchMode) {
        this.textMatchMode = textMatchMode;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    
    public enum TextMatchMode {
        full, exsit
    }
}
