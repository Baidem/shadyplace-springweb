package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.FamilyLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyLinkRepository extends CrudRepository<FamilyLink, Long> {


}
