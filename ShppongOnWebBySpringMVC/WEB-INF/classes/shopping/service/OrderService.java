package shopping.service;

import java.util.List;
import java.util.Map;

public interface OrderService {
	String SubmitOrder(List<Map<String ,Object>> cart);
}
