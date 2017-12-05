/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.dao.product;

import com.rainy.cglcrm.common.persistence.CrudDao;
import com.rainy.cglcrm.common.persistence.annotation.MyBatisDao;
import com.rainy.cglcrm.modules.crm.entity.product.CrmProduct;

/**
 * 产品管理DAO接口
 * @author rainy
 * @version 2017-11-19
 */
@MyBatisDao
public interface CrmProductDao extends CrudDao<CrmProduct> {
	
}