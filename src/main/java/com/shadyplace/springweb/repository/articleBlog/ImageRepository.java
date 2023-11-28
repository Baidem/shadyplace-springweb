package com.shadyplace.springweb.repository.articleBlog;

import com.shadyplace.springweb.models.articleBlog.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    Image findFirstByLocation(String location);
}
