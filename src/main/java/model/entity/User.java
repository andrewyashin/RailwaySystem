package model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity to table <b>USER</b>
 *
 * @author Andrii Yashin
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "select distinct u from User u"),
        @NamedQuery(name = "User.findById", query = "select distinct u from User u where u.id = :id"),
        @NamedQuery(name = "User.findByEmail", query = "select distinct u from User u where u.email = :email")
})
public class User implements Serializable{
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private Boolean admin;

    private Set<Request> requests = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Request> getRequests() {
        return requests;
    }

    @Column(name = "admin")
    public Boolean getAdmin(){
        return admin;
    }

    public void makeAdmin(){
        admin = true;
    }

    public void makeUser(){
        admin = false;
    }

}
