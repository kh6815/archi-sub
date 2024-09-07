package com.archi_sub.archi_sub.common.enumobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum BooleanFlag implements Serializable {

    Y("Y")
    ,N("N");

    private String type;

}
