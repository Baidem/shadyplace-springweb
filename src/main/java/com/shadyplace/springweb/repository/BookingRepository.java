package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Booking;
import com.shadyplace.springweb.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> getByCommandId(Long id);


}
