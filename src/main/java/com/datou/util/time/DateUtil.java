package com.datou.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.datou.util.enums.DateEnum;
import com.datou.util.enums.WeekEnum;

/**
 * 日期操作的工具类
 * 
 * @author anbaihong
 * @date 2019年3月15日 上午10:47:38
 * @comment 
 */
public final class DateUtil {

	public DateUtil() {
		
	}
	
	/**
	 * 将 java.util.Date 日期格式化成 yyyy-MM-dd HH:mm:ss 格式的字符串
	 * 
	 * @param date
	 *            要被格式化的日期
	 * @return
	 */
	public static String format(Date date){
		String result = null;
		try {
			result = format(date, DateEnum.DEFAULT_FORMAT.getFormat());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	/**
	 * 将 java.util.Date 日期格式化指定 format 格式的字符串
	 * 
	 * @param date
	 *            要被格式化的日期
	 * @param format
	 *            指定时间的格式
	 * @return
	 */
	public static String format(Date date, String dateFormat) {
		return new SimpleDateFormat(dateFormat).format(date);
	}

	/**
	 * 将默认日期格式的字符串转成日期对象
	 * 
	 * @param dateStr
	 *            日期格式的字符串
	 * @return Date
	 */
	public static Date parse(String dateStr){
		return parse(dateStr,DateEnum.DEFAULT_FORMAT.getFormat());
	}
	
	/**
	 * 将日期格式的字符串转成日期对象
	 * 
	 * @param dateStr
	 *            日期格式的字符串
	 * @param dateFormat
	 *            该日期的格式
	 * @return Date
	 */
	private static Date parse(String dateStr, String dateFormat) {
		Date date = null;
		try{
			date = new SimpleDateFormat(dateFormat).parse(dateStr);
		} catch (ParseException e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取当前时间的默认字符串格式的字符串表现形式
	 * 
	 * @return
	 */
	public static String currentDate(){
		return format(new Date());
	}
	
	/**
	 * 获取当前时间的指定字符串格式的字符串表现形式
	 * 
	 * @param format
	 *            时间的字符串格式
	 * @return
	 */
	public static String currentDate(String format){
		return format(new Date(), format);
	}	
	
	/**
	 * 获取当前时刻的天的起始时间
	 * 
	 * @return
	 */
	public static String startDay(){
		return format(new Date(), DateEnum.START_DAY_FORAMT.getFormat());
	}
	
	/**
	 * 获取指定时间的天的起始时间
	 * 
	 * @param date
	 *            指定的时间
	 * @return
	 */
	public static String startDay(Date date){
		return format(date, DateEnum.START_DAY_FORAMT.getFormat());
	}
	
	/**
	 * 获取当前时刻的天的结束时间
	 * 
	 * @return
	 */
	public static String endDay(){
		return format(new Date(), DateEnum.END_DAY_FORMAT.getFormat());
	}

	/**
	 * 获取指定时间的天的结束时间
	 * 
	 * @param date
	 *            指定的时间
	 * @return
	 */
	public static String endDay(Date date){
		return format(date, DateEnum.END_DAY_FORMAT.getFormat());
	}
	
	/**
	 * 获取指定时间的向前指定天数计算后的时间
	 * 
	 * @param date
	 *            指定的要计算的时间
	 * @param day
	 *            向前的天数
	 * @return
	 */
	public static Date beforeDay(Date date, int day){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -day);

		return calendar.getTime();
	}

	/**
	 * 获取指定时间的向前指定天数计算后的时间的字符串
	 * 
	 * @param date
	 *            要计算的时间
	 * @param day
	 *            向前的天数
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String beforeDay(Date date, int day, String format){
		return format(beforeDay(date, day), format);
	}

	/**
	 * 获取指定时间的向前指定小时计算后的时间
	 * 
	 * @param date
	 *            要计算的时间
	 * @param hour
	 *            向前计算的小时
	 * @return
	 */
	public static Date beforeHour(Date date, int hour){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, -hour);

		return calendar.getTime();
	}

	/**
	 * 获取指定时间的向前指定小时计算后的时间的指定格式的字符串
	 * 
	 * @param date
	 *            要计算的时间
	 * @param hour
	 *            向前计算的小时
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String beforeHour(Date date, int hour, String format){
		return format(beforeHour(date, hour), format);
	}

	/**
	 * 获取指定时间的向前指定月数计算后的时间
	 * 
	 * @param date
	 *            要计算的时间
	 * @param month
	 *            向前的月数
	 * @return
	 */
	public static Date beforeMonth(Date date, int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -month);

		return calendar.getTime();
	}

	/**
	 * 获取指定时间的向前指定月数计算后的时间的字符串
	 * 
	 * @param date
	 *            要计算的时间
	 * @param month
	 *            向前的月数
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String beforeMonth(Date date, int month, String format){
		return format(beforeMonth(date, month), format);
	}

	/**
	 * 获取指定时间是周几
	 * 
	 * @param date
	 *            要计算的时间
	 * @return
	 */
	public static int week(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取指定时间的周几
	 * 
	 * @param date
	 *            要计算的时间
	 * @return
	 */
	public static String weekChinese(Date date){
		return WeekEnum.week(week(date));
	}

	/**
	 * 获取指定的起始时间之间的各个天的日期
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static List<Date> spanDay(Date startDate, Date endDate){
		List<Date> spanDateList = new ArrayList<Date>();
		while (startDate.before(endDate)){
			spanDateList.add(startDate);
			startDate = beforeDay(startDate, -1);
		}
		return spanDateList;
	}

	/**
	 * 获取指定起始时间之间的每小时的日期
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static List<Date> spanHour(Date startDate, Date endDate){
		List<Date> spanDateList = new ArrayList<Date>();
		while (startDate.before(endDate)){
			spanDateList.add(startDate);
			startDate = beforeHour(startDate, -1);
		}
		return spanDateList;
	}	
	
	
	
}
