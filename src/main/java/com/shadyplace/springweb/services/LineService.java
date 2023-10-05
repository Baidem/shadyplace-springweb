package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Line;
import com.shadyplace.springweb.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineService {

    @Autowired
    LineRepository lineRepository;

    public Line findLineByLabel(String label){
        return this.lineRepository.findFirstByLabel(label);
    }

    public List<Line> findAll(){
        return this.lineRepository.findAll();
    }

    public void save(Line line) {
        this.lineRepository.save(line);
    }
}
