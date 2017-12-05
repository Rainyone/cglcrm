/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rainy.cglcrm.common.service.CrudService;
import com.rainy.cglcrm.modules.cms.dao.ArticleDataDao;
import com.rainy.cglcrm.modules.cms.entity.ArticleData;

/**
 * 站点Service
 * @author rainy
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
