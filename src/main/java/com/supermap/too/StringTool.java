package com.supermap.too;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.sf.json.JSONObject;

/**
 * 
* @ClassName: StringTool 
* @Description: 字符串工具类
* @author yuyao 
* @date 2017年9月20日 下午3:07:45 
*
 */
public class StringTool {
	
	private static SimpleDateFormat defaultYearFormatter = new SimpleDateFormat(
			"yyyy");

	private static SimpleDateFormat defaultDateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static SimpleDateFormat defaultDateTimeFormatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat defaultDateTimeCompactFormatter = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private static SimpleDateFormat defaultDateTimeMillionCompactFormatter = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");


	/**
	 * 获取UUID
	 * @return 处理好的UUID
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

	/**
	 * 获得指定数量的UUID
	 * @param number 指定数量
	 * @return UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
	
	
	/**
	 * 取得系统当前的年份,格式如: (2008)<br>
	 * 
	 * @return String
	 */
	public static String getSystemYearString() {
		return defaultYearFormatter.format(new java.util.Date());
	}

	/**
	 * 取得系统当前的日期字符表达式,格式如: (2008-05-12)<br>
	 * 
	 * @return String
	 */
	public static String getSystemDateString() {
		return defaultDateFormatter.format(new java.util.Date());
	}

	/**
	 * 取得系统当前的日期时间字符表达式,格式如: (2008-05-12 14:28:36)<br>
	 * 
	 * @return String
	 */
	public static String getSystemDateTimeString() {
		return defaultDateTimeFormatter.format(new java.util.Date());
	}

	/**
	 * 取得系统当前的日期时间字符表达式,格式如: (20080512142836)<br>
	 * 
	 * @return String
	 */
	public static String getSystemDateTimeCompactString() {
		defaultDateTimeCompactFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return defaultDateTimeCompactFormatter.format(new java.util.Date());
	}
	
	/**
	 * 取得系统当前的日期时间字符表达式,格式如: (20080512142836)<br> 北京时间
	 * 
	 * @return String
	 */
	public static String getSystemDateTimeCompactStringBjs() {
		return defaultDateTimeCompactFormatter.format(new java.util.Date());
	}

	/**
	 * 取得系统当前的日期时间字符表达式(带毫秒数),格式如: (20080512142836326)<br>
	 * 
	 * @return String
	 */
	public static String getSystemDateTimeMillionCompactString() {
		return defaultDateTimeMillionCompactFormatter
				.format(new java.util.Date());
	}
	
	
	/**
	 * 获取当前日期与周一相差的天数
	 */
	public static int getMondayPlus() {
		Calendar day = Calendar.getInstance();
		int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) { // 一周中第一天（周日）
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	/**
	 * 获得当天的起始时间
	 */
	public static Calendar getStartDate(Calendar today) {
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		return today;
	}

	/**
	 * 获取当天截止时间
	 */
	public static Calendar getEndDate(Calendar endToday) {
		endToday.set(Calendar.HOUR_OF_DAY, 23);
		endToday.set(Calendar.MINUTE, 59);
		endToday.set(Calendar.SECOND, 59);
		endToday.set(Calendar.MILLISECOND, 59);
		return endToday;
	}

	/**
	 * 获得当月起始时间
	 */
	public static Calendar getStartMounth(Calendar today) {
		Calendar calendar = getStartDate(today);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar;
	}

	/**
	 * 获得当月结束时间
	 */
	@SuppressWarnings("static-access")
	public static Calendar getEndMounth(Calendar endToday) {
		Calendar endMounth = getEndDate(endToday);
		endMounth.set(Calendar.DAY_OF_MONTH,
				endMounth.getActualMaximum(endMounth.DAY_OF_MONTH));
		return endMounth;
	}

	/**
	 * 获取当前季度 起始时间
	 */
	public static Calendar getStartQuarter(Calendar today) {
		int currentMonth = today.get(Calendar.MONTH) + 1;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				today.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				today.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				today.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				today.set(Calendar.MONTH, 9);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
				today.get(Calendar.DATE), 0, 0, 0);
		return today;
	}

	/**
	 * 获取当季的结束时间
	 */
	public static Calendar getEndQuarter(Calendar today) {
		int currentMonth = today.get(Calendar.MONTH) + 1;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				today.set(Calendar.MONTH, 2);
				today.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				today.set(Calendar.MONTH, 5);
				today.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				today.set(Calendar.MONTH, 8);
				today.set(Calendar.DATE, 30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				today.set(Calendar.MONTH, 11);
				today.set(Calendar.DATE, 31);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
				today.get(Calendar.DATE), 23, 59, 59);
		return today;
	}

	/**
	 * 获取当年起始时间
	 */
	public static Calendar getStartYear(Calendar today) {
		try {
			today.set(Calendar.MONTH, 0);
			today.set(Calendar.DAY_OF_MONTH,
					today.getActualMinimum(Calendar.DAY_OF_MONTH));
			
			today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
					today.get(Calendar.DATE), 0, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return today;
	}

	/**
	 * 获取当年结束时间
	 */
	public static Calendar getEndYear(Calendar today) {
		try {
			today.set(Calendar.MONTH, 11);
			today.set(Calendar.DAY_OF_MONTH,
					today.getMaximum(Calendar.DAY_OF_MONTH));
			
			today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
					today.get(Calendar.DATE), 23, 59, 59);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return today;
	}
	
	/**
	 * 
	* @Title: isNull 
	* @Description: 判断字符串是否为空
	* @param @param str
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean isNotNull(final CharSequence cs) {
		
		if(cs == null || cs.length() == 0 || "".equals(cs))return false;
		
		return true;
	}
	/**
	 * 
	* @Title: getMD5 
	* @Description: MD5加密
	* @param @param str
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	 public static String getMD5(String str) {  
	        try {  
	            // 生成一个MD5加密计算摘要  
	            MessageDigest md = MessageDigest.getInstance("MD5");  
	            // 计算md5函数  
	            md.update(str.getBytes());  
	            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
	            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
	            return new BigInteger(1, md.digest()).toString(16);  
	        } catch (Exception e) {  
	           System.out.println("MD5转换异常！");
	           return null;  
	        }  
	    }  
	 
	 	/**
		* @Title: generateFileName 
		* @Description: 通过时间加随机数 获取文件名称 
		* @param @return    设定文件 
		* @return String    返回类型 
		* @throws
		 */
		public static String getFileName() {
			// 获得当前时间
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			// 转换为字符串
			String formatDate = format.format(new Date());
			// 随机生成文件编号
			int random = new Random().nextInt(10000);
			return new StringBuffer().append(formatDate).append(random).toString();
		}
	 
	/**
	 * 将JSONObjec对象转换成Map-List集合
	 * @param json
	 * @return
	 */
	 @SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(JSONObject json){
	    Map<String, Object> map = new HashMap<String, Object>();
		Set<Entry<String, JsonElement>> entrySet = json.entrySet();
	    for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ){
	    	Entry<String, JsonElement> entry = iter.next();
	    	String key = entry.getKey();
	    	Object value = entry.getValue();
	        if(value instanceof JsonObject)
	            map.put((String) key, toMap((JSONObject) value));
	        else
	            map.put((String) key, value);
	    }
	    return map;
	}
	
	/**
	 * 
	* @Title: jsonzh
	* @Description: 去掉JSON参数中的\符号
	* @param @param json
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String jsonzh(String json) {
		
		String sj = json.replace("\\", "");
		
		return sj;
	}
	/**
	* @Title: getURLEncoder 
	* @Description: 参数转换
	* @param @param v
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getURLEncoder(String v) {
		v = v.replace("\"", "'");
		try {
			return URLEncoder.encode(v, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("参数转换异常："+v);
		}
		return null;
	}
	/**
	* @Title: getIpAddress 
	* @Description: 获取请求IP
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getIpAddress(HttpServletRequest request) {  
         String ip = request.getHeader("x-forwarded-for");  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("Proxy-Client-IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("WL-Proxy-Client-IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("HTTP_CLIENT_IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getRemoteAddr();  
         }  
         return ip;  
     }  
	
}
