package shopping.service.Imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import shopping.domain.Inventory;
import shopping.service.CartService;

@Service
public class CartServiceImp implements CartService{

	@Override
	public void cartjudgement(List<Map<String, Object>> cart, Long id, Inventory inventory) {
		
			int flag = 0;								//判定購物車內是否有同種商品
	        for (Map<String, Object> item : cart) {	   	
	            Long goodsid2=Long.valueOf(String.valueOf(item.get("goodsid")));     
	            if (id.equals(goodsid2)) {				//購物車中有同種商品
	                Integer quantity = (Integer) item.get("quantity");	
	                quantity++;
	                item.put("quantity", quantity);
	                flag++;
	            }
	        }
	        	if (flag == 0) {										//	購物車中沒有同種商品
	            Map<String, Object> item = new HashMap<>();												
	            item.put("goodsid", id);
	            item.put("goodsname",inventory.getName());
	            item.put("quantity", 1);
	            item.put("price",inventory.getPrice());
	            cart.add(item); 		
	        }
	        	 System.out.println(cart);
					
				
	
}

	@Override
	public ArrayList<Map<String,Object>> cartdelete(List<Map<String, Object>> cart, Long id,double total) {
		List<Map<String,Object>> cart1 = new ArrayList<Map<String,Object>>();
		
		if (cart !=null) {
			 for (Map<String, Object> item : cart) {
				 Long goodsid2=Long.valueOf(String.valueOf(item.get("goodsid")));     
			        if (id.equals(goodsid2)) {
			        	System.out.println("刪除:"+item);
			        	continue ;
			        	}
			   	 Integer quantity = (Integer) item.get("quantity");
		        	Double price = (Double) item.get("price");
		            double subtotal = price * quantity;
		            total +=subtotal;
			        cart1.add(item);
			 }  
		
	}
		return (ArrayList<Map<String, Object>>) cart1;
	

}
}
