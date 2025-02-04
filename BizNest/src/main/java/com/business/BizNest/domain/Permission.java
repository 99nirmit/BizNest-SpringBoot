package com.business.BizNest.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    private String permissionName;

    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<User> users;
}
