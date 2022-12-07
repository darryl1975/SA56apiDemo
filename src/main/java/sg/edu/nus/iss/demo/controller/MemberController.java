package sg.edu.nus.iss.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import sg.edu.nus.iss.demo.DemoApplication;
import sg.edu.nus.iss.demo.model.Member;
import sg.edu.nus.iss.demo.service.MemberService;

@Tag(description = "Member resources that provides access to availabe member data", name = "Member Resource")
@RestController
@RequestMapping("/api")
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    @Autowired
    private MemberService memberService;

    @Operation(summary = "Get members", description = "Provides all available member list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400", description = "${api.response-codes.badRequest.desc}", content = {
                    @Content(examples = { @ExampleObject(value = "") }) }),
            @ApiResponse(responseCode = "404", description = "${api.response-codes.notFound.desc}", content = {
                    @Content(examples = { @ExampleObject(value = "") }) }) })
    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        logger.info("Getting all members");

        try {
            List<Member> members = new ArrayList<Member>();
            members = memberService.getMemberList();

            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new member", description = "create a new member")
    @PostMapping("/members")
    public ResponseEntity<Member> saveMember(@RequestBody Member member) {
        logger.info("Creating new member");
        try {
            Member savedMember = memberService.saveMember(member);

            return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Modify member", description = "Modify an existing member details")
    @PutMapping("/members")
    public ResponseEntity<Member> updateMember(@RequestBody Member member) {
        logger.info("Update new member");

        try {
            Member savedMember = memberService.updateMember(member, member.getId());

            return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete member", description = "Delete an existing member details")
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Long> deleteMember(@PathVariable("id") Long id) {
        logger.info("Deleting existing member");
        try {
            var isRemoved = memberService.deleteMemberById(id);

            if (!isRemoved) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a member", description = "Retrieve an existing member details based on unique key ID")
    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id){
        logger.info("Get member, id" + id);

        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/members/name")
	public ResponseEntity<List<Member>> getMemberByName(@RequestParam String name) {
		return new ResponseEntity<List<Member>>(memberService.getMemebrByFullName(name), HttpStatus.OK);
	}
    
    @GetMapping("/members/email/{email}")
	public ResponseEntity<Optional<Member>> getMemberByEmail(@PathVariable String email) {
		return new ResponseEntity<Optional<Member>>(memberService.getMemberByEmail(email), HttpStatus.OK);
	}

    @GetMapping("/members/name/{name}")
	public ResponseEntity<List<Member>> getMemberByNameContaining(@PathVariable String name) {
		return new ResponseEntity<List<Member>>(memberService.getMemberByFullNameContaining(name), HttpStatus.OK);
	}
}
