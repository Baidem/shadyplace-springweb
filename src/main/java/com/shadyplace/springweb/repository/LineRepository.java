package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Line;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends CrudRepository<Line, Long> {


}
