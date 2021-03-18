package com.powerledger.techtest.utils;


import com.powerledger.techtest.dto.PersonDTO;
import com.powerledger.techtest.entity.Person;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataUtils {

    public static List<Person> preparePersonList() {

        Person johnDoe = new Person();
        johnDoe.setName("John Doe");
        johnDoe.setPostcode(6000);

        Person ashleyRogers = new Person();
        ashleyRogers.setName("Ashley Rogers");
        ashleyRogers.setPostcode(6000);

        Person brianSanders = new Person();
        brianSanders.setName("Brian Sanders");
        brianSanders.setPostcode(6002);

        Person ashleyBuchanan = new Person();
        ashleyBuchanan.setName("Ashley Buchanan");
        ashleyBuchanan.setPostcode(6001);

        Person josephFry = new Person();
        josephFry.setName("Joseph Fry");
        josephFry.setPostcode(7800);

        Person ashlleyRogers = new Person();
        ashlleyRogers.setName("Ashlley Rogers");
        ashlleyRogers.setPostcode(6000);

        return Arrays.asList(johnDoe, ashleyRogers, brianSanders, ashleyBuchanan, josephFry, ashlleyRogers);
    }

    public static String[] prepareOrderedNames() {
        return new String[]{"Ashley Buchanan", "Ashley Rogers", "Ashlley Rogers", "Brian Sanders", "John Doe"};
    }

    public static Map<String, PersonDTO> prepareMapData() {
        Map<String, PersonDTO> mappedData = new HashMap<>();

        mappedData.put("John Doe", new PersonDTO("John Doe", 6000));
        mappedData.put("Ashley Rogers", new PersonDTO("Ashley Rogers", 6000));
        mappedData.put("Brian Sanders", new PersonDTO("Brian Sanders", 6002));
        mappedData.put("Ashley Buchanan", new PersonDTO("Ashley Buchanan", 6001));
        mappedData.put("Joseph Fry", new PersonDTO("Joseph Fry", 7800));
        mappedData.put("Ashlley Rogers", new PersonDTO("Ashlley Rogers", 6000));
        return mappedData;
    }
}
