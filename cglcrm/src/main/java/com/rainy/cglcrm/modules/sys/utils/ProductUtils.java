/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.sys.utils;

import com.rainy.cglcrm.common.utils.SpringContextHolder;
import com.rainy.cglcrm.common.utils.StringUtils;
import com.rainy.cglcrm.modules.crm.dao.custom.CrmCustomDao;
import com.rainy.cglcrm.modules.crm.entity.product.CrmProduct;
/**
 * 用户工具类
 * @author rainy
 * @version 2013-12-05
 */
public class ProductUtils {

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
}
