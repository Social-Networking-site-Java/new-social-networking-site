package com.titus.socialnetworkingsite2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.titus.socialnetworkingsite2.Dto.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
@EntityListeners(AuditingEntityListener.class) // this will keep truck of when the user is created and the last time it was modified
// implement getName from principal
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    private String password;
    private String profilePictureName;
    private String profilePictureUrl;
    private Status status;

    @Column(unique = true)
    private String email;
    private boolean accountLocked;
    private boolean enabled;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Contacts> contacts;

    // Method to add a contact to the user's contacts list
    @JsonIgnore
    public void addContact(User contactUser) {
        if (contacts == null) {
            contacts = new ArrayList<>();
        }

        Contacts contact = new Contacts();
        contact.setFirstName(contactUser.getFirstname());
        contact.setUser(this); // Set the user associated with this contact

        contacts.add(contact);
    }




//    @JsonIgnore
//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<BlackListUser> blackListUsers;

   // private List<BlackListUser> blackListUsers;


    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Role> roles;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;



    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

//    public void addContact(String firstname) {
//        contacts.add(firstname);
//    }


    public String fullName(){
        return  firstname.toUpperCase() + " " + lastname;
    }
}
