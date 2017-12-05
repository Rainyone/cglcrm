/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.test.dao;

import com.rainy.cglcrm.common.persistence.CrudDao;
import com.rainy.cglcrm.common.persistence.annotation.MyBatisDao;
import com.rainy.cglcrm.test.entity.TestDataMain;

/**
 * 主子表生成DAO接口
 * @author rainy
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestDataMainDao extends CrudDao<TestDataMain> {
	
}