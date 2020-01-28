package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();
    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    //And when we go to /Person, this is the method that is run, which is why we get the whole database.
    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    //Alternatively, if we add a UUID to the /Person part, like /person/sdfjia4-23s,
    //then we would hit this part, which filters all the people by the id provided
    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id, Person person) {
        return selectPersonById(id)
                .map(p-> {
                    int indexOfPersonToDelete = DB.indexOf(person);
                    if (indexOfPersonToDelete >= 0) {
                        DB.set(indexOfPersonToDelete, person);
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    } {
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return 0;
    }
}
