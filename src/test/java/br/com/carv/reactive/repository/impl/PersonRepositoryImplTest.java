package br.com.carv.reactive.repository.impl;

import br.com.carv.reactive.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    private PersonRepositoryImpl personRepository;

    @BeforeEach
    void setUp() {
        this.personRepository = new PersonRepositoryImpl();
    }

    //Tests before refactoring PersonRepository

    @Test
    void getByIdBlock() {
        Mono<Person> personMono = personRepository.getById(1L);
        System.out.println("Person Mono: " + personMono.toString());
        Person person = personMono.block();
        System.out.println("Person: " + person.toString());
    }

    @Test
    void getByIdSubscriber() {
        Mono<Person> personMono = personRepository.getById(1L);
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void getByIdMapFunction() {
        Mono<Person> personMono = personRepository.getById(1L);
        personMono.map(person -> {
            System.out.println(person.toString());
            return person.getFirstName();
        }).subscribe(firstName -> {
            System.out.println("From Map: " + firstName);
        });
    }

    @Test
    void fluxTestBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();
        Mono<List<Person>> listMono = personFlux.collectList();
        List<Person> persons = listMono.block();
        System.out.println(persons);
    }

    @Test
    void fluxTestSubscriber() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.subscribe(person -> {
            System.out.println(person);
        });
    }

    @Test
    void testFindPersonById(){
        final Long id = 2L;
        Flux<Person> personFlux = personRepository.findAll();
        Mono<Person> person = personFlux.filter(person1 -> person1.getId().equals(id)).next();
        person.subscribe(p -> System.out.println(p));
    }

    @Test
    void testFindPersonByName(){
        final String param = "Lais Mansano";
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.filter(person -> person.getFirstName().equals(param)).next().subscribe(System.out::println);

    }

    @Test
    void testFindPersonByNameAnotherWay(){
        final String param = "Lais";
        Flux<Person> personFlux = personRepository.findAll();
        Mono<Person> next = personFlux.filter(person -> person.getFirstName().equals(param)).next();
        next.subscribe(p -> System.out.println(p));
    }

    @Test
    void testFindPersonIdNotFoundWithException() {
        Flux<Person> personFlux = personRepository.findAll();
        final Long id = 10L;
        Mono<Person> personMono = personFlux.filter(person -> person.getId().equals(id)).single();
        personMono.doOnError(throwable -> { System.out.println("I went boom"); }).onErrorReturn(new Person()).subscribe(System.out::println);
    }

    @Test
    void testFindByIdRefactor() {
        final Long id = 1L;
        Mono<Person> personMono = personRepository.getByIdRefactor(id);
        personMono.subscribe(System.out::println);
    }

    @Test
    void testFindAllRefactor() {
        Flux<Person> fluxList = personRepository.findAllRefactor();
        fluxList.subscribe(System.out::println);
    }
}