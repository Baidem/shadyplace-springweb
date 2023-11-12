package com.shadyplace.springweb.services.articleBlog;


import com.shadyplace.springweb.models.articleBlog.Image;
import com.shadyplace.springweb.models.bookingResa.Location;
import com.shadyplace.springweb.repository.bookingResa.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public Location finByLineAndRank(int lineNumber, int rankNumber) {
        return locationRepository.findLocationByLineNumberAndRankNumber(lineNumber, rankNumber);
    }
    public void save(Location location){
        locationRepository.save(location);
    }

}

