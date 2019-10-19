package shopping.service;

import java.util.List;
import java.util.Map;

import shopping.domain.Inventory;

public interface OrderService {
	String SubmitOrder(List<Map<String ,Object>> cart);
	

}
