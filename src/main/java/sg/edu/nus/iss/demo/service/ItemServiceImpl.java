package sg.edu.nus.iss.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.demo.model.Item;
import sg.edu.nus.iss.demo.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getItemList() {
        return itemRepository.findAll();
    }

    // @Override
    // public List<Item> getItemContainingName(String name) {
    //     return itemRepository.findByName(name);
    // }

    @Override
    public Item updateItem(Item item, Long itemId) {
        Item itemEnt = itemRepository.findById(itemId).get();

        if (Objects.nonNull(item.getName()) && !"".equalsIgnoreCase(item.getName())) {
            itemEnt.setName(item.getName());
        }

        if (Objects.nonNull(item.getDescription()) && !"".equalsIgnoreCase(item.getDescription())) {
            itemEnt.setDescription(item.getDescription());
        }

        return itemRepository.save(itemEnt);
    }

    @Override
    public void deleteItemById(Long itemId) {
        itemRepository.deleteById(itemId);
        
    }
    
}
