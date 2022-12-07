package sg.edu.nus.iss.demo.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.demo.model.Role;
import sg.edu.nus.iss.demo.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository repo) {
        this.roleRepository = repo;
    }

    @CacheEvict(cacheNames = {"roleCache"}, key = "#id")
    @Override
    public Boolean deleteRoleById(Long roleId) {
        try {
            // Optional<Role> optional = roleRepository.findById(roleId);
            // if (optional.isPresent()) {
                roleRepository.deleteById(roleId);
                return true;
            // }

            // return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Cacheable(cacheNames = {"roleCache"})
    @Override
    public List<Role> getRoleList() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {

        return roleRepository.save(role);
    }

    @CachePut(cacheNames = {"roleCache"}, key="#id")
    @Override
    public Role updateRole(Role role, Long roleId) {

        Role roleEnt = roleRepository.findById(roleId).get();

        if (Objects.nonNull(role.getRoleName()) && !"".equalsIgnoreCase(role.getRoleName())) {
            roleEnt.setRoleName(role.getRoleName());
        }

        if (Objects.nonNull(role.getDescription()) && !"".equalsIgnoreCase(role.getDescription())) {
            roleEnt.setDescription(role.getDescription());
        }

        return roleRepository.save(roleEnt);
    }

    // @Cacheable(cacheNames = {"roleCache"}, key = "#id")
    @Override
    public Optional<Role> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

}
