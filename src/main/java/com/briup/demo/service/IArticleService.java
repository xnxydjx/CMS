package com.briup.demo.service;

import java.util.List;

import com.briup.demo.bean.Article;
import com.briup.demo.utils.CustomerException;

/**
 * 文章相关内容的Service接口
 * @author DJX
 */
public interface IArticleService {
	/**
	 * 新增或修改文章
	 */
	void saveOrUpdateArticle(Article article) throws CustomerException;
	/**
	 * 删除文章
	 */
	void deleteArticleById(Integer id) throws CustomerException;
	/**
	 * 查询文章
	 * @param key 表示搜索框
	 * @param condition 表示栏目框
	 * @return
	 * @throws CustomerException
	 */
	List<Article> findArticleByCondition(String key,String condition)
			throws CustomerException;
	/**
	 * 根据id查询文章
	 */
	Article findArticleById(Integer id) throws CustomerException;
	
	/**
	 * 通过CategoryId查找所有文章
	 */
	List<Article> findArticleByCategoryId(Integer id) throws CustomerException;
	/**
	 * 更新Clicktimes点击数
	 */
	void addClicktimes(Integer id) throws CustomerException;
}
