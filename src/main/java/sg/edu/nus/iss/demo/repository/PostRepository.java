package sg.edu.nus.iss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.demo.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
}
