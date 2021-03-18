package com.powerledger.techtest.dto;


import java.util.List;

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
