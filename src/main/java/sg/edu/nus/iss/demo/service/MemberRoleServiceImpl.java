package sg.edu.nus.iss.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import sg.edu.nus.iss.demo.model.MemberRole;
import sg.edu.nus.iss.demo.repository.MemberRoleRepository;

public class MemberRoleServiceImpl implements MemberRoleService{

    @Autowired
    MemberRoleRepository memberRoleRepository;
    
    @Override
    public MemberRole saveMemberRole(MemberRole memberRole) {
        return memberRoleRepository.save(memberRole);
    }

    @Override
    public List<MemberRole> getMemberRoleList() {
        return memberRoleRepository.findAll();
    }

    @Override
    public MemberRole updateMemberRole(MemberRole memberRole) {

        return memberRoleRepository.save(memberRole);
    }

    @Override
    public Boolean deleteMemberRole(Long Id) {

        try {
            // memberRoleRepository.deleteById(Id);

            MemberRole retrievedMemberRole = memberRoleRepository.getReferenceById(Id);
            retrievedMemberRole.setDeleted(true);
            memberRoleRepository.save(retrievedMemberRole);
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
