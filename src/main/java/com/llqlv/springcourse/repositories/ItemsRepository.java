package com.llqlv.springcourse.repositories;

import com.llqlv.springcourse.entity.Item;
import com.llqlv.springcourse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
    List<Item> findByOwner(User owner);

    List<Item> findByItemName(String name);
}
