package sg.edu.nus.iss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.demo.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
