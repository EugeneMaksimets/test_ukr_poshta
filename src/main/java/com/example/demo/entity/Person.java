package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "Persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonIgnoreProperties("persons")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level_id")
    private Level level;

    @JsonIgnoreProperties("personList")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Set<Project> projects;

}
