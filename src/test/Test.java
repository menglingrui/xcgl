

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Test {
   public static void main(String[] strs){
	     long str=1395284236562l;
	   
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date  date=new Date(str);
		
		
		date.toString();
   }
}
