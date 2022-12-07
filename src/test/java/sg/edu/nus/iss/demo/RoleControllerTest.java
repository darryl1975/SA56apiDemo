package sg.edu.nus.iss.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import sg.edu.nus.iss.demo.controller.RoleController;
import sg.edu.nus.iss.demo.model.Role;
import sg.edu.nus.iss.demo.service.RoleServiceImpl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

// https://springframework.guru/testing-spring-boot-restful-services/

// @WebMvcTest
@ExtendWith(MockitoExtension.class)
// @SpringBootTest
// @AutoConfigureMockMvc
public class RoleControllerTest {

        @Mock
        private RoleServiceImpl roleService;
        private Role role;
        private List<Role> roleList;

        @InjectMocks
        private RoleController roleController;

        @Autowired
        private MockMvc mockMvc;

        @BeforeEach
        public void setup() {
                role = new Role();
                role.setRoleName("Test 1");
                role.setDescription("Test 1");
                roleList = new ArrayList<Role>();
                mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
        }

        @AfterEach
        void tearDown() {
                role = null;
                roleList = null;
        }

        @Test
        public void PostMappingOfRole() throws Exception {
                when(roleService.saveRole(any())).thenReturn(role);
                mockMvc.perform(post("/api/roles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(role)))
                                .andExpect(status().isCreated());
                verify(roleService, times(1)).saveRole(any());
        }

        @Test
        public void GetMappingOfAllRoles() throws Exception {
                when(roleService.getRoleList()).thenReturn(roleList);
                mockMvc.perform(get("/api/roles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(roleList)))
                                .andDo(MockMvcResultHandlers.print());
                verify(roleService).getRoleList();
                verify(roleService, times(1)).getRoleList();
        }

        @Test
        public void GetMappingOfAllRole() throws Exception {
                when(roleService.getRoleList()).thenReturn(roleList);
                mockMvc.perform(MockMvcRequestBuilders.get("/api/roles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(roleList)))
                                .andDo(MockMvcResultHandlers.print());
                verify(roleService).getRoleList();
                verify(roleService, times(1)).getRoleList();
        }

        @Test
        public void GetMappingOfRoleShouldReturnRespectiveRole() throws Exception {
                when(roleService.getRoleById(1L)).thenReturn(Optional.of(role));
                mockMvc.perform(get("/api/roles/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(role)))
                                .andExpect(status().isOk())
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        public void DeleteMappingUrlAndIdThenShouldReturnDeletedRole() throws Exception {
                when(roleService.deleteRoleById(1L)).thenReturn(true);
                mockMvc.perform(delete("/api/roles/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(role)))
                                .andExpect(status().isOk())
                                .andDo(MockMvcResultHandlers.print());
        }

        public static String asJsonString(final Object obj) {
                try {
                        return new ObjectMapper().writeValueAsString(obj);
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }
}
