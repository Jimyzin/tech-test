package com.powerledger.techtest.repository;

import com.powerledger.techtest.entity.Person;
import com.powerledger.techtest.entity.id.PersonId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, PersonId> {

    /**
     * Finds Person records between startRange and endRange ordered by name
     * in ascending.
     *
     * @param startRange
     * @param endRange
     * @return List of Person records
     */
    public List<Person> findByPostcodeBetweenOrderByNameAsc(long startRange, long endRange);
}
