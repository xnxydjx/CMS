package com.briup.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.ArticleExample;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.CategoryExample;
import com.briup.demo.mapper.ArticleMapper;
import com.briup.demo.mapper.CategoryMapper;
import com.briup.demo.service.IArticleService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;
/**
 * 文章管理相关的逻辑类
 * @author DJX
 */
@Service
public class ArticleServiceImpl implements IArticleService {
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Override
	public void saveOrUpdateArticle(Article article) throws CustomerException {
		if (article==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "参数为空！");
		}
		if (article.getId()==null) {
			//需要额外添加两条数据
			article.setPublishdate(new Date());
			article.setClicktimes(0);
			articleMapper.insert(article);
		}else {
//			article.setPublishdate(new Date());//如果是作者修改文章，则修改日期
			articleMapper.updateByPrimaryKey(article);
		}
	}

	@Override
	public void deleteArticleById(Integer id) throws CustomerException {
		articleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Article> findArticleByCondition(String key, String condition) throws CustomerException {
		/**
		 * 分三种情况：
		 * 1.没有添加任何条件
		 * 2.没有指定栏目，但指定了关键字
		 * 3.指定栏目，没有指定关键字
		 * 4.指定栏目，同时指定查询的关键字
		 */
		key = key==null ? "" : key.trim();
		condition = condition==null ? "" : condition.trim();
		//创建空白模板对象
		ArticleExample articleExample = new ArticleExample();
		CategoryExample categoryExample = new CategoryExample();
		if ("".equals(key)&&"".equals(condition)) {
			//情况1
			return articleMapper.selectByExample(articleExample);
		}else if ("".equals(condition)) {
			//情况2
			articleExample.createCriteria().andTitleLike("%"+key+"%");
			return articleMapper.selectByExample(articleExample);
		}else if ("".equals(key)) {
			//情况3
			categoryExample.createCriteria().andNameEqualTo(condition);
			List<Category> list = categoryMapper.selectByExample(categoryExample);
			if (list.size()>0) {
				//根据栏目信息，找到里面所有文章				
				articleExample.createCriteria().andCategoryIdEqualTo(list.get(0).getId());
			}else {
				throw new CustomerException(StatusCodeUtil.NOFOUND_CODE, "没有指定的搜索栏目");
			}
			return articleMapper.selectByExample(articleExample);
		}else {
			//情况4
			categoryExample.createCriteria().andNameEqualTo(condition);
			List<Category> list = categoryMapper.selectByExample(categoryExample);
			if (list.size()>0) {
				articleExample.createCriteria().andCategoryIdEqualTo(list.get(0).getId()).andTitleLike("%"+key+"%");
			}else {
				throw new CustomerException(StatusCodeUtil.NOFOUND_CODE, "没有指定的搜索栏目");
			}
			return articleMapper.selectByExample(articleExample);
		}
	}

	/**
	 * and的方式拼接条件
	 * 		example.createCriteria().and...
	 * or的方式拼接条件
	 * 		example.or().and...
	 */
	
	@Override//负责点击数+1
	public Article findArticleById(Integer id) throws CustomerException {
		Article article = articleMapper.selectByPrimaryKey(id);
		Integer clicktimes = article.getClicktimes() == null ? 0 : article.getClicktimes();
		article.setClicktimes(clicktimes+1);
		this.saveOrUpdateArticle(article);
		return article;
	}

	@Override
	public List<Article> findArticleByCategoryId(Integer id) throws CustomerException {
		ArticleExample example = new ArticleExample();
		example.createCriteria().andCategoryIdEqualTo(id);
		return articleMapper.selectByExample(example);
	}

	@Override
	public void addClicktimes(Integer id) throws CustomerException {
		Article article = findArticleById(id);
		article.setClicktimes(article.getClicktimes()+1);
		articleMapper.updateByPrimaryKeySelective(article);
	}
}
