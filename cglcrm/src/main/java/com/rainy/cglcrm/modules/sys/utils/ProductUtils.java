/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.sys.utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rainy.cglcrm.common.utils.CacheUtils;
import com.rainy.cglcrm.common.utils.SpringContextHolder;
import com.rainy.cglcrm.common.utils.StringUtils;
import com.rainy.cglcrm.modules.crm.dao.custom.CrmCustomDao;
import com.rainy.cglcrm.modules.crm.entity.product.CrmProduct;
/**
 * 用户工具类
 * @author rainy
 * @version 2013-12-05
 */
import com.rainy.cglcrm.modules.sys.entity.Dict;
public class ProductUtils {
	public static final String CUSTOM_SOURCE = "custom_source";
	private static CrmCustomDao crmCustomDao = SpringContextHolder.getBean(CrmCustomDao.class);

	public static String getProductsName(String ids){
		String names = "";
		//遍历产品id
		if(StringUtils.isNotBlank(ids)) {
			String[] prodIds = ids.split(",");
			for(String id:prodIds) {
				CrmProduct cp = crmCustomDao.getProduct(null, id);
				if(cp!=null) {
					String name = cp.getProductName();
					if(StringUtils.isNotBlank(name)) {
						if(StringUtils.isNotBlank(names)) {
							names += "," + name;
						}else {
							names = name;
						}
					}
				}
			}
		}
		return names;
	}
	public static List<Dict> getCustomSourceList(){
			List<Dict>  dictList = crmCustomDao.getCustomSourceList();
			if (dictList == null){
				dictList = Lists.newArrayList();
			}
		return dictList;
	}
	public static List<Dict> getMainIndustryList(){
		List<Dict>  dictList = crmCustomDao.getMainIndustryList();
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	public static List<Dict> getProductsList(){
		List<Dict>  dictList = crmCustomDao.getProductsList();
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
}
