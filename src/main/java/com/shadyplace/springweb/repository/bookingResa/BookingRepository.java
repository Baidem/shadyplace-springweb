package com.shadyplace.springweb.repository.bookingResa;

import com.shadyplace.springweb.models.bookingResa.Booking;
import com.shadyplace.springweb.models.bookingResa.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> getByCommandId(Long id);

    List<Booking> getAllByBookingDate(Calendar bookingDate);

    Booking findByBookingDateAndLocation(Calendar bookingDate, Location location);

}
