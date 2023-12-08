package com.shadyplace.springweb.repository.bookingResa;

import com.shadyplace.springweb.models.bookingResa.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

       Location findLocationByLineNumberAndRankNumber(int lineNumber, int rankNumber);

       List<Location> getLocationsByLineNumber(int lineNumber);

       @Query("SELECT l FROM Location l JOIN Booking b WHERE b.bookingDate = :bookingDate")
       List<Location> getAllLocationByBookingDate(@Param("bookingDate") Calendar bookingDate);

       @Query("SELECT l FROM Location l JOIN Booking b WHERE b.bookingDate = :bookingDate AND l.lineNumber = :lineNumber")
       List<Location> getLocationsByBookingDateAndLineNumber(
               @Param("bookingDate") Calendar bookingDate,
               @Param("lineNumber") int lineNumber
       );

        List<Location> findAll();
}
