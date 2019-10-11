package onlinecom.ben.intercepter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import shopping.domain.Customer;

public class MyIntercepter implements HandlerInterceptor{

	//在controller完成之後執行
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

		
	}
	//在controller方法之後執行
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	
	}
//在controller方法之前執行
	/**
	 * 該方法返回值代表是否繼續執行controller方法
	 * true 繼續執行
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		//	承接用戶參數
		String userid = request.getParameter("id");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String birthday = request.getParameter("birthday");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		//	驗證信息
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
		if (address==null||address.equals("")) {
			errors.add("地址不能為空");
		}	
		if (phone==null||phone.equals("")) {
			errors.add("電話不能為空");
		}
		if(errors.size()>0) { //	驗證失敗
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("/customer_reg.jsp").forward(request, response);
		return false;
		} else {
		return true;
	}

}
}
