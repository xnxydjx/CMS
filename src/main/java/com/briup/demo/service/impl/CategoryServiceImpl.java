package com.briup.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.ArticleExample;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.CategoryExample;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.mapper.ArticleMapper;
import com.briup.demo.mapper.CategoryMapper;
import com.briup.demo.mapper.ex.CategoryExMapper;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;
@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired//栏目dao
	private CategoryMapper categoryMapper;
	@Autowired//文章dao
	private ArticleMapper articleMapper;
	//栏目扩展dao
	@Autowired
	private CategoryExMapper categoryExMapper;
	
	@Override
	public void saveOrUpdateCategory(Category category) throws CustomerException {
		if (category==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "参数为空！");
		}
		if (category.getId()==null) {
			categoryMapper.insert(category);
		}else {
			categoryMapper.updateByPrimaryKey(category);
		}
	}
	@Override
	public List<Category> findAllCategories() throws CustomerException {
		return categoryMapper.selectByExample(new CategoryExample());
	}
	@Override
	public void deleteCategoryById(Integer id) throws CustomerException {
		//删除栏目的同时，先删除栏目中包含的文章信息
		ArticleExample example = new ArticleExample();
		example.createCriteria().andCategoryIdEqualTo(id);
		articleMapper.deleteByExample(example);
		categoryMapper.deleteByPrimaryKey(id);
	}
	@Override
	public Category findCategoryById(Integer id) throws CustomerException {
		return categoryMapper.selectByPrimaryKey(id);
	}
	@Override
	public List<Category> findCategoryByNameOrCode(Category category) throws CustomerException {
		CategoryExample example = new CategoryExample();
		example.createCriteria().andNameEqualTo(category.getName());
		example.or().andCodeEqualTo(category.getCode());
		return categoryMapper.selectByExample(example);
	}
	@Override
	public List<CategoryEx> findAllCategoryEx() throws CustomerException {
		return categoryExMapper.findAllCategoryExs();
	}
	@Override
	public CategoryEx findCategoryExById(Integer id) throws CustomerException {
		return categoryExMapper.findCategoryExById(id);
	}

}
