/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.common.utils.excel.fieldtype;

import com.rainy.cglcrm.common.utils.StringUtils;
import com.rainy.cglcrm.modules.sys.entity.Area;
import com.rainy.cglcrm.modules.sys.entity.User;
import com.rainy.cglcrm.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 * @author rainy
 * @version 2013-03-10
 */
public class UserType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		User u = UserUtils.getByLoginName(val);
		if (u!=null&&StringUtils.isNotBlank(u.getId())){
			return u;
		}
		return null;
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((User)val).getName() != null){
			return ((User)val).getName();
		}
		return "";
	}
}
