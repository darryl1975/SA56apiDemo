package sg.edu.nus.iss.demo.service;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.iss.demo.model.Member;

public interface MemberService {
    Member saveMember(Member member);
    
    List<Member> getMemberList();

    Member updateMember(Member member, Long memberId);

    Boolean deleteMemberById(Long memberId);

    Optional<Member> getMemberById(Long memberId);

    Optional<Member> getMemberByEmail(String email);
    List<Member> getMemebrByFullName(String fullName);
    List<Member> getMemberByFullNameContaining(String fullName);
}
