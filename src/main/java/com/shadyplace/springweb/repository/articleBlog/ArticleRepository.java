package com.shadyplace.springweb.repository.articleBlog;

import com.shadyplace.springweb.models.articleBlog.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

    List<Article> findAll();

    @Query(value = "FROM Article")
    Page<Article> paginateResult(Pageable pageable);

    List<Article> findByTitle(String title);

    @Query(value = "FROM Article a ORDER BY a.publicationDate desc")
    Page<Article> pagePagination(Pageable page);

}
