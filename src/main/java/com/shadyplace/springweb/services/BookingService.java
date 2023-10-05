package com.shadyplace.springweb.services;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.PlaceOptionForm;
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

    public List<PlaceOptionForm> payloadToResas(String requestBody) {

        List<PlaceOptionForm> reservationForm = new ArrayList<>();

        // On sépare chacun de nos éléments en faisant un split sur le &
        // On met tout ça en list pour simplifier l'utilisation
        String[] params = requestBody.split("&");
        List<String> listParam = Arrays.stream(params).toList();

        // Je cré un Hashmap (clé => valeur) cela nous permettra de rechercher facilement
        // des éléments de notre requêtes en fonction du champs souhaité
        Map<String, String> mapParam = new HashMap<>();

        // Je par cours ma liste de paramètre
        // Un paramètre sera donc par exemple items_3_equipement=lit
        for (String paramString : listParam) {
            // Je fais un split sur le "=" pour séparer la clé de la valeur
            String[] strParam = paramString.split("=");

            List<String> paramList = Arrays.stream(strParam).toList();
            if (paramList.size() == 2) {
                // J'ajoute dans mon hashmap la clé et la valeur
                mapParam.put(paramList.get(0), paramList.get(1));
            }
        }

        // Je parcours la map que j'ai créé précédement
        for (Map.Entry<String, String> entry : mapParam.entrySet()) {

            // Si la clé de ma map commence par items_ et fini par _file
            // Je vais devoir créer un nouvel emplacement
            if (entry.getKey().startsWith("items_") && entry.getKey().endsWith("_file")) {
                // Je réccupére l'index de mon emplacement
                // Ceci me permettra de retrouver les autres champs en relation avec mon emplacement
                String[] splitItemName = entry.getKey().split("_");
                List<String> splitNameList = Arrays.stream(splitItemName).toList();
                String index = splitNameList.get(1);

                // Collect options of the place
                PlaceOptionForm placeOptionForm = new PlaceOptionForm();
                Long linedId = Long.parseLong(mapParam.get(mapParam.get("items_" + index + "_file")));
                placeOptionForm.setLine(lineRepository.getById(linedId));
                // Je réccupére mon equipement dans le hashmap qui représente ma requête
                Long equipmentId = Long.parseLong(mapParam.get("items_" + index + "_equipement"));
                placeOptionForm.setEquipment(equipmentRepository.getById(equipmentId));

                // J'ajoute mon emplacement dans mon formulaire
                reservationForm.add(placeOptionForm);
            }

        }
        // Je retourne ma liste d'emplacement
        return reservationForm;
    }

    public void persistReservationFromForm(BookingForm bookingForm, String email){
        Command command = new Command();
        command.setUser(this.userRepository.findByEmail(email));
        command.setComment(bookingForm.getComment());

        this.commandRepository.save(command);

        for (PlaceOptionForm placeOptionForm : bookingForm.getLocations()){

            Booking booking = new Booking();
            booking.setBookingDate(new GregorianCalendar());
            booking.setLine(placeOptionForm.getLine());
            booking.setEquipment(placeOptionForm.getEquipment());
            booking.setCommand(command);

            this.bookingRepository.save(booking);
        }

    }


}
