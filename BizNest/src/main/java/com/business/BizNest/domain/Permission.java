package com.business.BizNest.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "permissions")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    private String permissionName;

    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<User> users;

    @JsonCreator
    public Permission(@JsonProperty("permissionName") String permissionName){
        this.permissionName = permissionName;
    }

    public Permission(long id, String permissionName) {
     this.permissionId = id;
     this.permissionName = permissionName;
    }
}
