package onlinecom.ben.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class Dateconverter implements Converter<String, Date>{

	@Override	//(String arg0)為表單傳過來的參數
	public Date convert(String birthday) {
		// 字串轉date
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
