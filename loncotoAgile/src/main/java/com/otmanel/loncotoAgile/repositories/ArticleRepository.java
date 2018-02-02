package com.otmanel.loncotoAgile.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.otmanel.loncotoAgile.metier.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Integer> {

}
