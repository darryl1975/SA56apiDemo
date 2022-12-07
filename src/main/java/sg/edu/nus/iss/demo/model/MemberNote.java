package sg.edu.nus.iss.demo.model;

import java.util.List;

import org.springframework.data.annotation.Transient;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class MemberNote {
    
    @Transient
    private Member member;

    @Transient
    private List<Note> noteList;

    public Member getMember() {
        return this.member;
    }
    
    public void setMember(Member member) {
        this.member = member;
    }

    public List<Note> getNotes() {
        return this.noteList;
    }

    public void setNotes(List<Note> noteList) {
        this.noteList = noteList;
    }
}
