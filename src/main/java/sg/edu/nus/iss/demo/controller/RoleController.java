package sg.edu.nus.iss.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
// import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.nus.iss.demo.DemoApplication;
import sg.edu.nus.iss.demo.model.Role;
import sg.edu.nus.iss.demo.service.RoleService;

@Tag(description = "Role resources that provides access to availabe role data", name = "Role Resource")
@RestController
@RequestMapping("/api")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    @Autowired
    private RoleService roleService;

    @Operation(summary = "Get roles", description = "Provides all available role list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400", description = "${api.response-codes.badRequest.desc}", content = {
                    @Content(examples = { @ExampleObject(value = "") }) }),
            @ApiResponse(responseCode = "404", description = "${api.response-codes.notFound.desc}", content = {
                    @Content(examples = { @ExampleObject(value = "") }) }) })
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        logger.info("Getting all roles");

        try {
            List<Role> roles = new ArrayList<Role>();
            roles = roleService.getRoleList();

            if (roles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new role", description = "create a new role")
    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        logger.info("Creating new role");
        try {
            Role savedRole = roleService.saveRole(role);

            return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Modify role", description = "Modify an existing role details")
    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        logger.info("Update new role");

        try {
            Role savedRole = roleService.updateRole(role, role.getId());

            return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // https://www.stevenschwenke.de/ReturningAnEmptyResponseEntityInSpringMVC
    @Operation(summary = "Delete role", description = "Delete an existing role details")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Long> deleteRole(@PathVariable("id") Long id) {
        logger.info("Deleting existing role");
        try {
            var isRemoved = roleService.deleteRoleById(id);

            if (!isRemoved) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a role", description = "Retrieve an existing role details based on unique key ID")
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id){
        logger.info("Get role, id" + id);

        return roleService.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
