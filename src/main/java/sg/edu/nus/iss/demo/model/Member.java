package sg.edu.nus.iss.demo.model;

import java.util.ArrayList;
import java.util.List;
// import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Member")
public class Member extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(150) not null")
    private String fullName;

    @Email(message = "Email should be valid")
    @Size(max = 200)
    @Pattern(regexp = ".+@.+\\..+", message = "Wrong email!")
    @Column(columnDefinition = "nvarchar(200) not null")
    private String email;

    @Min(value = 1, message = "Min value for day is 1")
    @Max(value = 31, message = "Max value for day is 31")
    @Column(columnDefinition = "int not null")
    private Integer BirthDay;

    @Min(value = 1, message = "Min value for month is 1")
    @Max(value = 12, message = "Max value for month is 12")
    @Column(columnDefinition = "int not null")
    private Integer BirthMonth;

    @Pattern(regexp = "(\\8|9)[0-9]{7}")
    @Column(columnDefinition = "nvarchar(max)")
    private String MobilePhone;

    @Digits(fraction = 0, integer = 6)
    @Column(columnDefinition = "int not null")
    private Integer PostalCode;

    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // @JoinTable(name = "MemberRole", joinColumns = {
    //         @JoinColumn(name = "Member_Id", referencedColumnName = "Id") }, inverseJoinColumns = {
    //                 @JoinColumn(name = "Role_Id", referencedColumnName = "Id") })
    // private Set<Role> roles;
    // private List<Role> roles;

    @Column(nullable = false, name = "password")
    private String password;

    @OneToMany(targetEntity = MemberRole.class, mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    private List<MemberRole> memberRoleList;

    @Transient
    private List<Role> myRoles;

    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<Role>();

        if (this.memberRoleList == null) {
            return roles;
        }

        for (MemberRole memRole : this.memberRoleList) {
            roles.add(memRole.getRole());
        }

        this.myRoles = roles;
        return this.myRoles;
    }

    public void setRole(List<Role> roleList) {
        for (Role r : roleList) {
            MemberRole mr = new MemberRole();
            mr.setMember(this);
            mr.setRole(r);

            if (this.memberRoleList != null) {
                boolean alreadyExists = false;

                for(MemberRole existingMemRole : this.memberRoleList) {
                    if (r.getId() == existingMemRole.getRole().getId()) {
                        alreadyExists = true;
                        break;
                    }
                }

                if (!alreadyExists) {
                    this.memberRoleList.add(mr);
                }
            } else {
                this.memberRoleList = new ArrayList<MemberRole>();
                this.memberRoleList.add(mr);
            }
        }
    }
    
    public void removeRole(MemberRole memRole) {
        this.memberRoleList.remove(memRole);
    }
}
