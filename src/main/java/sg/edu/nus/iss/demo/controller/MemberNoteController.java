package sg.edu.nus.iss.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.nus.iss.demo.DemoApplication;
import sg.edu.nus.iss.demo.model.MemberNote;
import sg.edu.nus.iss.demo.service.MemberNotesServiceImpl;


@Tag(description = "Member resources that provides access to availabe member data", name = "Member Note Resource")
@RestController
@RequestMapping("/api")
public class MemberNoteController {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    
    @Autowired
    private MemberNotesServiceImpl memberNotesService;
    
    @Operation(summary = "Create new member with notes", description = "create a new member with notes")
    @PostMapping("/membernotes")
    public ResponseEntity<String> saveMemberNote(@RequestBody MemberNote memberNote) {
        logger.info("Creating new member note");
        try {
            String createdResult = memberNotesService.createMemberAndAddNote(memberNote);
            
            return new ResponseEntity<>(createdResult, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a member notes", description = "Retrieve all notes of an existing member")
    @GetMapping("/membernotes/members/{id}")
    public ResponseEntity<MemberNote> getRoleById(@PathVariable("id") Long id){
        logger.info("Retrieving all notes of  an existing member by memberID");

        MemberNote returnedMemberNote =  memberNotesService.getNoteListByMemberId(id);
        return new ResponseEntity<>(returnedMemberNote, HttpStatus.OK);
    }
}
