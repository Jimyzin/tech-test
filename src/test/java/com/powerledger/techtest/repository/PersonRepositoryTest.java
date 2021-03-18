package com.powerledger.techtest.repository;


import com.powerledger.techtest.TechTestApplication;
import com.powerledger.techtest.entity.Person;
import com.powerledger.techtest.utils.TestDataUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This test class is executed in a TEST H2 database.
 * This database is different from the one used in the main application.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechTestApplication.class)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @After
    public void clearTable() {
        personRepository.deleteAll();
    }

    /**
     * Given: PersonRepository
     * When: saving a valid entity
     * Then: entity is successfully saved in H2 database.
     */
    @Test
    public void givenPersonRepository_whenSaveEntity_thenOK() {
        // test data preparation
        Person johnDoe = new Person();
        johnDoe.setName("John Doe");
        johnDoe.setPostcode(6000);

        // executing the test
        personRepository.save(johnDoe);

        // assertions
        personRepository.findAll().forEach(person -> {
            Assert.assertEquals(johnDoe.getName(), person.getName());
            Assert.assertEquals(johnDoe.getPostcode(), person.getPostcode());
        });
    }

    /**
     * Given: PersonRepository
     * When: saving an invalid entity
     * Then: throw TransactionSystemException (due to ConstraintValidationException)
     */
    @Test(expected = TransactionSystemException.class)
    public void givenPersonRepository_whenSaveInvalidEntity_thenException() {
        // test data preparation
        Person johnDoe = new Person();
        johnDoe.setName(null); // invalid name
        johnDoe.setPostcode(0); // invalid postcode

        // executing the test
        personRepository.save(johnDoe);

    }

    /**
     * Given: PersonRepository
     * When: finding entities by a postcode range
     * Then: return entities list sorted alphabetically by names
     */
    @Test
    public void givenPersonRepository_whenFindByPostcodeBetweenOrderByNameAsc_thenOK() {
        // test data preparation
        List<Person> persons = TestDataUtils.preparePersonList();
        personRepository.saveAll(persons);
        String[] expectedOrderedNames = TestDataUtils.prepareOrderedNames();

        // executing the test
        List<Person> retrievedPersons = personRepository.findByPostcodeBetweenOrderByNameAsc(6000, 6002);

        // assertions
        Assert.assertEquals(5, retrievedPersons.size());

        AtomicInteger index = new AtomicInteger(0);

        retrievedPersons.forEach(person -> {
            Assert.assertEquals(expectedOrderedNames[index.getAndAdd(1)], person.getName());
        });
    }

}
