package com.rpg.swapopia.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rpg.swapopia.domain.item.Box;


public interface BoxRepository extends JpaRepository<Box, Long> {
    
    //buscar todas as caixas com preço menor que o preço passado como parâmetro
    List<Box> findByPriceLessThan(double price);

    //excluir caixa pelo id
    void deleteById(Long id);

    List<Box> findAll();

    Box findByType(String type);

}
