package sg.edu.nus.iss.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.demo.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
    // List<Item> findByName(String name);
    
}
