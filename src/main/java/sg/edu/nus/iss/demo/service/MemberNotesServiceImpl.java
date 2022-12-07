package sg.edu.nus.iss.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.demo.model.Member;
import sg.edu.nus.iss.demo.model.MemberNote;
import sg.edu.nus.iss.demo.model.Note;
import sg.edu.nus.iss.demo.util.ApplicationConstants;

@Service
public class MemberNotesServiceImpl implements MemberNotesService {
    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    private NoteServiceImpl noteService;

    @Override
    @Transactional
    public String createMemberAndAddNote(MemberNote memberNote) throws Exception {
        // create new member
        Member createdMember = memberService.saveMember(memberNote.getMember());

        List<Note> noteList = memberNote.getNotes();

        for (Note note : noteList) {
            Note dbNote = new Note();
            dbNote.setDeleted(note.isDeleted());
            dbNote.setCreatedBy(note.getCreatedBy());
            dbNote.setCreatedTime(note.getCreatedTime());
            dbNote.setLastUpdatedBy(note.getLastUpdatedBy());
            dbNote.setLastUpdatedTime(note.getLastUpdatedTime());

            dbNote.setTitle(note.getTitle());
            dbNote.setMessage(note.getMessage());

            // set created member to note
            dbNote.setMemberDetails(createdMember);

            // persist new note
            noteService.saveNote(dbNote);
        }

        return ApplicationConstants.ADDED_NOTE_DESC;
    }

    @Override
    public MemberNote getNoteListByMemberId(Long id) {
        Member existingMember = memberService.getMemberList().stream().filter(mem -> mem.getId().equals(id)).findFirst().get();

        List<Note> noteList = noteService.getNotesById(id);

        MemberNote memNote = new MemberNote();
        memNote.setMember(existingMember);

        memNote.setNotes(noteList);

        return memNote;
    }
}
