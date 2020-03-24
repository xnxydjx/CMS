package com.briup.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.service.IArticleService;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.Message;
import com.briup.demo.utils.MessageUtil;
import com.briup.demo.utils.StatusCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "栏目相关接口")
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IArticleService articleService;
	@PostMapping("/saveCategory")
	@ApiOperation("保存栏目信息")
	public Message<String> saveCategory(Category category) {
		try {
			if(categoryService.findCategoryByNameOrCode(category).size()>0) {
				return MessageUtil.error(StatusCodeUtil.NOALLOW_CODE, "栏目存在勿重复添加");
			}else {
				categoryService.saveOrUpdateCategory(category);	
				return MessageUtil.success();
			}
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误："+e.getMessage());
		}
	}
	@PostMapping("/updateCategory")
	@ApiOperation("修改栏目信息")
	public Message<String> updateLink(Category category) {
		categoryService.saveOrUpdateCategory(category);
		return MessageUtil.success();
	}
	@GetMapping("/selectCategories")
	@ApiOperation("查询所有栏目信息")
	public Message<List<Category>> selectCategories() {
		return MessageUtil.success(categoryService.findAllCategories());
	}
	@GetMapping("/deleteCategoryById")
	@ApiOperation("根据id删除栏目信息")
	public Message<String> deleteById(Integer id) {
		categoryService.deleteCategoryById(id);
		return MessageUtil.success();
	}
	@GetMapping("/findCategoryById")
	@ApiOperation("根据id查找指定栏目信息")
	public Message<Category> findCategoryById(Integer id) {
		return MessageUtil.success(categoryService.findCategoryById(id));
	}
	
	@GetMapping("/getArticleById")
	@ApiOperation("根据id查询文章信息")
	public Message<Article> getArticleById(Integer id) {
		return MessageUtil.success(articleService.findArticleById(id));
	}
	
	/**
	 * 根据id查找栏目及其包含所有文章信息
	 */
	@GetMapping("/findCategoryExById")
	@ApiOperation("根据id查找栏目及其包含所有文章信息")
	public Message<CategoryEx> findCategoryExById(Integer id){
		return MessageUtil.success(categoryService.findCategoryExById(id));
	}
}
