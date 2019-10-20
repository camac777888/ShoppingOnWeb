package shopping.service.Imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import shopping.dao.InventoryDao;
import shopping.dao.OrderDao;
import shopping.dao.ShoppingCartDao;
import shopping.dao.imp.InventoryDaoImpJdbc;
import shopping.dao.imp.OrderDaoImpJdbc;
import shopping.dao.imp.ShoppingCartDaoImpJdbc;
import shopping.domain.Inventory;
import shopping.domain.Orders;
import shopping.domain.ShoppingCart;
import shopping.service.InventoryService;
import shopping.service.OrderService;
@Service
public class OrderServiceImp implements OrderService{
	InventoryDao inventorydao ;
	OrderDao orderdao ;
	ShoppingCartDao shoppingCartdao ;
	InventoryService inventoryService ;
	
	
	public void setInventorydao(InventoryDao inventorydao) {
		this.inventorydao = inventorydao;
	}


	public void setOrderdao(OrderDao orderdao) {
		this.orderdao = orderdao;
	}


	public void setShoppingCartdao(ShoppingCartDao shoppingCartdao) {
		this.shoppingCartdao = shoppingCartdao;
	}


	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}


	@Override
	public String SubmitOrder(List<Map<String, Object>> cart) {
		/*	
		 * 提交訂單流程:
		 * 主要目標就是將購物車內的資訊建立訂單與訂單詳細資訊並導入數據庫中		訂單包含id,orderDate,status,total
		 * 1.建立訂單編號 	id = 下訂單的時間+隨機數字
		 * 2.orderDate 為按下提交訂單按鍵時間	status=1   未付款狀態	total為0(購物車內計算)	將訂單資訊插入數據庫
		 * 3.取出購物車資訊並計算 total	迴圈取出選購的商品及數量並算出total 並將訂單詳細資訊插入數據庫(含有訂單資訊、選購的商品資訊等)
		 * 4.更新數據庫中的訂單資訊(total)
		 */
		
		Date date =new Date();
		Orders orders = new Orders();
		//	orderId=設定為下訂單的時間+隨機數字
		String orderId = String.valueOf(date.getTime())+ String.valueOf((int)(Math.random()*100));
		
		orders.setId(orderId);
		orders.setOrderDate(date);//下訂單的時間
		orders.setStatus(1);//	待付款狀態
		orders.setTotal(0.0f);//在購物車內才計算
			//訂單信息插入數據庫
		orderdao.create(orders); 	
			double total=0.0;
			
		for(Map item : cart) {
			//item結構為 商品id,數量
			Long goodsid=Long.valueOf(String.valueOf(item.get("goodsid"))); 	//	物件轉Long   
			Integer quantity = (Integer) item.get("quantity");
			Inventory inventory =inventorydao.findByPk(goodsid); //	對照商品id取出的商品info
			double subtotal = inventory.getPrice()*quantity; //	小計金額
			total += subtotal;
			
			ShoppingCart shoppingCart =new ShoppingCart ();
			shoppingCart.setQuantity(quantity);
			shoppingCart.setGoods(inventory);
			shoppingCart.setOrders(orders);
			shoppingCart.setSubTotal(subtotal);
		
			//	訂單詳細資料插入數據庫
			shoppingCartdao.create(shoppingCart);
		}
			orders.setTotal(total);
			
			//	更新數據庫
			orderdao.modify(orders);
		return orderId;
	}

	
	
	
}