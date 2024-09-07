package com.archi_sub.archi_sub.common.enumobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum RoleType implements Serializable {

    ADMIN("ADMIN")
    ,USER("USER");

    private String role;

}
