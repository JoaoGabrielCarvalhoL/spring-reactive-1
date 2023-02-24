package br.com.carv.reactive.repository.impl;

import br.com.carv.reactive.domain.Person;
import br.com.carv.reactive.repository.PersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    private List<Person> persons;

    public void populate() {
        Person person = new Person(1L, "João Gabriel", "Carvalho",
                "27.joaogabriel@gmail.com");
        Person person2 = new Person(2L, "Lais Mansano", "Alexandre",
                "lais2@gmail.com");

        Person person3 = new Person(3L, "Test", "Number One",
                "test@gmail.com");

        Person person4 = new Person(4L, "Test", "Number Two",
                "testtwo@gmail.com");

        Person person5 = new Person(5L, "Test", "Number Two",
                "testtwo@gmail.com");

        this.persons.add(person);
        this.persons.add(person2);
        this.persons.add(person3);
        this.persons.add(person4);
        this.persons.add(person5);
    }

    public PersonRepositoryImpl() {
        this.persons = new ArrayList<Person>();
        populate();
    }

    public List<Person> getPersons() {
        return persons;
    }


    @Override
    public Mono<Person> getById(Long id) {
        return Mono.just(getPersons().get(0));
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(getPersons().get(0), getPersons().get(1), getPersons().get(2), getPersons().get(3), getPersons().get(4));
    }

    @Override
    public Mono<Person> getByIdRefactor(Long id) {
        Person result = getPersons().stream().filter(person -> person.getId().equals(id)).findFirst().get();
        return Mono.just(result);
    }

    @Override
    public Flux<Person> findAllRefactor() {
        return Flux.fromIterable(getPersons());
    }
}
