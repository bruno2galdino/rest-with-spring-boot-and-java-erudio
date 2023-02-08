package br.com.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
public class PersonServices {
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<Person> findAll(){
        logger.info("Finding All People!");
        List<Person> persons = new ArrayList<>();
        for(int i = 0 ; i < 8; i++){
            Person person = mockPerson(i);
            persons.add(person);

        }
        return persons;
    }

    public Person findById(String id){

        logger.info("Finding one Person!");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Bruno");
        person.setLastName("Galdino");
        person.setAddress("Tatui-SP");
        person.setGender("Male");
        return person;
    }

    public Person create(Person person){
        logger.info("Creating one person");

        return person;
    }

    public Person update(Person person){
        logger.info("Update one person");

        return person;
    }

    public void delete(String id){
        logger.info("Delete one person");

    }

    public Person mockPerson(int i){
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person Name " + i);
        person.setLastName("Last Name " + i);
        person.setAddress("Some address in Brasil " + i);
        person.setGender((i%2 == 0)?"Male":"Female");
        return person;
    }

}
