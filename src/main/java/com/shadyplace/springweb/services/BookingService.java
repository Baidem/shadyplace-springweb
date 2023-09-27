package com.shadyplace.springweb.services;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.LocationForm;
import com.shadyplace.springweb.models.Booking;
import com.shadyplace.springweb.models.Command;
import com.shadyplace.springweb.models.User;
import com.shadyplace.springweb.repository.BookingRepository;
import com.shadyplace.springweb.repository.CommandRepository;
import com.shadyplace.springweb.repository.UserRepository;
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
    DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    LineService lineService;

    private User user;

    public List<LocationForm> payloadToResas(String requestBody) {

        List<LocationForm> reservationForm = new ArrayList<>();

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

                // Je cré mon emplacement
                LocationForm emplacementForm = new LocationForm();
                // Je récupére mon la file dans hmap qui représente ma requête
                emplacementForm.setLine(Integer.valueOf(mapParam.get("items_" + index + "_file")));
                // Je réccupére mon equipement dans le hashmap qui représente ma requête
                emplacementForm.setEquipement(mapParam.get("items_" + index + "_equipement"));

                // J'ajoute mon emplacement dans mon formulaire
                reservationForm.add(emplacementForm);
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

        for (LocationForm locationForm: bookingForm.getLocations()){

            Booking booking = new Booking();
            booking.setBookingDate(new GregorianCalendar());
            booking.setLine(lineService.getLineByString(locationForm.getLine().toString()));
            booking.setEquipment(locationForm.getEquipment());
            booking.setCommand(command);

            this.bookingRepository.save(booking);
        }

    }


}
