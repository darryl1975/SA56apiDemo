package sg.edu.nus.iss.demo.service;

import java.util.List;

import sg.edu.nus.iss.demo.model.Announcement;

public interface AnnouncementService {
    Announcement saveAnnouncement(Announcement announcement);
    
    List<Announcement> getAnnouncementList();

    Announcement updateAnnouncement(Announcement announcement, Long announcementId);

    Boolean deleteAnnouncementById(Long announcementId);
}
