/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/rainy/cglcrm">cglcrm</a> All rights reserved.
 */
package com.rainy.cglcrm.modules.crm.web.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rainy.cglcrm.common.config.Global;
import com.rainy.cglcrm.common.persistence.Page;
import com.rainy.cglcrm.common.web.BaseController;
import com.rainy.cglcrm.common.utils.StringUtils;
import com.rainy.cglcrm.modules.crm.entity.product.CrmProduct;
import com.rainy.cglcrm.modules.crm.service.product.CrmProductService;

/**
 * 产品管理Controller
 * @author rainy
 * @version 2017-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/crm/product/crmProduct")
public class CrmProductController extends BaseController {

	@Autowired
	private CrmProductService crmProductService;
	
	@ModelAttribute
	public CrmProduct get(@RequestParam(required=false) String id) {
		CrmProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = crmProductService.get(id);
		}
		if (entity == null){
			entity = new CrmProduct();
		}
		return entity;
	}
	
	@RequiresPermissions("crm:product:crmProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrmProduct crmProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrmProduct> page = crmProductService.findPage(new Page<CrmProduct>(request, response), crmProduct); 
		model.addAttribute("page", page);
		return "modules/crm/product/crmProductList";
	}
	
	@RequiresPermissions("crm:product:crmProduct:view")
	@RequestMapping(value = {"crmProductListChoose"})
	public String crmProductListChoose(CrmProduct crmProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrmProduct> page = crmProductService.findPage(new Page<CrmProduct>(request, response), crmProduct); 
		model.addAttribute("page", page);
		return "modules/crm/product/crmProductListChoose";
	}
	@RequiresPermissions("crm:product:crmProduct:view")
	@RequestMapping(value = "form")
	public String form(CrmProduct crmProduct, Model model) {
		model.addAttribute("crmProduct", crmProduct);
		return "modules/crm/product/crmProductForm";
	}

	@RequiresPermissions("crm:product:crmProduct:edit")
	@RequestMapping(value = "save")
	public String save(CrmProduct crmProduct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, crmProduct)){
			return form(crmProduct, model);
		}
		crmProduct.setState("1");
		crmProductService.save(crmProduct);
		addMessage(redirectAttributes, "保存产品管理成功");
		return "redirect:"+Global.getAdminPath()+"/crm/product/crmProduct/?repage";
	}
	
	@RequiresPermissions("crm:product:crmProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(CrmProduct crmProduct, RedirectAttributes redirectAttributes) {
		crmProductService.delete(crmProduct);
		addMessage(redirectAttributes, "删除产品管理成功");
		return "redirect:"+Global.getAdminPath()+"/crm/product/crmProduct/?repage";
	}

}