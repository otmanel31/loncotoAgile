package com.loncoto.loncontoBoot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loncoto.loncontoBoot.metier.Article;
import com.loncoto.loncontoBoot.repositories.ArticleRepository;


@Controller
@RequestMapping(value="/extendedapi")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/articles",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Article> getPageArticles(@PageableDefault(page=0, size=12) Pageable page) {
		return this.articleRepository.findAll(page);
	}
	
}
