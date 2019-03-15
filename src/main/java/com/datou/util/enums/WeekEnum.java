package com.datou.util.enums;
/**
 * 与周相关的枚举类
 * 
 * @author anbaihong
 * @date 2019年3月15日 上午11:51:41
 * @comment 
 */
public enum WeekEnum {
	/**
	 * 星期一
	 */
	MONDAY(2, "星期一"),
	/**
	 * 星期二
	 */
	TUESDAY(3, "星期二"),
	/**
	 * 星期三
	 */
	WEDNESDAY(4, "星期三"),
	/**
	 * 星期四
	 */
	THURSDAY(5, "星期四"),
	/**
	 * 星期五
	 */
	FRIDAY(6, "星期五"),
	/**
	 * 星期六
	 */
	SATURDAY(7, "星期六"),
	/**
	 * 星期日
	 */
	SUNDAY(1, "星期日");

	private int index;
	private String week;

	/**
	 * 根据index 获取中文的周几
	 * 
	 * @param index
	 * @return
	 */
	public static String week(int index) {
		for (WeekEnum weekEnumation : WeekEnum.values()) {
			if (index == weekEnumation.getIndex()) {
				return weekEnumation.getWeek();
			}
		}
		return null;
	}

	WeekEnum(int index, String week) {
		this.index = index;
		this.week = week;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
}
