package sg.edu.nus.iss.demo.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.demo.model.Note;
import sg.edu.nus.iss.demo.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteRepository noteRepository;
    
    @Override
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getNoteList() {
        return noteRepository.findAll();
    }

    @Override
    public Note updateNote(Note note, Long noteId) {
        Note noteEnt = noteRepository.findById(noteId).get();

        if (Objects.nonNull(note.getTitle()) && !"".equalsIgnoreCase(note.getTitle())) {
            noteEnt.setTitle(note.getTitle());
        }

        if (Objects.nonNull(note.getMessage()) && !"".equalsIgnoreCase(note.getMessage())) {
            noteEnt.setMessage(note.getMessage());
        }

        return noteRepository.save(noteEnt);
    }

    @Override
    public Boolean deleteNoteById(Long noteId) {
        try {
            noteRepository.deleteById(noteId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<Note> getNotesById(Long memId) {
        return noteRepository.findAll().stream().filter(m -> m.getMemberDetails().getId().equals(memId)).collect(Collectors.toList());
    }
}
