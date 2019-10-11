package shopping.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


	
public interface PreparedStatementCreator {
	PreparedStatement createPreparedStatement(Connection conn) throws SQLException;
	}


