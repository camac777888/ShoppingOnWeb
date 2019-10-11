  package shopping.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shopping.domain.Customer;
import shopping.domain.Inventory;
import shopping.service.CustomerService;
import shopping.service.InventoryService;
import shopping.service.OrderService;
import shopping.service.ServiceException;
import shopping.service.Imp.CustomerServiceImp;
import shopping.service.Imp.InventoryServiceImp;
import shopping.service.Imp.OrderServiceImp;

/**
 * Servlet implementation class Controller
 */
//@WebServlet(name="Controller",urlPatterns= {"/controller"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
      private 	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
      private 	CustomerService customerService =new CustomerServiceImp();
      private 	InventoryService inventoryService =new InventoryServiceImp();
      private 	OrderService orderService =new OrderServiceImp();
      private int totalPageNumber = 0;  //	總頁數
      private int pageSize ;			//	每頁行數
      private int currentPage = 1;		//	當前頁數默認為第一頁開始
      
      @Override
    public void init(ServletConfig config) throws ServletException {	//	利用配置xml參數來傳遞	pageSize=20
    	
    	super.init(config);
    	pageSize =new Integer(config.getInitParameter("pageSize"));  //	字串轉int
    }
      
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String action = request.getParameter("action");
		if ("reg".equals(action)) {
			//----------------用戶註冊---------------------------
			String userid = request.getParameter("userid");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
			String birthday = request.getParameter("birthday");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			//----------------驗證信息---------------------------
			List<String> errors = new ArrayList<>();
			if (userid==null||userid.equals("")) {
				errors.add("用戶帳號不能為空");
			}
			if (name==null||name.equals("")) {
				errors.add("用戶姓名不能為空");
			}
			if (password==null
					||password2==null
					||!password.equals(password2)) {
				errors.add("兩次密碼不一致!");
			}		//	檢查日期型態 (YYYY/MM/DD)
			String pattern= "^((((19|20)(([02468][048])|([13579][26]))-02-29))|((20[0-9][0-9])|(19[0-9][0-9]))-((((0[1-9])|(1[0-2]))-((0[1-9])|(1\\d)|(2[0-8])))|((((0[13578])|(1[02]))-31)|(((0[1,3-9])|(1[0-2]))-(29|30)))))$";
			if (!Pattern.matches(pattern, birthday)){
				errors.add("出生日期格式無效");
			}
			if(errors.size()>0) { //	驗證失敗
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("customer_reg.jsp").forward(request, response);;
			}else {				  //	驗證成功	
				Customer customer =new Customer();
				customer.setId(userid);
				customer.setName(name);
				customer.setPassword(password);
				customer.setPhone(phone);
				customer.setAddress(address);
				try {					
					Date d = dateformat.parse(birthday);
					customer.setBirthday(d);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
								  //註冊
				try {
					customerService.register(customer);
					request.getRequestDispatcher("login.jsp").forward(request, response);
				} catch (ServiceException e) {
					errors.add("用戶id已存在");
					request.setAttribute("errors", errors);
					request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
				}							
				}			
		}	else if("login".equals(action)) {
			//	用戶登入
			String userid = request.getParameter("userid");
			String password = request.getParameter("password");
			
			Customer customer = new Customer();
			customer.setId(userid);
			customer.setPassword(password);
			
			if (customerService.login(customer)) {
				//	登入成功 將用戶信息放入seesion
				HttpSession session = request.getSession();
				session.setAttribute("customer", customer);
				request.getRequestDispatcher("main.jsp").forward(request, response);
			}else { //	登入失敗
				List<String> error = new ArrayList<>();
				error.add("帳號密碼不正確");
				request.setAttribute("errors", error);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			
		}else if("list".equals(action)) {
			List<Inventory> goodlist = inventoryService.SearchInventoryAll();
			
			if((goodlist.size()%pageSize)==0) {					//	資料總數/每頁行數  若整除則為總頁數 若不整除總頁數則加一
				totalPageNumber=goodlist.size()/pageSize;
			}else {
				totalPageNumber=(goodlist.size()/pageSize)+1;
			}
			request.setAttribute("totalPageNumber", totalPageNumber);
			request.setAttribute("currentPage", currentPage);
			
			int start = (currentPage-1)*pageSize;//	(當前頁數-1)*每頁行數
			int end = currentPage*pageSize;//	當前頁數*每頁行數 
			if (currentPage==totalPageNumber) {// 最後一頁(避免超出邊界問題)
				end= goodlist.size();  
			}
			
			request.setAttribute("goodlist", goodlist.subList(start,end));  //	當前頁數*每頁行數
			request.getRequestDispatcher("goods_list.jsp").forward(request, response);
			
		}else if("paging".equals(action)) {
			//	商品列表分頁
			String page = request.getParameter("page"); //	傳回當前頁數
				if(page.equals("prev")){ //上一頁
					if(currentPage>1) {//	當前頁數大於第一頁則執行
					currentPage--;
					}
				}
				else if(page.equals("next")){ //下一頁
						if(currentPage<totalPageNumber) { //	當前頁數小於總頁數則執行
						currentPage++;
						}
					}
				else {
			currentPage = Integer.valueOf(page);
				}
			int start = (currentPage-1)*pageSize;//	(當前頁數-1)*每頁行數
			int end = currentPage*pageSize;//	當前頁數*每頁行數
			
			List<Inventory> goodlist = inventoryService.SearchByStartEnd(start, end);
			
			request.setAttribute("totalPageNumber", totalPageNumber);
			request.setAttribute("currentPage", currentPage);		
			request.setAttribute("goodlist", goodlist); 
			request.getRequestDispatcher("goods_list.jsp").forward(request, response);
			
		}else if("detail".equals(action)) {
			//	查看商品詳細
			String goodsid = request.getParameter("id");
			Inventory inventory= inventoryService.InventoryDetail(new Long(goodsid));
			
			request.setAttribute("inventory",inventory);
			request.getRequestDispatcher("goods_detail.jsp").forward(request, response);
		
		}else if("add".equals(action)) {
			//-----	加入購物車
			Long goodsid =new Long(request.getParameter("id"));
			String goodsname = request.getParameter("name");
			Float price = new Float(request.getParameter("price"));
			
			//	購物車結構是list中包含map，每個Map是一個商品
			//	從session中取出購物車
			List<Map<String,Object>> cart =(List<Map<String, Object>>) request.getSession().getAttribute("cart");	
			
			if (cart==null) { //	第一次取出為null
				cart = new ArrayList<Map<String,Object>>();
				request.getSession().setAttribute("cart", cart);	
			}
			
			 int flag = 0;	
			 //	           購物車中已有選擇的商品
	            for (Map<String, Object> item : cart) {	               
	                Long goodsid2=Long.valueOf(String.valueOf(item.get("goodsid")));     
	                if (goodsid.equals(goodsid2)) {
	                    Integer quantity = (Integer) item.get("quantity");
	                    quantity++;
	                    item.put("quantity", quantity);
	                    flag++;
	                }
	            }
	            	if (flag == 0) {//	購物車中沒有已選擇的商品
	                Map<String, Object> item = new HashMap<>();
	                // item結構為Map [商品id，商品名稱，價格，數量]
	                item.put("goodsid", goodsid);
	                item.put("goodsname", goodsname);
	                item.put("quantity", 1);
	                item.put("price", price);
	                //	加入購物車
	                cart.add(item);
	            }
	            
	            System.out.println(cart);	//	後台觀察
	            String pagename = request.getParameter("pagename");
	            
	            if (pagename.equals("list")) {	//	判斷為哪個頁面請求的來執行跳轉回該頁面
	                int start = (currentPage - 1) * pageSize;
	                int end = currentPage * pageSize;

	                List<Inventory> goodslist = inventoryService.SearchByStartEnd(start,end);

	                request.setAttribute("totalPageNumber", totalPageNumber);
	                request.setAttribute("currentPage", currentPage);
	                request.setAttribute("goodlist", goodslist);
	                request.getRequestDispatcher("goods_list.jsp").forward(request,response);

	            } else if (pagename.equals("detail")) {

	            	Inventory inventory = inventoryService.InventoryDetail(new Long(goodsid));
	                request.setAttribute("inventory", inventory);
	                request.getRequestDispatcher("goods_detail.jsp").forward(request, response);
	            }
	            
	            
	        }else if("cart".equals(action)) {
	        	//查看購物車
	        	
	        	double	total = 0.0;
	        	//	        	從session中取出購物車
				List<Map<String,Object>> cart =(List<Map<String, Object>>) request.getSession().getAttribute("cart");
						if (cart !=null) {
							 for (Map<String, Object> item : cart) {
					            	
					            	 Integer quantity = (Integer) item.get("quantity");
					            	Float price = (Float) item.get("price");
					                double subtotal = price * quantity;
					                total +=subtotal;
							 }
						}
						request.setAttribute("total", total);
						request.getRequestDispatcher("cart.jsp").forward(request, response);
						
	        }else if("delete".equals(action)) {	
	        			//	購物車中刪除項目
	        	Long goodsid =new Long(request.getParameter("id"));
	        	/*
	        	 * 1.取出session中的cart，並進行比對商品id，取出的同時放入另一個cart1
	        	 * 2.比對出的商品id，若相符則用continue跳過迴圈繼續輸出
	        	 * 3.刪除原有的cart，以新的cart代替
	        	 * 
	        	 */
	        	
	        	List<Map<String,Object>> cart =(List<Map<String, Object>>) request.getSession().getAttribute("cart");
	        	List<Map<String,Object>> cart1 = new ArrayList<Map<String,Object>>();
	        	double	total = 0.0;
				if (cart !=null) {
					 for (Map<String, Object> item : cart) {
						 Long goodsid2=Long.valueOf(String.valueOf(item.get("goodsid")));     
			                if (goodsid.equals(goodsid2)) {
			                	continue ;}
			                
			                Integer quantity = (Integer) item.get("quantity");
			            	Float price = (Float) item.get("price");
			                double subtotal = price * quantity;
			                total +=subtotal;
			                cart1.add(item);
					 }  
					 
					 request.getSession().removeAttribute("cart");

						request.setAttribute("total", total);
						
						request.getSession().setAttribute("cart", cart1);
								
						request.getRequestDispatcher("cart.jsp").forward(request, response);
				}
						
	        }else if("sub_ord".equals(action)) {
	        	//				提交訂單
	        	//	        	從session中取出購物車
				List<Map<String,Object>> cart =(List<Map<String, Object>>) request.getSession().getAttribute("cart");

				for (Map<String, Object> item : cart) {
					String str=String.valueOf(item.get("goodsid"));  
					Long goodsid=Long.valueOf(str);									
					String strquantity=request.getParameter("quantity_"+goodsid);
					int quantity=0;
					try {
						quantity= new Integer(strquantity);
					}catch (Exception e ) {
						
					}
					item.put("quantity", quantity);
					
				}
				//	提交訂單
				String orderid = orderService.SubmitOrder(cart);
				request.setAttribute("orderid", orderid);
				request.getRequestDispatcher("order_finish.jsp").forward(request, response);
				//	清空購物車
				request.getSession().removeAttribute("cart");
		}else if("main".equals(action)) {
			request.getRequestDispatcher("main.jsp").forward(request, response);

		}else if("logout".equals(action)) {
			//	清除購物車
			request.getSession().removeAttribute("cart");
			//	清除用戶信息
			request.getSession().removeAttribute("customer");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else if("reg_init".equals(action)) {
			//用戶註冊頁面進入
			request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
		}
			
	}
	}

