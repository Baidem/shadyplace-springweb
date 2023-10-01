package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {


    Image findFirstByTitle(String title);
    Image findFirstByLocation(String location);

}
