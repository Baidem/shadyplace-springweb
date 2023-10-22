package com.shadyplace.springweb.repository.bookingResa;

import com.shadyplace.springweb.models.bookingResa.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {


}
