package sg.edu.nus.iss.demo.service;

import java.util.List;

import sg.edu.nus.iss.demo.model.Note;

public interface NoteService {
    Note saveNote(Note note);
    
    List<Note> getNoteList();

    // List<Note> getNoteContainingTitle(String title);

    Note updateNote(Note note, Long noteId);

    Boolean deleteNoteById(Long noteId);

    List<Note> getNotesById(Long memberId);
}
