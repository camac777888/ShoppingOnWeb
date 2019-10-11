package shopping.core;

import java.sql.ResultSet;
import java.sql.SQLException;
	
public interface RowCallHandler {
	void processRow(ResultSet rs) throws SQLException;
}
