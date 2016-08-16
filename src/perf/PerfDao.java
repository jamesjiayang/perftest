package perf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import perf.EventInfo;
import perf.EventType;

public class PerfDao {
    private JdbcTemplate jdbcTemplate;
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    protected List<Map<String, Object>> getOrderList(String start, String end){
       return this.jdbcTemplate.queryForList("select distinct RootOrderId " +
       		"from omsOrderEvent where EventTime between ? and ? ", start, end);
    }
    
    public List<Map<String, Object>> initOrderList(String start, String end) {
        if (start == null || "".equals(end)) {
            throw new RuntimeException("Start time not provided. Program exists...");
        }
        if (end == null || "".equals(end)) {
            end = new SimpleDateFormat().format(Calendar.getInstance().getTime());
        }
        return getOrderList(start, end);
    }
    
    public List<EventInfo> getOrderEvents(Long rootOrderId, String start, String end) {
        return this.jdbcTemplate.query(
                "select * from omsOrderEvent where EventTime between '"+ start + "' and '" + end + "' and RootOrderId= " 
                + rootOrderId.toString(), new EventInfoMapper());
    }
    
    private static final class EventInfoMapper implements RowMapper<EventInfo> {
        public EventInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            EventInfo event = new EventInfo();
            event.setRootOrdID(rs.getLong("RootOrderId"));
            event.setOrderID(rs.getLong("OrderId"));
            event.setEventNum(rs.getInt("EventNumber"));
            event.setEventType(EventType.getEnum(rs.getInt("EventType")));
            event.setEventTime(rs.getTimestamp("InsertTime"));
            event.setClientState(rs.getString("ClientState"));
            return event;
        }
    }
}
