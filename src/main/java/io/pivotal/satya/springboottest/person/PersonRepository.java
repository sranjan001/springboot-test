package io.pivotal.satya.springboottest.person;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, String> {
    public Optional<Person> findByLastName(String lastName);
}
