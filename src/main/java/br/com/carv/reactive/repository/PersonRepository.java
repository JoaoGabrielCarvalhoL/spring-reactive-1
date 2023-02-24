package br.com.carv.reactive.repository;

import br.com.carv.reactive.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

    Mono<Person> getById(Long id); //0 or 1

    Flux<Person> findAll(); //0 or N

    Mono<Person> getByIdRefactor(Long id);

    Flux<Person> findAllRefactor();

}
