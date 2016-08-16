package perf;

import java.util.Map;
import java.util.HashMap;
import java.util.EnumSet;

public enum EventType {
    PROPOSE_NEW(1),
    PROPOSE_CORR(2),
    PROPOSE_CXL(3),
    SENT(4),
    ACK(5),
    DEST_ACK(6),
    NEW(7),
    CORR(8),
    CXL(9),
    FILL(10),
    APPROVAL(11),
    APPROVAL_REJECT(12),
    REJECTION(13),
    CANCELLATION(14),
    CONFIRM(15),
    CXL_REJECT(16),
    CORR_REJECT(17),
    RESTATED_REPORT(18),
    END_REPORT(19),
    FILL_CANCEL(20),
    FILL_CORR(21),
    DISCARD(22),
    ATTEMPTED(23),
    EXPIRY(24),
    UNSOLICITED_CANCEL(25),
    UNSOLICITED_ORDER(26),
    PROPOSED_EXPIRY(27),
    GATEWAY_REJECTION(28),
    CHECKOUT(29),
    RELEASE_RESERVATION(30),
    CONFIRM_BYPASS(31),
    BATCH_RELEASE(32),
    UNSENT(33),
    MANUAL_FILL(34),
    MANUAL_FILL_CORR(35),
    MANUAL_FILL_CXL(36),
    FILL_ALLOCATION(37),
    APPROVAL_TIMEOUT(38),
    CHECKOUT_BY_ID(39),
    ASSIGNED_TO_SUP(40),
    TRIGGERED(41),
    ALLOCATE_NI(43),
    CLOSE_NI(44),
    SAVE_ORDER(46),
    HOLD_RESERVATION(47),
    CEP_QUERY(100),
    CEP_ACTIVATE(101),
    CEP_UPDATE(102),
    CEP_ACTION(103),
    CEP_REJECT(104),
    UNDEFINED(null);

    private static final Map<Integer, EventType> lookup = new HashMap<Integer, EventType>();
    static {
        for (EventType r: EnumSet.allOf(EventType.class))
          lookup.put(r.getDBValue(), r);
    }

    private Integer dbVal;
    private EventType(Integer dbVal) {
        this.dbVal = dbVal;
    }

    public static EventType getEnum(int dbVal) {
         return getEnum(Integer.valueOf(dbVal));
    }

    public static EventType getEnum(Integer dbVal) {
        return lookup.get(dbVal);
    }

    public Integer getDBValue() {
        return dbVal;
    }
}
