package com.powerledger.techtest.entity.id;

import java.io.Serializable;

/**
 * This class represents the composite key (name, postcode).
 * This serves as table id.
 */
public class PersonId implements Serializable {

    private String name;

    private long postcode;

    public PersonId() {
    }

    public PersonId(String name, long postcode) {
        this.name = name;
        this.postcode = postcode;
    }
}
