package shopping.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class jdbcTemplate {
	/*
	 * 針對數據庫的CRUD設計模板 減少代碼重複性
	 * 分兩種方法 查詢 與 修改
	 * 
	 * 
	 */
	public static int count ;
		public void search(PreparedStatementCreator pscreater,RowCallHandler callbackHandler)
		throws DataAccessException{
			Connection  connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet   resultSet  = null;
			try {
			connection =DBHelper.getconnection(); //加載驅動與數據庫連接
			preparedStatement = pscreater.createPreparedStatement(connection);
			resultSet = preparedStatement.executeQuery();	//查詢用executeQuery(sql)
			
			
			while(resultSet.next()) {	
				callbackHandler.processRow(resultSet);	//執行查詢
			}
			
			}
			
		catch(SQLException e) {
			throw new  DataAccessException("jdbcTemplate的SQLException",e);
		}
		catch(ClassNotFoundException e) {
			throw new  DataAccessException("jdbcTemplate的ClassNotFoundException",e);
				
			}finally {
			if (connection !=null) {
				try {
				connection.close();
				} catch (SQLException e) {
					throw new  DataAccessException("jdbcTemplate的connection.close()失敗",e);
				}
				if (preparedStatement !=null) {
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						throw new  DataAccessException("jdbcTemplate的preparedStatement.close()失敗",e);
					}
					if (resultSet !=null) {
						try {
							resultSet.close();
						} catch (SQLException e) {
							throw new  DataAccessException("jdbcTemplate的resultSet.close()失敗",e);
						}
			}
		}
		}
	
	}
			
		
}
		public void update(PreparedStatementCreator pscreater) 
				throws DataAccessException{
			Connection  connection = null;
			PreparedStatement preparedStatement = null;
			
			try {
			connection =DBHelper.getconnection();
			preparedStatement = pscreater.createPreparedStatement(connection);
			count =  preparedStatement.executeUpdate();
			System.out.printf("變更%d筆資料 ",count);	
			}
			catch(SQLException e) {
				throw new  DataAccessException("jdbcTemplate的SQLException",e);
			}
			catch(ClassNotFoundException e) {
				throw new  DataAccessException("jdbcTemplate的ClassNotFoundException",e);
					
				}finally {
				if (connection !=null) {
					try {
					connection.close();
					} catch (SQLException e) {
						throw new  DataAccessException("jdbcTemplate的connection.close()失敗",e);
					}
					if (preparedStatement !=null) {
						try {
							preparedStatement.close();
						} catch (SQLException e) {
							throw new  DataAccessException("jdbcTemplate的preparedStatement.close()失敗",e);
						}
			}
			}
			
			
		}
		}		
}