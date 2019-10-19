package shopping.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import shopping.dao.CustomerDao;
import shopping.domain.Customer;




public final class DBConnect {
	
	static final JdbcTemplate temp ;
	
	
	static {
		
		//創建jdbctemplate對象
		 temp = new JdbcTemplate(); 
	
		//注入數據源
		//創建spring提供數據源
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/shoppingonlinemall?serverTimezone=UTC&characterEncoding=utf-8");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("12345");
		//使用模板
		temp.setDataSource(dataSource);

	}
	}

