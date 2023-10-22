package com.shadyplace.springweb.repository.articleBlog;

import com.shadyplace.springweb.forms.SearchForm;
import com.shadyplace.springweb.models.articleBlog.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleCriteriaRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Article> getArticleListBySearchForm(SearchForm searchForm){
        // Criteria root
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> plageRoot = cq.from(Article.class);

        // Predicate list
        List<Predicate> predicates = new ArrayList<Predicate>();

        // Predicate conditions
        if (!searchForm.getSearchBar().isEmpty()){
            Predicate p1 = cb.like(plageRoot.get("title"), "%"+searchForm.getSearchBar()+"%");
            Predicate p2 = cb.like(plageRoot.get("content"), "%"+searchForm.getSearchBar()+"%");

            predicates.add(cb.or(p1,p2));
        }

        cq.where(predicates.toArray(new Predicate[] {}));

        TypedQuery<Article> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    public Page<Article> getArticlePageBySearchForm(SearchForm searchForm, Pageable pageable) {
        // Criteria root
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> plageRoot = cq.from(Article.class);

        // Predicate list
        List<Predicate> predicates = new ArrayList<Predicate>();

        // Predicate conditions
        if (!searchForm.getSearchBar().isEmpty()){
            Predicate p1 = cb.like(plageRoot.get("title"), "%"+searchForm.getSearchBar()+"%");
            Predicate p2 = cb.like(plageRoot.get("content"), "%"+searchForm.getSearchBar()+"%");

            predicates.add(cb.or(p1,p2));
        }

        cq.where(predicates.toArray(new Predicate[] {}));

        cq.orderBy(cb.desc(plageRoot.get("publicationDate")));

        TypedQuery<Article> query = entityManager.createQuery(cq);

        List<Article> articleList = query.getResultList();
        int start = (int) pageable.getOffset();
        int end = start + pageable.getPageSize();

        if (end > articleList.size()) {
            end = articleList.size();
        }

        List<Article> sublist = articleList.subList(start, end);

        Page<Article> articlePage = new PageImpl<>(sublist, pageable, articleList.size());

        return articlePage;
    }


}
