package sg.edu.nus.iss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.demo.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
