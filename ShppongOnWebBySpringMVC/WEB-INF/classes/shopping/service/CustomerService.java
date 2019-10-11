package shopping.service;

import shopping.domain.Customer;

public interface CustomerService {

	/*用戶登入方法
	 * @param customer
	 * @return 登入成功為true，失敗為false
	 */
	
	boolean login (Customer customer);
	
	/*用戶註冊方法
	 * @param customers
	 * @throws ServiceException 註冊失敗時拋出異常
	 */
	void register(Customer customer) throws ServiceException;
}
