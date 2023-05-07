package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "Levels")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @JsonIgnoreProperties("level")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "persons")
    private List<Person> persons;

}
