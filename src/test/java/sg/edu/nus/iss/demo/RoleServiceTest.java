package sg.edu.nus.iss.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import sg.edu.nus.iss.demo.model.Role;
import sg.edu.nus.iss.demo.repository.RoleRepository;
import sg.edu.nus.iss.demo.service.RoleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Autowired
    @InjectMocks
    private RoleServiceImpl roleService;
    private Role role1;
    private Role role2;
    List<Role> roleList;

    @BeforeEach
    public void setUp() {
        roleList = new ArrayList<>();
        role1 = new Role();
        role1.setId((long) 1);
        role1.setDescription("Tester 1");
        role1.setRoleName("Tester1");
        role2 = new Role();
        role2.setId((long) 2);
        role2.setDescription("Tester 2");
        role2.setRoleName("Tester2");
        roleList.add(role1);
        roleList.add(role2);
    }

    @AfterEach
    public void tearDown() {
        role1 = role2 = null;
        roleList = null;
    }

    @Test
    void givenRoleToAddShouldReturnAddedRole() throws Exception {
        // stubbing
        when(roleRepository.save(any())).thenReturn(role1);
        roleService.saveRole(role1);
        verify(roleRepository, times(1)).save(any());
    }

    @Test
    public void GivenGetAllUsersShouldReturnListOfAllUsers() {
        roleRepository.save(role1);
        // stubbing mock to return specific data
        when(roleRepository.findAll()).thenReturn(roleList);
        List<Role> roleList1 = roleService.getRoleList();
        assertEquals(roleList1, roleList);
        verify(roleRepository, times(1)).save(role1);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void givenIdThenShouldReturnRoleOfThatId() {
        roleRepository.findById((long) 1);
        Mockito.when(Optional.of(role1)).thenReturn(Optional.of(role1));
        assertThat(roleService.getRoleById((long) 1).get()).isEqualTo(role1);
    }

    // @Test
    // public void givenIdTODeleteThenShouldDeleteTheRole() {
    //     when(roleService.deleteRoleById((long) 1)).thenThrow(new RuntimeException());
    //     // assertThat(roleService.getRoleById((long) 1).get()).asString();
    //     verify(roleRepository, times(1)).findAll();
    // }
}
