package sg.edu.nus.iss.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import sg.edu.nus.iss.demo.controller.AnnouncementController;
import sg.edu.nus.iss.demo.model.Announcement;
import sg.edu.nus.iss.demo.service.AnnouncementServiceImpl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AnnouncementControllerTest {
    @Mock
    private AnnouncementServiceImpl annService;
    private Announcement announcement;
    private List<Announcement> announcementList;

    @InjectMocks
    private AnnouncementController annController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        OffsetDateTime offsetDT1 = OffsetDateTime.now();

        announcement = new Announcement();
        announcement.setText("Test 1");
        announcement.setBroadcasted(true);
        announcement.setBroadcastBy("darryl");
        announcement.setBroadcastTime(offsetDT1);
        announcementList = new ArrayList<Announcement>();
        mockMvc = MockMvcBuilders.standaloneSetup(annController).build();
    }

    @AfterEach
    void tearDown() {
        announcement = null;
        announcementList = null;
    }

    @Test
    public void PostMappingOfRole() throws Exception {
            when(annService.saveAnnouncement(any())).thenReturn(announcement);
            mockMvc.perform(post("/api/announcements")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(announcement)))
                            .andExpect(status().isCreated());
            verify(annService, times(1)).saveAnnouncement(any());
    }

    @Test
    public void GetMappingOfAllRoles() throws Exception {
            when(annService.getAnnouncementList()).thenReturn(announcementList);
            mockMvc.perform(get("/api/announcements")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(announcementList)))
                            .andDo(MockMvcResultHandlers.print());
            verify(annService).getAnnouncementList();
            verify(annService, times(1)).getAnnouncementList();
    }


    @Test
    public void DeleteMappingUrlAndIdThenShouldReturnDeletedAnnouncement() throws Exception {
            when(annService.deleteAnnouncementById(1L)).thenReturn(true);
            mockMvc.perform(delete("/api/announcements/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(announcement)))
                            .andExpect(status().isOk())
                            .andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
