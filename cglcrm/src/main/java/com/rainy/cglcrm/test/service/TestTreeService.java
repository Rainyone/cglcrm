/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rainy.cglcrm.common.service.TreeService;
import com.rainy.cglcrm.common.utils.StringUtils;
import com.rainy.cglcrm.test.entity.TestTree;
import com.rainy.cglcrm.test.dao.TestTreeDao;

/**
 * 树结构生成Service
 * @author rainy
 * @version 2015-04-06
 */
@Service
@Transactional(readOnly = true)
public class TestTreeService extends TreeService<TestTreeDao, TestTree> {

	public TestTree get(String id) {
		return super.get(id);
	}
	
	public List<TestTree> findList(TestTree testTree) {
		if (StringUtils.isNotBlank(testTree.getParentIds())){
			testTree.setParentIds(","+testTree.getParentIds()+",");
		}
		return super.findList(testTree);
	}
	
	@Transactional(readOnly = false)
	public void save(TestTree testTree) {
		super.save(testTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestTree testTree) {
		super.delete(testTree);
	}
	
}