package perf;

import java.util.Date;

public class EventInfo {
    protected EventType eventType = EventType.UNDEFINED;
    protected Long rootOrdID;
    protected Long orderID;
    protected Integer eventNum;
    protected Integer fillID;
    protected String reasonCode;
    protected String reasonText;
    protected String clientState = null;
    protected Date eventTime;
    protected Date insertTime;
    
    public EventType getEventType() {
        return eventType;
    }
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    public Long getRootOrdID() {
        return rootOrdID;
    }
    public void setRootOrdID(Long rootOrdID) {
        this.rootOrdID = rootOrdID;
    }
    public Long getOrderID() {
        return orderID;
    }
    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }
    public Integer getEventNum() {
        return eventNum;
    }
    public void setEventNum(Integer eventNum) {
        this.eventNum = eventNum;
    }
    public Integer getFillID() {
        return fillID;
    }
    public void setFillID(Integer fillID) {
        this.fillID = fillID;
    }
    public String getReasonCode() {
        return reasonCode;
    }
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
    public String getReasonText() {
        return reasonText;
    }
    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }
    public String getClientState() {
        return clientState;
    }
    public void setClientState(String clientState) {
        this.clientState = clientState;
    }
    public Date getEventTime() {
        return eventTime;
    }
    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    public Date getInsertTime() {
        return insertTime;
    }
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

}
