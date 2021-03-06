/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.gen.dao;

import com.rainy.cglcrm.common.persistence.CrudDao;
import com.rainy.cglcrm.common.persistence.annotation.MyBatisDao;
import com.rainy.cglcrm.modules.gen.entity.GenScheme;

/**
 * 生成方案DAO接口
 * @author rainy
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {
	
}
