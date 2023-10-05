package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Line;
import com.shadyplace.springweb.models.enums.LineLabel;
import com.shadyplace.springweb.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineService {

    @Autowired
    LineRepository lineRepository;

    public Line getLineByString(String lineString){
        for (LineLabel lineLabel : LineLabel.values()) {
            if (lineLabel.getLabel().equalsIgnoreCase(lineString)) {
                return lineRepository.findByLabel(lineLabel);
            }
        }
        return null;
    }

    public List<Line> getAll(){
        return this.getAll();
    }
}
