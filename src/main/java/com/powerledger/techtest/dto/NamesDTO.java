package com.powerledger.techtest.dto;


import java.util.List;

/**
 * This class represents the response object for retrieving operation.
 * It comprises of a names list and an integer to hold total number of
 * characters in the names in the list.
 */
public class NamesDTO {

    private int totalNumberOfCharacters;
    private List<String> names;

    public NamesDTO(int totalNumberOfCharacters, List<String> names) {
        this.totalNumberOfCharacters = totalNumberOfCharacters;
        this.names = names;
    }

    public int getTotalNumberOfCharacters() {
        return totalNumberOfCharacters;
    }

    public List<String> getNames() {
        return names;
    }
}
