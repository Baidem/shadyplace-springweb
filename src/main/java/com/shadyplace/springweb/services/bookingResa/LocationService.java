package com.shadyplace.springweb.services.bookingResa;


import com.shadyplace.springweb.models.bookingResa.Location;
import com.shadyplace.springweb.repository.bookingResa.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    public List<Location> getAll(){
//        return locationRepository.getAll();
//    }

//    public Map<String, String> getPlanningMap(Calendar bookingDate) {
//        Map<String, String> map = new HashMap<String, String>();
//        List<Location> locations = locationRepository.getAll();
//        List<Location> occupiedLocations = locationRepository.getAllLocationByBookingDate(bookingDate);
//
//        for (Location location : locations) {
//            String key = location.getLineNumber()+","+ location.getRankNumber();
//            boolean isOccupied = occupiedLocations.stream()
//                    .anyMatch(occLocation -> occLocation.getLineNumber() == location.getLineNumber()
//                            && occLocation.getRankNumber() == location.getRankNumber());
//
//            map.put(key, isOccupied ? "Occupied" : "Available");
//        }
//        return map;
//    }

    public String[][] beach2D() {
        String[][] array2D = new String[8][36];
        for (int i = 0; i < array2D.length; i++) {
            for (int j = 0; j < array2D[i].length; j++) {
                array2D[i][j] = (i + 1) + "," + (j + 1);
            }
        }
        return array2D;
    }
}

