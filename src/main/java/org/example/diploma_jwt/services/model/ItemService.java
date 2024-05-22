package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
