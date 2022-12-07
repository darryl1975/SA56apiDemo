package sg.edu.nus.iss.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.demo.model.Announcement;
import sg.edu.nus.iss.demo.repository.AnnouncementRepository;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    AnnouncementRepository announcementRepository; 

    public AnnouncementServiceImpl(AnnouncementRepository repo) {
        this.announcementRepository = repo;
    }


    @Override
    public Announcement saveAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public List<Announcement> getAnnouncementList() {
        return announcementRepository.findAll();
    }

    @Override
    public Announcement updateAnnouncement(Announcement announcement, Long announcementId) {
        Announcement announcementyEnt = announcementRepository.findById(announcementId).get();

        if (Objects.nonNull(announcement.getText()) && !"".equalsIgnoreCase(announcement.getText())) {
            announcementyEnt.setText(announcement.getText());
        }

        return announcementRepository.save(announcementyEnt);
    }

    @Override
    public Boolean deleteAnnouncementById(Long announcementId) {
        try {
            announcementRepository.deleteById(announcementId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
