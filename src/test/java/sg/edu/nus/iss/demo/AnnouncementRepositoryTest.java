package sg.edu.nus.iss.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sg.edu.nus.iss.demo.model.Announcement;
import sg.edu.nus.iss.demo.repository.AnnouncementRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AnnouncementRepositoryTest {
    @Autowired
    private AnnouncementRepository announcementRepository;
    private Announcement announcement;

    @BeforeEach
    public void setup() {
        OffsetDateTime offsetDT1 = OffsetDateTime.now();
        
        announcement = new Announcement();
        announcement.setText("Lorem ipsum");
        announcement.setBroadcasted(true);
        announcement.setBroadcastBy("Darryl");
        announcement.setBroadcastTime(offsetDT1);
    }

    @AfterEach
    public void tearDown() {
        announcementRepository.deleteAll();
        announcement = null;
    }

    @Test
    public void givenAnnouncementToAddShouldReturnAddedAnnouncement() {
        announcementRepository.save(announcement);
        Announcement fetchedAnnouncement = announcementRepository.findById(announcement.getId()).get();
        assertEquals(1, fetchedAnnouncement.getId());
    }

    @Test
    public void givenGetAllAnnouncementsShouldReturnListOfAllAnnouncements() {
        Announcement announcement1 = new Announcement();
        announcement1.setText("Lorem Ipsum");
        announcement1.setBroadcasted(true);
        Announcement announcement2 = new Announcement();
        announcement2.setText("Lorem Ipsum2");
        announcement2.setBroadcasted(false);
        announcementRepository.save(announcement1);
        announcementRepository.save(announcement2);
        List<Announcement> announcementList = (List<Announcement>) announcementRepository.findAll();
        assertEquals("Lorem Ipsum2", announcementList.get(1).getText());
    }

    @Test
    public void givenIdThenShouldReturnannouncementOfThatId() {
        Announcement announcement1 = new Announcement();
        announcement1.setText("Lorem Ipsum");
        announcement1.setBroadcasted(true);
        Announcement announcement2 = announcementRepository.save(announcement1);
        Optional<Announcement> optional = announcementRepository.findById(announcement2.getId());
        assertEquals(announcement2.getId(), optional.get().getId());
        assertEquals(announcement2.getText(), optional.get().getText());
    }

    
    @Test
    public void givenIdTODeleteThenShouldDeleteTheAnnouncement() {
        Announcement announcement1 = new Announcement();
        announcement1.setText("Lorem Ipsum");
        announcement1.setBroadcasted(true);
        announcementRepository.save(announcement1);
        announcementRepository.deleteById(announcement1.getId());
        Optional<Announcement> optional = announcementRepository.findById(announcement1.getId());
        assertEquals(Optional.empty(), optional);
    }
}

