package sg.edu.nus.iss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.demo.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
}
