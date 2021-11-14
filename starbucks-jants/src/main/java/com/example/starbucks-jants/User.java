package com.example.jants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@RequiredArgsConstructor
public class User {

    private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;

    @Column(nullable = false) private String first_name;
    @Column(nullable = false) private String last_name;
    @Column(nullable = false) private String email;
    @Column(nullable = false) private String password;
}
