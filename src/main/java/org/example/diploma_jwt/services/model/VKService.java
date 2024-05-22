package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import org.example.diploma_jwt.models.Avito;
import org.example.diploma_jwt.models.VK;
import org.example.diploma_jwt.repositories.OzonRepository;
import org.example.diploma_jwt.repositories.VKRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VKService {
    private final VKRepository vkRepository;

    public VK save(VK vk){
        return vkRepository.save(vk);
    }
}
