package com.briup.demo.service;

import java.util.List;

import com.briup.demo.bean.Category;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.utils.CustomerException;
/**
 * 栏目相关的service层
 * @author DJX
 */
public interface ICategoryService {
	/**
	 * 添加或修改栏目信息
	 */
	void saveOrUpdateCategory(Category category) throws CustomerException;
	/**
	 * 查询所有的栏目
	 */
	List<Category> findAllCategories() throws CustomerException;
	/**
	 * 根据id删除栏目
	 */
	void deleteCategoryById(Integer id) throws CustomerException;
	/**
	 * 根据id查找指定栏目信息
	 */
	Category findCategoryById(Integer id) throws CustomerException;
	
	/**
	 * 根据项目名称查找栏目信息
	 */
	List<Category> findCategoryByNameOrCode(Category category) throws CustomerException;
	
	/**
	 * 查询栏目信息并且级联查询包含文章信息(自己写)
	 */
	List<CategoryEx> findAllCategoryEx() throws CustomerException;
	
	/**
	 * 查询栏目及其包含文章数据(上课写)
	 */
	CategoryEx findCategoryExById(Integer id) throws CustomerException;
}
