package com.rpg.swapopia.model.item;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InventoryContentsResponse {
    private List<Item> items;
    private List<Box> boxes;
}
