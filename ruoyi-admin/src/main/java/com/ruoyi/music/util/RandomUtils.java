package com.ruoyi.music.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
 
/**
 * 随机数工具类
 */
public class RandomUtils {
 
	private static final Random random = new Random();
 
	private static final DecimalFormat fourdf = new DecimalFormat("0000");
 
	private static final DecimalFormat sixdf = new DecimalFormat("000000");
 
	private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBER_CHAR = "0123456789";
 
	/**
	 * 获取定长的随机数，包含大小写、数字
	 * @param length 随机数长度
	 * @return
	 */
	public static String getGenerateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
		}
		return sb.toString();
	}
 
	/**
	 * 获取定长的随机数,包含大小写字母
	 * @param length 随机数长度
	 * @return
	 */
	public static String getGenerateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
		}
		return sb.toString();
	}
 
	/**
	 * 获取定长的随机数，只包含小写字母
	 * @param length 随机数长度
	 * @return
	 */
	public static String getGenerateLowerString(int length) {
		return getGenerateMixString(length).toLowerCase();
	}
 
	/**
	 * 获取定长的随机数,只包含大写字母
	 * @param length 随机数长度
	 * @return
	 */
	public static String getGenerateUpperString(int length) {
		return getGenerateMixString(length).toUpperCase();
	}
 
	/**
	 * 获取定长的随机数,只包含数字
	 * @param length 随机数长度
	 * @return
	 */
	public static String getGnerateNumberString(int length){
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
		}
		return sb.toString();
	}
 
	/**
	 * 获取四位随机数
	 * @return
	 */
	public static String getFourBitRandom() {
		return fourdf.format(random.nextInt(10000));
	}
 
	/**
	 * 获取六位随机数
	 * @return
	 */
	public static String getSixBitRandom() {
		return sixdf.format(random.nextInt(1000000));
	}
 
	/**
	 * 给定数组，抽取n个数据
	 * @param list
	 * @param n
	 * @return
	 */
	public static ArrayList getRandomFromList(List list, int n) {
 
		Random random = new Random();
 
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
 
		// 生成随机数字并存入HashMap
		for (int i = 0; i < list.size(); i++) {
 
			int number = random.nextInt(100) + 1;
 
			hashMap.put(number, i);
		}
 
		// 从HashMap导入数组
		Object[] robjs = hashMap.values().toArray();
 
		ArrayList r = new ArrayList();
 
		// 遍历数组并打印数据
		for (int i = 0; i < n; i++) {
			r.add(list.get((int) robjs[i]));
			System.out.print(list.get((int) robjs[i]) + "\t");
		}
		System.out.print("\n");
		return r;
	}
 

//		"大小写数字:"+generateString(10)
//		"大小写字母:"+generateMixString(10)
//		"小写字母:"+generateLowerString(10)
//		"大写字母:"+generateUpperString(10)
//		"纯数字:"+generateNumberString(10)

}