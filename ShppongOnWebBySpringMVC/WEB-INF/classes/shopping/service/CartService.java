package shopping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shopping.domain.Inventory;

public interface CartService {
	void cartjudgement(List<Map<String, Object>> cart,Long id,Inventory inventory);
	List<Map<String,Object>> cartdelete(List<Map<String, Object>> cart, Long id,double total);
}
