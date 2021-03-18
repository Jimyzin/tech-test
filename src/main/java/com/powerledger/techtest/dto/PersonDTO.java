package com.powerledger.techtest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.powerledger.techtest.entity.Person;

/**
 * This class represents the input request object comprising of
 * a name and its corresponding postcode.
 */
public class PersonDTO {

    private String name;
    private long postcode;

    public PersonDTO(String name, long postcode) {
        this.name = name;
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public long getPostcode() {
        return postcode;
    }

    @JsonIgnore
    public Person toPersonEntity() {
        Person personEntity = new Person();
        personEntity.setName(this.name);
        personEntity.setPostcode(this.postcode);
        return personEntity;
    }

}