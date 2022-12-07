package sg.edu.nus.iss.demo.service;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.iss.demo.model.Role;

public interface RoleService {
    Role saveRole(Role role);
    
    List<Role> getRoleList();

    Role updateRole(Role role, Long roleId);

    Boolean deleteRoleById(Long roleId);

    Optional<Role> getRoleById(Long roleId);
}
