package perf;

import java.util.Date;

public class OrderEventResult {
    protected Long roundTripTime;
    protected Long newOrderEventTS;
    protected Long destAckTS;
    protected Date eventTime;

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Long getDestAckTS() {
        return destAckTS;
    }

    public void setDestAckTS(Long destAckTS) {
        this.destAckTS = destAckTS;
    }

    public Long getNewOrderEventTS() {
        return newOrderEventTS;
    }

    public void setNewOrderEventTS(Long newOrderEventTS) {
        this.newOrderEventTS = newOrderEventTS;
    }

    public Long getRoundTripTime() {
        return roundTripTime;
    }

    public void setRoundTripTime(Long roundTripTime) {
        this.roundTripTime = roundTripTime;
    }
}
