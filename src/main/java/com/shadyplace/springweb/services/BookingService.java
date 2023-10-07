package com.shadyplace.springweb.services;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.EquipmentAndLineForm;
import com.shadyplace.springweb.models.Booking;
import com.shadyplace.springweb.models.Command;
import com.shadyplace.springweb.models.User;
import com.shadyplace.springweb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    LineService lineService;

    private User user;

//      Body request schema :

//    Requête avec des champs vierges
// _csrf=gUCKm2t7DVSx4UxiUOQdR9Wkczle9Bp6mjcjysyGIYBxzqo0sHfp-A9IaGKc039QMskpJuGSXgFnxH9XqQNG_vjjQOZI-Z5Q&
// dateStart=&dateEnd=&
// items_0_line=&items_0_equipment=&
// comment=

    //  Grosse requête
//    _csrf=834cG-MyK4Vgucnvpdw__e9cMlx4QNmuISHPUvgo0ND0Uw_Qy0h6LdUEGeFNjfrew_ELyIxqHz5AIemDFEP4M8lL5OmVYj6y&
//    dateStart=2023-10-07&dateEnd=2023-10-07&
//    items_0_line=6&items_0_equipment=2&
//    items_1_line=5&items_1_equipment=5&
//    items_2_line=5&items_2_equipment=4&
//    items_3_line=7&items_3_equipment=2&
//    comment=long+commentaire+long+commentaire+long+commentaire+long+commentaire+long+commentaire+long+commentaire+
//
    public List<EquipmentAndLineForm> payloadToBookingList(String requestBody) {

        List<EquipmentAndLineForm> equipmentAndLineFormArrayList = new ArrayList<>();

        // Split rows
        List<String> paramList = Arrays.stream(requestBody.split("&")).toList();

        // For each row split key value and add to map
        Map<String, String> paramMap = new HashMap<>();
        for (String paramString : paramList) {
            String[] strParam = paramString.split("=");
            List<String> strParamList = Arrays.stream(strParam).toList();
            if (strParamList.size() == 2) {
                paramMap.put(strParamList.get(0), strParamList.get(1));
            }
        }

        // For each key get index and build
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (
                    entry.getKey().startsWith("items_")
                            &&
                            entry.getKey().endsWith("_line")
            ) {
                String index = Arrays.stream(entry.getKey().split("_")).toList().get(1);

                // Get Line and Equipment id for this index
                String lineIdstr = paramMap.get("items_" + index + "_line");
                String equipmentIdstr = paramMap.get("items_" + index + "_equipment");

                Long linedId = Long.parseLong(lineIdstr);
                Long equipmentId = Long.parseLong(equipmentIdstr);
                System.out.println(linedId);
                // Set a new EquipmentAndLineForm with line and equipment id
                EquipmentAndLineForm e = new EquipmentAndLineForm();
                e.setLine(lineRepository.getById(linedId));
                e.setEquipment(equipmentRepository.getById(equipmentId));

                // Add to the result list
                equipmentAndLineFormArrayList.add(e);
            }
        }
        return equipmentAndLineFormArrayList;
    }


    public void persistReservationFromForm (BookingForm bookingForm, String email){
        Command command = new Command();
        command.setUser(this.userRepository.findByEmail(email));
        command.setComment(bookingForm.getComment());

        this.commandRepository.save(command);

        for (EquipmentAndLineForm equipmentAndLineForm : bookingForm.getLocations()) {

            Booking booking = new Booking();
            booking.setBookingDate(new GregorianCalendar());
            booking.setLine(equipmentAndLineForm.getLine());
            booking.setEquipment(equipmentAndLineForm.getEquipment());
            booking.setCommand(command);

            this.bookingRepository.save(booking);
        }

    }

}

