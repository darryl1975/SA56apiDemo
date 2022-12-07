package sg.edu.nus.iss.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sg.edu.nus.iss.demo.model.Role;
import sg.edu.nus.iss.demo.repository.RoleRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;
    private Role role;

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setDescription("Tester 1");
        role.setRoleName("Tester1");
    }

    @AfterEach
    public void tearDown() {
        roleRepository.deleteAll();
        role = null;
    }

    @Test
    public void givenRoleToAddShouldReturnAddedRole() {
        roleRepository.save(role);
        Role fetchedRole = roleRepository.findById(role.getId()).get();
        assertEquals(1, fetchedRole.getId());
    }

    @Test
    public void GivenGetAllRoleShouldReturnListOfAllRoles() {
        Role role1 = new Role();
        role1.setRoleName("bat");
        role1.setDescription("bat");
        Role role2 = new Role();
        role2.setRoleName("spider");
        role2.setDescription("spider");
        roleRepository.save(role1);
        roleRepository.save(role2);
        List<Role> roleList = (List<Role>) roleRepository.findAll();
        assertEquals("spider", roleList.get(1).getRoleName());
    }

    @Test
    public void givenIdThenShouldReturnRoleOfThatId() {
        Role role1 = new Role();
        role1.setRoleName("bat");
        role1.setDescription("bat");
        Role role2 = roleRepository.save(role1);
        Optional<Role> optional = roleRepository.findById(role2.getId());
        assertEquals(role2.getId(), optional.get().getId());
        assertEquals(role2.getRoleName(), optional.get().getRoleName());
    }

    @Test
    public void givenIdTODeleteThenShouldDeleteTheRole() {
        Role role1 = new Role();
        role1.setRoleName("bat");
        role1.setDescription("bat");
        roleRepository.save(role1);
        roleRepository.deleteById(role1.getId());
        Optional<Role> optional = roleRepository.findById(role1.getId());
        assertEquals(Optional.empty(), optional);
    }
}
