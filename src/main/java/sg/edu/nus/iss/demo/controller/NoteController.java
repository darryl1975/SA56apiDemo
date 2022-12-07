package sg.edu.nus.iss.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import sg.edu.nus.iss.demo.DemoApplication;
import sg.edu.nus.iss.demo.model.Note;
import sg.edu.nus.iss.demo.service.NoteService;

@Tag(description = "Note resources that provides access to availabe note data", name = "Note Resource")
@RestController
@RequestMapping("/api")
public class NoteController {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    @Autowired
    private NoteService noteService;

    @Operation(summary = "Get notes", description = "Provides all available note list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400", description = "${api.response-codes.badRequest.desc}", content = {
                    @Content(examples = { @ExampleObject(value = "") }) }),
            @ApiResponse(responseCode = "404", description = "${api.response-codes.notFound.desc}", content = {
                    @Content(examples = { @ExampleObject(value = "") }) }) })
    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllMembers() {
        logger.info("Getting all notes");

        try {
            List<Note> notes = new ArrayList<Note>();
            notes = noteService.getNoteList();

            if (notes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(notes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(summary = "Create new note", description = "create a new note")
    @PostMapping("/notes")
    public ResponseEntity<Note> saveNote(@RequestBody Note note) {
        logger.info("Creating new note");
        try {
            Note savedNote = noteService.saveNote(note);

            return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Modify note", description = "Modify an existing note details")
    @PutMapping("/notes")
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        logger.info("Update new note");

        try {
            Note savedNote = noteService.updateNote(note, note.getNoteId());

            return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Delete note", description = "Delete an existing note details")
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Long> deleteMember(@PathVariable("id") Long id) {
        logger.info("Deleting existing note");
        try {
            var isRemoved = noteService.deleteNoteById(id);

            if (!isRemoved) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
