package com.shadyplace.springweb.services.userAuth;

import com.shadyplace.springweb.models.userAuth.FidelityRank;
import com.shadyplace.springweb.repository.userAuth.FidelityRankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FidelityRankService {

    @Autowired
    FidelityRankRepository fidelityRankRepository;

    public FidelityRank findByLabel(String label) {
        return this.fidelityRankRepository.findFirstByLabel(label);
    }

    public void save(FidelityRank fidelityRank) {
        this.fidelityRankRepository.save(fidelityRank);
    }
}
