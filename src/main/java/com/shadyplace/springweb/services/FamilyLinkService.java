package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.FamilyLink;
import com.shadyplace.springweb.models.enums.FamilyLinkLabel;
import com.shadyplace.springweb.repository.FamilyLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyLinkService {

    @Autowired
    FamilyLinkRepository familyLinkRepository;

    public FamilyLink findByLabel(FamilyLinkLabel familyLinkLabel) {
        return this.familyLinkRepository.findFirstByLabel(familyLinkLabel);
    }

    public void save(FamilyLink familyLink) {
        this.familyLinkRepository.save(familyLink);
    }
}