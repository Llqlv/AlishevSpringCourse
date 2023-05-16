package com.llqlv.springcourse.services;

import com.llqlv.springcourse.entity.Item;
import com.llqlv.springcourse.entity.User;
import com.llqlv.springcourse.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findByItemName(String itemName) {
        return itemsRepository.findByItemName(itemName);
    }

    public List<Item> findByOwner(User owner) {
        return itemsRepository.findByOwner(owner);
    }


}
