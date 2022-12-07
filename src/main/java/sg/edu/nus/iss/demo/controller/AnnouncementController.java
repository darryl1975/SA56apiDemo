package sg.edu.nus.iss.demo.controller;

import java.util.ArrayList;
import java.util.List;

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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.nus.iss.demo.model.Announcement;
import sg.edu.nus.iss.demo.service.AnnouncementService;

@Tag(description = "Announcement resources that provides access to availabe announcement data", name = "Announcement Resource")
@RestController
@RequestMapping("/api")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @Operation(summary = "Get announcements", description = "Provides all available announcement list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400", description = "${api.response-codes.badRequest.desc}", content = {
                    @Content(examples = { @ExampleObject(value = "") }) }),
            @ApiResponse(responseCode = "404", description = "${api.response-codes.notFound.desc}", content = {
                    @Content(examples = { @ExampleObject(value = "") }) }) })
    @GetMapping("/announcements")
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        try {
            List<Announcement> announcements = new ArrayList<Announcement>();
            announcements = announcementService.getAnnouncementList();

            if (announcements.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(announcements, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new announcement", description = "create a new announcement")
    @PostMapping("/announcements")
    public ResponseEntity<Announcement> saveAnnouncement(@RequestBody Announcement announcement) {
        try {
            Announcement savedAnnouncement = announcementService.saveAnnouncement(announcement);

            return new ResponseEntity<>(savedAnnouncement, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Modify announcement", description = "Modify an existing announcement details")
    @PutMapping("/announcements")
    public ResponseEntity<Announcement> updateAnnouncement(@RequestBody Announcement announcement) {
        try {
            Announcement savedAnnouncement = announcementService.updateAnnouncement(announcement, announcement.getId());

            return new ResponseEntity<>(savedAnnouncement, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete announcement", description = "Delete an existing announcement details")
    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<Long> deleteCategory(@PathVariable("id") Long id) {
        try {
            var isRemoved = announcementService.deleteAnnouncementById(id);

            if (!isRemoved) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
