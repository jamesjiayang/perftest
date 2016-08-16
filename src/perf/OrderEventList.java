package perf;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderEventList {
    protected Long RootOrderId;
    //at lease one element
    protected List<EventInfo> events;
    protected Map<Long, OrderEventResult> orderEventMap; 
    
    public OrderEventList() {
        this.orderEventMap = new HashMap<Long, OrderEventResult>();
    }
    public OrderEventList(List<EventInfo> events) {
        this.events = events;
        this.RootOrderId = events.get(0).getRootOrdID();
        this.orderEventMap = new HashMap<Long, OrderEventResult>();
    }
    
    //in milliseconds
    public void calculateRoundTrip() {
        long newOrderCreate = 0;
        long deskAck = 0;
        for (EventInfo evt : this.events) {
            if (evt.getEventType().equals(EventType.NEW) || evt.getEventType().equals(EventType.CONFIRM) || evt.getEventType().equals(EventType.BATCH_RELEASE)) {
                Long orderId = evt.getOrderID();
                OrderEventResult result = this.orderEventMap.get(orderId);
                if (result == null) {
                    result = new OrderEventResult();
                }
                newOrderCreate = evt.getEventTime().getTime();
                result.setNewOrderEventTS(newOrderCreate);
                if (result.getDestAckTS() != null) {
                    result.setRoundTripTime(result.getDestAckTS().longValue()
                            - newOrderCreate);
                }
                this.orderEventMap.put(orderId, result);
            }
            if (evt.getEventType().equals(EventType.DEST_ACK)) {
                Long orderId = evt.getOrderID();
                OrderEventResult result = this.orderEventMap.get(orderId);
                if (result == null) {
                    result = new OrderEventResult();
                }
                deskAck = evt.getEventTime().getTime();
                result.setDestAckTS(deskAck);
                if (result.getNewOrderEventTS() != null) {
                    result.setRoundTripTime(deskAck - result.getNewOrderEventTS());
                    result.setEventTime(evt.getEventTime());
                }
                this.orderEventMap.put(orderId, result);
            }
        }
    }
    
    public String toRoundTripString() {
        StringBuffer buffer = new StringBuffer();
        for (Long orderId : this.orderEventMap.keySet()) {
            OrderEventResult result = this.orderEventMap.get(orderId);
            if (result.getRoundTripTime() != null) {
                buffer.append(orderId).append(Const.COMMA);
                buffer.append(new SimpleDateFormat("hh:mm:ss")
                            .format(result.getEventTime())).append(Const.COMMA);
                buffer.append(result.getRoundTripTime()).append(Const.NEWLINE);
            }
        }
        return buffer.toString();
    }
}
