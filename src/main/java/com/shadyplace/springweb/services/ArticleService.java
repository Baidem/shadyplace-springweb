package com.shadyplace.springweb.services;

import com.shadyplace.springweb.forms.SearchForm;
import com.shadyplace.springweb.models.Article;
import com.shadyplace.springweb.repository.ArticleCriteriaRepository;
import com.shadyplace.springweb.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCriteriaRepository articleCriteriaRepository;

    public List<Article> getAll(){
        return this.articleRepository.findAll();
    }

    public void add(Article plage){
        this.articleRepository.save(plage);
    }

    public void remove(Article plage){
        this.articleRepository.delete(plage);
    }

    public List<Article> findByTitle(String title){
        return this.articleRepository.findByTitle(title);
    }

    public Page<Article> paginatePage(int nbResult, int page){
        Pageable pageable = PageRequest.of(page, nbResult);
        Page<Article> plagePaginated = this.articleRepository.pagePagination(pageable);
        return plagePaginated;
    }

    public List<Article> searchEngine(SearchForm searchForm){
        return this.articleCriteriaRepository.getArticleListBySearchForm(searchForm);
    }
    public Page<Article> getArticlePageBySearchForm(SearchForm searchForm, int nbResult, int page){
        Pageable pageable = PageRequest.of(page, nbResult);
        Page<Article> articlePaginated = this.articleCriteriaRepository.getArticlePageBySearchForm(searchForm, pageable);
        return articlePaginated;
    }

}
