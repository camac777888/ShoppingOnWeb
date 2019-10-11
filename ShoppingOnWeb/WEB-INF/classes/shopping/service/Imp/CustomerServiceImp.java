package shopping.service.Imp;

import shopping.dao.CustomerDao;
import shopping.dao.imp.CustomerDaoImpJdbc;
import shopping.domain.Customer;
import shopping.service.CustomerService;
import shopping.service.ServiceException;

public class CustomerServiceImp implements CustomerService {

	CustomerDao customerDao = new CustomerDaoImpJdbc();
	
	@Override
	public boolean login(Customer customer) {
		/*	由表示層傳入customer取出其中id到數據庫按主鍵查詢，查詢結果返回至dbcustomer
		 *	customer 一般只有放置id,password
		 * 	dbcustomer則有全部資料
		 */
		Customer dbcustomer = customerDao.findByPk(customer.getId());
		/*
		 * 	若登入成功將其餘資料回傳至表示層
		 */
		if(dbcustomer.getPassword().equals(customer.getPassword())) {
			customer.setPhone(dbcustomer.getPhone());
			customer.setAddress(dbcustomer.getAddress());
			customer.setName(dbcustomer.getName());
			customer.setBirthday(dbcustomer.getBirthday());
			return true;
		}
		
		
		return false;
	}

	@Override
	public void register(Customer customer) throws ServiceException {
		//查詢id是否存在
		Customer dbcustomer= customerDao.findByPk(customer.getId());
		if(dbcustomer!=null) {
			throw new ServiceException("用戶id:"+customer.getId()+"已存在");
		}
		customerDao.create(customer);
	}

}
