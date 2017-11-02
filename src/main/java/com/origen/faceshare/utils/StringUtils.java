
package com.origen.faceshare.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 * 
 * @author: origen.wang
 * @date: 2017年7月27日
 */
public class StringUtils {

	public static boolean isEmpty(String str) {

		if ( str == null || "".equals(str) )
		{
			return true;
		} else
		{
			return false;
		}
	}

	public static boolean isNotEmpty(String str) {

		if ( str == null || "".equals(str) )
		{
			return false;
		} else
		{
			return true;
		}
	}

	public static boolean isBlank(final CharSequence cs) {

		int strLen;
		if ( cs == null || ( strLen = cs.length() ) == 0 )
		{
			return true;
		}
		for ( int i = 0; i < strLen; i++ )
		{
			if ( Character.isWhitespace(cs.charAt(i)) == false )
			{
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(final CharSequence cs) {

		return !isBlank(cs);
	}

	/**
	 * 判断是否包含特殊字符
	 */
	public static boolean specialCharCheck(String str) {

		if ( StringUtils.isBlank(str) )
		{
			return false;
		}

		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);

		return m.find();
	}
}
