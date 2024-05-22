package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import org.example.diploma_jwt.models.Avito;
import org.example.diploma_jwt.repositories.AvitoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvitoService {
    private final AvitoRepository avitoRepository;

    public Avito save(Avito avito){
        return avitoRepository.save(avito);
    }
}
