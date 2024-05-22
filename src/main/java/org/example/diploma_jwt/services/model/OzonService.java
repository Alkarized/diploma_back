package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import org.example.diploma_jwt.models.Ozon;
import org.example.diploma_jwt.repositories.AvitoRepository;
import org.example.diploma_jwt.repositories.OzonRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OzonService {
    private final OzonRepository ozonRepository;

    public Ozon save(Ozon ozon) {return ozonRepository.save(ozon);}
}
