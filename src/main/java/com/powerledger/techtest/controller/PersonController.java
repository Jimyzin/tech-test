package com.powerledger.techtest.controller;

import com.powerledger.techtest.dto.NamesDTO;
import com.powerledger.techtest.dto.PersonDTO;
import com.powerledger.techtest.entity.Person;
import com.powerledger.techtest.exception.TechTestException;
import com.powerledger.techtest.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Creates list of persons in H2 database
     *
     * @param persons list of persons
     * @return saved list of persons
     */
    @PostMapping(value = "/createAll", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Person> createAll(@RequestBody List<PersonDTO> persons) {
        LOG.info("Invoked PersonController.createAll() at {}", new Date());
        return personService.createAll(persons);
    }

    /**
     * Retrieves a list of alphabetically ordered names corresponding to the range of postcodes.
     * The response also contains sum total of characters of list of names in the response.
     *
     * @param startRange the starting postcode (mandatory parameter)
     * @param endRange the ending postcode (mandatory parameter)
     * @return list of names and total number of characters in the names
     * @throws TechTestException if invalid postcode range is specified
     */
    @GetMapping(value = "/retrieveBetweenRange", produces = "application/json")
    public NamesDTO retrieveNamesBetweenRange(@RequestParam long startRange, @RequestParam long endRange) throws TechTestException {
        LOG.info("Invoked PersonController.retrieveNamesBetweenRange() at {}", new Date());
        return personService.retrieveNamesBetweenRange(startRange, endRange);
    }
}
