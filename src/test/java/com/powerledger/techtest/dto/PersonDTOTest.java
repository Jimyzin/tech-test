package com.powerledger.techtest.dto;

import com.powerledger.techtest.entity.Person;
import org.junit.Assert;
import org.junit.Test;

public class PersonDTOTest {

    @Test
    public void givenPersonDTO_whenMappedToPersonEntity_thenSuccessfully() {
        PersonDTO personDTO = new PersonDTO("John Doe", 5000);
        Person person = personDTO.toPersonEntity();
        Assert.assertEquals(personDTO.getName(), person.getName());
        Assert.assertEquals(personDTO.getPostcode(), person.getPostcode());
    }

}
