package sg.edu.nus.iss.demo.service;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.demo.model.MemberNote;

@Service
public interface MemberNotesService {
    public String createMemberAndAddNote(MemberNote memberNote) throws Exception;
    public MemberNote getNoteListByMemberId(Long id);

    //addNoteToSpecificMember
}
