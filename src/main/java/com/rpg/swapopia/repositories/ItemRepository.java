package com.rpg.swapopia.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rpg.swapopia.domain.item.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
    Optional<Item> findByName(String name);

    void deleteByName(String name);

    List<Item> findByNameIn(Set<String> names);

    //findall
    List<Item> findAllByOrderByNameAsc();
}
