package com.powerledger.techtest.entity;

import com.powerledger.techtest.entity.id.PersonId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity(name = "person")
@IdClass(PersonId.class)
public class Person {

    @Id
    @NotNull(message = "Name cannot be null")
    private String name;

    @Id
    @Min(value = 1, message = "Postcode should be non-negative and non-zero")
    private long postcode;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPostcode() {
        return postcode;
    }

    public void setPostcode(long postcode) {
        this.postcode = postcode;
    }
}
