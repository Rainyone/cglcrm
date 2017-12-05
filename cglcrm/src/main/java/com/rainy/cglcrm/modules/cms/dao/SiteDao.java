/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.cms.dao;

import com.rainy.cglcrm.common.persistence.CrudDao;
import com.rainy.cglcrm.common.persistence.annotation.MyBatisDao;
import com.rainy.cglcrm.modules.cms.entity.Site;

/**
 * 站点DAO接口
 * @author rainy
 * @version 2013-8-23
 */
@MyBatisDao
public interface SiteDao extends CrudDao<Site> {

}
