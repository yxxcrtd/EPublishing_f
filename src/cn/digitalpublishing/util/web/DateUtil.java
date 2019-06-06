package cn.digitalpublishing.util.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @description 对日期的工具类
 * 
 * @author gongguanghui
 * 
 */
public class DateUtil {
	/**
	 * 获取当前日期
	 */
	public static String getNowDate(String format) {
		Date nowDate = new Date();//获取当前日期
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);//定义日期格式
		return dateFormat.format(nowDate);//返回获取的日期
	}
	/**
	 * 获取当前日期
	 */
	public static String getNowDate(String format,Date date) {
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);//定义日期格式
		return dateFormat.format(date);//返回获取的日期
	}

	/**
	 * 获取当前日期
	 */
	public static String getDateStr(String format, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);//定义日期格式
		return dateFormat.format(date);//返回获取的日期
	}

	/**
	 * 根据日期的字符串封装为Date对象
	 */
	public static Date getUtilDate(String datestr, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);//定义日期格式
		Date date = null;
		try {
			date = dateFormat.parse(datestr);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			//e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取昨天的日期
	 */
	public static String getYesterDayDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.add(Calendar.DATE, -1);
		return dateFormat.format(nowdate.getTime());//返回获取的日期
	}

	/**
	 * 获取日期的上个月当天(yyyy-MM-dd)
	 */
	public static String getLastMonthDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.add(Calendar.MONTH, -1);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的下个月当天(yyyy-MM-dd)
	 */
	public static String getNextMonthDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.add(Calendar.MONTH, 1);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的去年当天(yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastYearDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.add(Calendar.YEAR, -1);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的明年当天(yyyy-MM-dd)
	 */
	public static String getNextYearDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.add(Calendar.YEAR, 1);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的明年当天(yyyy-MM-dd)
	 */
	public static String addYearDate(Date date, int add) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.add(Calendar.YEAR, add);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的本周周一(yyyy-MM-dd)
	 */
	public static String getWeekMonday(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.set(Calendar.DAY_OF_WEEK, 2);//获取周一

		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的上周周一(yyyy-MM-dd)
	 */
	public static String getBeforeWeekMonday(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.set(Calendar.DAY_OF_WEEK, 2);//获取周一
		nowdate.set(Calendar.DAY_OF_MONTH, nowdate.get(Calendar.DAY_OF_MONTH) - 7);//获取上周

		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的上周周末(yyyy-MM-dd)
	 */
	public static String getBeforeWeekSunday(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.add(Calendar.WEEK_OF_MONTH, 1);
		nowdate.set(Calendar.DAY_OF_WEEK, 1);
		nowdate.set(Calendar.DAY_OF_MONTH, nowdate.get(Calendar.DAY_OF_MONTH) - 7);//获取上周
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的本月月初(yyyy-MM-dd)
	 */
	public static String getMonthStartDay(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.set(Calendar.DAY_OF_MONTH, 2);
		nowdate.add(Calendar.DAY_OF_MONTH, -1);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的本月月末(yyyy-MM-dd)
	 */
	public static String getMonthEndDay(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.set(Calendar.MONTH, nowdate.get(nowdate.MONTH) + 1);
		nowdate.set(Calendar.DATE, 1);
		nowdate.add(Calendar.DATE, -1);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的上月月初(yyyy-MM-dd)
	 */
	public static String getBeforeMonthStartDay(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.set(Calendar.DAY_OF_MONTH, 2);
		nowdate.add(Calendar.DAY_OF_MONTH, -1);
		nowdate.set(Calendar.MONTH, nowdate.get(Calendar.MONTH) - 1);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的上月月末(yyyy-MM-dd)
	 */
	public static String getBeforeMonthEndDay(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.set(Calendar.DAY_OF_MONTH, 1);
		nowdate.add(Calendar.DAY_OF_MONTH, -1);
		//		nowdate.set(Calendar.MONTH,nowdate.get(Calendar.MONTH)-1);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 两天之间的间隔天数
	 */
	public static int dayDiff(Date date1, Date date2) {
		long diffMillseconds = date1.getTime() - date2.getTime();
		int diffDay = (int) (diffMillseconds / 1000 / 60 / 60 / 24);
		return diffDay;
	}

	/**
	 * 两个时间之间的间隔时长
	 */
	public static int timeDiff(Date date1, Date date2) {
		long diffMillseconds = date1.getTime() - date2.getTime();
		int diffTime = (int) (diffMillseconds / 1000 / 60);
		return diffTime;
	}

	/**
	 * 获取日期处理后的日期
	 */
	public static Date getDealedDate(Date date, int addYear, int addMonth, int addDay, int addHour, int addMinute, int addSecond) {
		Calendar nowdate = new GregorianCalendar();
		nowdate.setTime(date);
		nowdate.add(Calendar.YEAR, addYear);
		nowdate.add(Calendar.MONTH, addMonth);
		nowdate.add(Calendar.DAY_OF_MONTH, addDay);
		nowdate.add(Calendar.HOUR_OF_DAY, addHour);
		nowdate.add(Calendar.MINUTE, addMinute);
		nowdate.add(Calendar.SECOND, addSecond);
		return nowdate.getTime();
	}

	/**
	 * 获取日期的第几周（周报固定值）
	 * (yyyy-MM-WW) WW指01-05
	 */
	public static String getMonthWeek(Date date) {
		Calendar nowdate = new GregorianCalendar();
		nowdate.setTime(date);
		nowdate.set(Calendar.DAY_OF_WEEK, 6);//设置周五
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-WW");//定义日期格式
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 获取日期的N个月后当天日期(yyyy-MM-dd)
	 */
	public static String getEndMonthDate(Date date, int N) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		Calendar nowdate = new GregorianCalendar();
		nowdate.add(Calendar.MONTH, N);
		return dateFormat.format(nowdate.getTime());
	}

	/**
	 * 测试静态方法
	 */
	public static void main(String[] args) {
		Date d = getDealedDate(new Date(), 0, 0, -7, 0, 0, 0);

		// TODO 自动生成方法存根
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式

		System.out.println(dateFormat.format(d));
		System.out.println(getMonthStartDay(null));

		System.out.println(getMonthStartDay(new Date()));
		System.out.println(getMonthEndDay(new Date()));
	}

	/**
	 * 获取当前时间多少分钟前的时间(返回时间)
	 * @param min
	 * @return
	 */
	public static Date getDateBeforeMinutes(int min) {
		Calendar nowdate = new GregorianCalendar();
		nowdate.add(Calendar.MINUTE, -min);
		return nowdate.getTime();//返回获取的日期
	}
}
