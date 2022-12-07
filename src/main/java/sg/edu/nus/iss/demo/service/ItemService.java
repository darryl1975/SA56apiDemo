package sg.edu.nus.iss.demo.service;

import java.util.List;

import sg.edu.nus.iss.demo.model.Item;

public interface ItemService {
    Item saveItem(Item item);
    
    List<Item> getItemList();

    // List<Item> getItemContainingName(String name);

    Item updateItem(Item item, Long itemId);

    void deleteItemById(Long itemId);
}
