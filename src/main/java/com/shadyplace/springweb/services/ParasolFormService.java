package com.shadyplace.springweb.services;

import com.shadyplace.springweb.forms.ParasolForm;
import com.shadyplace.springweb.repository.EquipmentRepository;
import com.shadyplace.springweb.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParasolFormService {

    @Autowired
    LineRepository lineRepository;
    @Autowired
    EquipmentRepository equipmentRepository;

    /** Body request schema :
     *
     *
     *  Requête avec des champs vierges
     *  _csrf=gUCKm2t7DVSx4UxiUOQdR9Wkczle9Bp6mjcjysyGIYBxzqo0sHfp-A9IaGKc039QMskpJuGSXgFnxH9XqQNG_vjjQOZI-Z5Q&
     *  dateStart=&dateEnd=&
     *  items_0_line=&items_0_equipment=&
     *  comment=
     *
     *  Grosse requête
     *
     *  _csrf=834cG-MyK4Vgucnvpdw__e9cMlx4QNmuISHPUvgo0ND0Uw_Qy0h6LdUEGeFNjfrew_ELyIxqHz5AIemDFEP4M8lL5OmVYj6y&
     *  dateStart=2023-10-07&dateEnd=2023-10-07&
     *  items_0_line=6&items_0_equipment=2&
     *  items_1_line=5&items_1_equipment=5&
     *  items_2_line=5&items_2_equipment=4&
     *  items_3_line=7&items_3_equipment=2&
     *  comment=long+commentaire+long+commentaire+long+commentaire+long+commentaire+long+commentaire+long+commentaire
     *
     **/

    public List<ParasolForm> convertPayloadToParasolFormList(String requestBody) {

        List<ParasolForm> parasolFormArrayList = new ArrayList<>();

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
                Long linedId = Long.parseLong(paramMap.get("items_" + index + "_line"));
                Long equipmentId = Long.parseLong(paramMap.get("items_" + index + "_equipment"));

                // Set a new EquipmentAndLineForm with line and equipment id
                ParasolForm e = new ParasolForm();
                e.setLine(lineRepository.getById(linedId));
                e.setEquipment(equipmentRepository.getById(equipmentId));

                // Add to the result list
                parasolFormArrayList.add(e);
            }
        }
        return parasolFormArrayList;
    }
}
