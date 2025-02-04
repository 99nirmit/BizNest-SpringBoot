package com.business.BizNest.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_permission")
@Data
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPermissionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;

}
