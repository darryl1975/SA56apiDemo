package sg.edu.nus.iss.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sg.edu.nus.iss.demo.Exception.ResourceNotFoundException;
import sg.edu.nus.iss.demo.model.Announcement;
import sg.edu.nus.iss.demo.repository.AnnouncementRepository;
import sg.edu.nus.iss.demo.service.AnnouncementServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AnnouncementServiceTest {
    @Mock
    private AnnouncementRepository annRepository;

    @InjectMocks
    private AnnouncementServiceImpl annService;

    private Announcement announcement;

    @BeforeEach
    public void setup() {

        annService = new AnnouncementServiceImpl(annRepository);

        announcement = Announcement.builder()
                .id(1L)
                .text("Lorem Ipsum")
                .build();
    }

    // JUnit test for saveAnnouncement method
    @DisplayName("JUnit test for saveAnnouncement method")
    @Test
    public void givenAnnouncementObject_whenSaveAnnouncement_thenReturnAnnouncementObject() {

        // given - precondition or setup
        // given(annRepository.findById(announcement.getId()))
        // .willReturn(Optional.empty());

        given(annRepository.save(announcement)).willReturn(announcement);

        System.out.println(annRepository);
        System.out.println(annService);

        // when - action or the behaviour that we are going test
        Announcement savedAnnouncement = annService.saveAnnouncement(announcement);

        System.out.println(savedAnnouncement);
        // then - verify the output
        assertThat(savedAnnouncement).isNotNull();
    }

    // JUnit test for saveAnnouncement method
    @DisplayName("JUnit test for saveAnnouncement method which throws exception")
    @Test
    public void givenExistingId_whenSaveAnnouncement_thenThrowsException() {
        // given - precondition or setup
        given(annRepository.findById(2L))
                .willReturn(Optional.of(announcement));

        System.out.println(annRepository);
        System.out.println(annService);

        // when - action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            annService.saveAnnouncement(announcement);
        });

        // then
        verify(annRepository, never()).save(any(Announcement.class));
    }

    // JUnit test for getAllAnnouncements method
    @DisplayName("JUnit test for getAllAnnouncements method")
    @Test
    public void givenAnnouncementList_whenGetAlAnnouncements_thenReturnAnnouncementList() {

        // given - precondition or setup
        Announcement announcement1 = Announcement.builder()
                .id(2L)
                .text("Lorem Ipsum")
                .build();

        given(annRepository.findAll()).willReturn(List.of(announcement, announcement1));

        // when - action or the behaviour that we are going test
        List<Announcement> announcementList = annService.getAnnouncementList();

        System.out.println();
        System.out.print("announcementList");
        System.out.println();
        System.out.print(announcementList);

        // then - verify the output
        assertThat(announcementList).isNotNull();
        assertThat(announcementList.size()).isEqualTo(2);
    }

    // JUnit test for getAllAnnouncements method
    @DisplayName("JUnit test for getAllAnnouncements method (negative scenario)")
    @Test
    public void givenEmptyAnnouncementList_whenGetAllAnnouncements_thenReturnEmptyAnnouncementList() {
        // given - precondition or setup

        given(annRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behaviour that we are going test
        List<Announcement> announcementList = annService.getAnnouncementList();

        // then - verify the output
        assertThat(announcementList).isEmpty();
        assertThat(announcementList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getAnnouncementById method")
    @Test
    public void givenAnnouncementId_whenGetAnnouncementById_thenReturnAnnouncementObject() {

        System.out.print("givenAnnouncementId_whenGetAnnouncementById_thenReturnAnnouncementObject");
        System.out.println();
        System.out.print(announcement);

        // when
        Optional<Announcement> savedAnnouncement = annService.getAnnouncementList().stream()
                .filter(ann -> ann.getId() == announcement.getId()).findAny();
        System.out.println();
        System.out.print("savedAnnouncement");
        System.out.println();
        System.out.print(savedAnnouncement);

        // then
        assertThat(savedAnnouncement).isEmpty();
    }

    // JUnit test for updateAnnouncement method
    @DisplayName("JUnit test for updateAnnouncement method")
    @Test
    public void givenAnnouncementObject_whenUpdateAnnouncement_thenReturnUpdatedAnnouncement() {
        // given - precondition or setup
        given(annRepository.save(announcement)).willReturn(announcement);

        System.out.print("givenAnnouncementObject_whenUpdateAnnouncement_thenReturnUpdatedAnnouncement");
        System.out.println();
        System.out.print(announcement);
        announcement.setText("Lorem Ipsum");

        // when - action or the behaviour that we are going test
        Announcement updatedAnnouncement = annService.updateAnnouncement(announcement, 1L);

        // then - verify the output
        assertThat(updatedAnnouncement.getText()).isEqualTo("Lorem Ipsum");
    }

    // JUnit test for deleteAnnouncement method
    @DisplayName("JUnit test for deleteAnnouncement method")
    @Test
    public void givenAnnouncementId_whenDeleteAnnouncement_thenNothing() {
        // given - precondition or setup
        Long announcementId = 1L;

        willDoNothing().given(annRepository).deleteById(announcementId);

        // when - action or the behaviour that we are going test
        annService.deleteAnnouncementById(announcementId);

        // then - verify the output
        verify(annRepository, times(1)).deleteById(announcementId);
    }
}
