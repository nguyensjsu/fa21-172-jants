package com.example.springrewards;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Rewards")
@Data
@RequiredArgsConstructor
public class Reward {

    private @Id @GeneratedValue Long id;

    private String email;
    public int starsBalance;
    private int redeemedPoints;
    private String redeemedItem;
}
