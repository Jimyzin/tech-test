package com.powerledger.techtest.service;

import com.powerledger.techtest.dto.NamesDTO;
import com.powerledger.techtest.dto.PersonDTO;
import com.powerledger.techtest.entity.Person;
import com.powerledger.techtest.exception.TechTestException;
import com.powerledger.techtest.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Saves the provided list of PersonDTO(name, postcode) in H2 database.
     *
     * @param persons list of PersonDTO(name, postcode)
     * @return inserted list of PersonDTO(name, postcode)
     */
    public List<Person> createAll(List<PersonDTO> persons) {
        LOG.info("Invoked PersonService.createAll() at {}", new Date());
        return persons.stream()
                .filter(person -> person != null) // removes null items from the input list
                .map(person -> {

                    // using personRepository.save() instead of personRepository.saveAll() because saveAll() uses
                    // a for-each loop internally. So, this avoids iterating over the same list again in saveAll().
                    return personRepository.save(person.toPersonEntity());

                }).collect(Collectors.toList());
    }

    /**
     * Retrieves names of persons corresponding to the entered range of postcodes.
     * Then, the names are arranged alphabetically. The output also contains the
     * sum total of characters in all the output names combined.
     *
     * @param startRange starting postcode range
     * @param endRange ending postcode range
     * @return list of names and total number of characters in the names
     * @throws TechTestException if invalid postcode range is provided
     */
    public NamesDTO retrieveNamesBetweenRange(long startRange, long endRange) throws TechTestException {
        LOG.info("Invoked PersonService.retrieveNamesBetweenRange() at {}", new Date());

        if (startRange <= 0 || endRange <= 0) {
            throw new TechTestException("Start range or end range must not be zero or negative");
        }

        if (endRange < startRange) {
            throw new TechTestException("End range is lesser than start range");
        }

        AtomicInteger totalNumberOfCharacters = new AtomicInteger(0);
        List<String> names = personRepository.findByPostcodeBetweenOrderByNameAsc(startRange, endRange)
                .stream().map(person -> {

                    totalNumberOfCharacters.getAndAdd(
                            person.getName()
                                    .replaceAll("\\s+", "") // removes all whitespaces before adding totalNumberOfCharacters
                                    .length());
                    return person.getName();
                })
                .collect(Collectors.toList());

        return new NamesDTO(totalNumberOfCharacters.get(), names);
    }
}
