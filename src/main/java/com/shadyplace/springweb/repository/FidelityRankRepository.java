package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.FidelityRank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FidelityRankRepository extends CrudRepository<FidelityRank, Long> {

     @Query(value = "FROM FidelityRank WHERE label = :label")
     FidelityRank findFirstByLabel(String label);
}
