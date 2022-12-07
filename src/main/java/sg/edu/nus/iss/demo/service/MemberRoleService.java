package sg.edu.nus.iss.demo.service;

import java.util.List;

import sg.edu.nus.iss.demo.model.MemberRole;

public interface MemberRoleService {
    MemberRole saveMemberRole(MemberRole memberRole);
    
    List<MemberRole> getMemberRoleList();

    MemberRole updateMemberRole(MemberRole memberRole);

    Boolean deleteMemberRole(Long Id);
}
