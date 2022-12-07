package sg.edu.nus.iss.demo.service;

// import java.io.Console;
import java.util.ArrayList;
import java.util.List;
// import java.util.Objects;
import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import sg.edu.nus.iss.demo.DemoApplication;
import sg.edu.nus.iss.demo.model.Member;
import sg.edu.nus.iss.demo.model.MemberRole;
import sg.edu.nus.iss.demo.model.Role;
import sg.edu.nus.iss.demo.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
    // private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    
    @Autowired
    MemberRepository memberRepository;
    
    @Override
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    @Override
    public Member updateMember(Member member, Long memberId) {
        Member memberEnt = memberRepository.findById(memberId).get();
        this.updateMemberRole(memberEnt, member);

        // if (Objects.nonNull(member.getFullName()) && !"".equalsIgnoreCase(member.getFullName())) {
        //     memberEnt.setFullName(member.getFullName());
        // }

        // if (Objects.nonNull(member.getEmail()) && !"".equalsIgnoreCase(member.getEmail())) {
        //     memberEnt.setEmail(member.getEmail());
        // }

        // if (Objects.nonNull(member.getMobilePhone()) && !"".equalsIgnoreCase(member.getMobilePhone())) {
        //     memberEnt.setMobilePhone(member.getMobilePhone());
        // }

        // if (Objects.nonNull(member.getPostalCode())) {
        //     memberEnt.setPostalCode(member.getPostalCode());
        // }

        // if (Objects.nonNull(member.getBirthDay())) {
        //     memberEnt.setBirthDay(member.getBirthDay());
        // }

        // if (Objects.nonNull(member.getBirthMonth())) {
        //     memberEnt.setBirthMonth(member.getBirthMonth());
        // }


        return memberRepository.save(member);
    }

    @Override
    public Boolean deleteMemberById(Long memberId) {
        try {
            memberRepository.deleteById(memberId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Member> getMemebrByFullName(String fullName) {
        return memberRepository.findByFullName(fullName);
    }

    @Override
    public List<Member> getMemberByFullNameContaining(String fullName) {
        return memberRepository.findByFullNameContaining(fullName);
    }

    // Utility
    private void updateMemberRole(Member existingMemberDetails, Member updatedMemberDetails) {
        List<MemberRole> mergedMemberRole = new ArrayList<MemberRole>();

        for (Role r : updatedMemberDetails.getMyRoles()) {
            MemberRole mr = new MemberRole();
            mr.setRole(r);
            mr.setMember(existingMemberDetails);
            boolean alreadyExists = false;
            for (MemberRole mRole : existingMemberDetails.getMemberRoleList()) {
                if (r.getId() == mRole.getRole().getId()) {
                    alreadyExists = true;
                    mergedMemberRole.add(mRole);
                    break;
                }
            }
            if (!alreadyExists) {
                mergedMemberRole.add(mr);
            }
        }
        updatedMemberDetails.setMemberRoleList(mergedMemberRole);
    }
}
