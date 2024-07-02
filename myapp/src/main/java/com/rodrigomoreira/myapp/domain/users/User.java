package com.rodrigomoreira.myapp.domain.users;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rodrigomoreira.myapp.domain.courses.Course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="users")
@Table(name="users")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User {

    @Setter
    @Id
    private Long id;

    @Setter
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @Setter
    @NotEmpty
    @Column(unique=true, nullable = false)
    private String email;

    @Setter
    @NotEmpty
    @Column(unique=true, nullable = false)
    private String document;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToMany
    @JoinTable(name = "tb_user_course", 
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id"))
    @JsonIgnoreProperties("users")
    private Set<Course> courses = new HashSet<>();

    public User(String document) {
        this.document = document;
    }
    
    public User(Long id, String name, String email, String document, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.document = document;
        this.userType = userType;
    }

}
