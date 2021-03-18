package com.powerledger.techtest.service;

import com.powerledger.techtest.TechTestApplication;
import com.powerledger.techtest.dto.NamesDTO;
import com.powerledger.techtest.dto.PersonDTO;
import com.powerledger.techtest.entity.Person;
import com.powerledger.techtest.entity.id.PersonId;
import com.powerledger.techtest.exception.TechTestException;
import com.powerledger.techtest.repository.PersonRepository;
import com.powerledger.techtest.utils.TestDataUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.powerledger.techtest.utils.TestDataUtils.prepareMapData;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechTestApplication.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @After
    public void clearTable() {
        personRepository.deleteAll();
    }

    /**
     * Given: PersonService
     * When: saving a valid list of PersonDTO (containing names and corresponding postcodes)
     * Then: persist data in test H2 database
     */
    @Test
    public void givenPersonService_whenCreateAll_thenOK() {
        // prepare test data
        Map<String, PersonDTO> inputMap = prepareMapData();

        // execute the test and assert
        personService.createAll((List<PersonDTO>) new ArrayList(inputMap.values())).stream().forEach(person -> {

            personRepository.findById(new PersonId(person.getName(), person.getPostcode())).ifPresent(personEntity -> {
                        PersonDTO expectedValue = inputMap.get(personEntity.getName());
                        Assert.assertEquals(expectedValue.getName(), personEntity.getName());
                        Assert.assertEquals(expectedValue.getPostcode(), personEntity.getPostcode());
                    }
            );
        });

    }

    /**
     * Given: PersonService
     * When: saving an empty list of PersonDTO
     * Then: the list is not persisted and an empty list is returned
     */
    @Test
    public void givenPersonService_whenCreateAll_with_empty_list_then_empty_list_is_returned() {

        // execute the test
        Assert.assertEquals(0, personService.createAll((List<PersonDTO>) new ArrayList()).size());

    }

    /**
     * Given: PersonService
     * When: saving a list of invalid PersonDTO
     * Then: throw TransactionSystemException triggered by ConstrainViolationException
     */
    @Test(expected = TransactionSystemException.class)
    public void givenPersonService_whenCreateAll_with_invalid_input_thenException() {

        // execute the test
        personService.createAll(Arrays.asList(new PersonDTO(null, 0)));

    }

    /**
     * Given: PersonService
     * When: retrieving names within a valid range of postcodes
     * Then: successfully return NamesDTO(list of names, totalNumberOfCharacters)
     */
    @Test
    public void givenPersonService_whenRetrieveNamesBetweenRange_thenOK() {
        // prepare test data
        List<Person> persons = TestDataUtils.preparePersonList();
        personRepository.saveAll(persons);
        String[] expectedOrderedNames = TestDataUtils.prepareOrderedNames();
        int expectedTotalNumberOfCharacters = 58; // = total number of characters - whitespaces

        try {
            // executing the test case
            NamesDTO namesDTO = personService.retrieveNamesBetweenRange(6000, 6002);

            // assertions
            AtomicInteger index = new AtomicInteger(0);
            namesDTO.getNames().forEach(name -> {
                Assert.assertEquals(expectedOrderedNames[index.getAndAdd(1)], name);
            });
            Assert.assertEquals(expectedTotalNumberOfCharacters, namesDTO.getTotalNumberOfCharacters());

        } catch (Exception e) {
            Assert.fail();
        }
    }

    /**
     * Given: PersonService
     * When: retrieving names between a non-existent range in the database
     * Then: return zero records
     */
    @Test
    public void givenPersonService_whenRetrieveNamesBetweenRange_returning_zero_records_thenOK() {
        // prepare test data
        List<Person> persons = TestDataUtils.preparePersonList();
        personRepository.saveAll(persons);
        String[] expectedOrderedNames = TestDataUtils.prepareOrderedNames();
        int expectedTotalNumberOfCharacters = 0;

        try {
            // executing the test case
            NamesDTO namesDTO = personService.retrieveNamesBetweenRange(60000, 60020);

            // assertions
            AtomicInteger index = new AtomicInteger(0);
            namesDTO.getNames().forEach(name -> {
                Assert.assertEquals(expectedOrderedNames[index.getAndAdd(1)], name);
            });
            Assert.assertEquals(expectedTotalNumberOfCharacters, namesDTO.getTotalNumberOfCharacters());

        } catch (Exception e) {
            Assert.fail();
        }
    }

    /**
     * Given: PersonService
     * When: the startRange is greater than endRange
     * Then: throw TechTestException
     * @throws TechTestException
     */
    @Test(expected = TechTestException.class)
    public void givenPersonService_whenRetrieveNamesBetweenRange_startRange_is_greater_than_endRange_thenException() throws TechTestException {

        // executing the test case
        personService.retrieveNamesBetweenRange(62000, 6002);
    }

    /**
     * Given: PersonService
     * When: startRange is zero
     * Then: throw TechTestException
     * @throws TechTestException
     */
    @Test(expected = TechTestException.class)
    public void givenPersonService_whenRetrieveNamesBetweenRange_startRange_is_zero_thenException() throws TechTestException {

        // executing the test case
        personService.retrieveNamesBetweenRange(0, 6002);
    }

    /**
     * Given: PersonService
     * When: endRange is zero
     * Then: throw TechTestException
     * @throws TechTestException
     */
    @Test(expected = TechTestException.class)
    public void givenPersonService_whenRetrieveNamesBetweenRange_endRange_is_zero_thenException() throws TechTestException {

        // executing the test case
        personService.retrieveNamesBetweenRange(0, 0);
    }

    /**
     * Given: PersonService
     * When: startRange and endRange are negative
     * Then: throw TechTestException
     * @throws TechTestException
     */
    @Test(expected = TechTestException.class)
    public void givenPersonService_whenRetrieveNamesBetweenRange_startRange_and_endRange_are_negative_thenException() throws TechTestException {

        // executing the test case
        personService.retrieveNamesBetweenRange(-20, -10);
    }

}
