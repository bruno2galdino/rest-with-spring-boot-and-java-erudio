package br.com.erudio.integrationtests.repositories;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.erudio.integrationtests.testcontainer.AbstractIntegrationTest;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest{
    @Autowired
    public PersonRepository repository;

    private static Person person;

    @BeforeAll
    public static void setup(){
        person = new Person();
    }

    @Test
    @Order(0)
    public void testeFindByName() throws JsonMappingException, JsonProcessingException {

        Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "firstName"));
        
        person = repository.findPersonsByName("ayr", pageable).getContent().get(0);

        assertEquals(111, person.getId());

        assertEquals("Sayres", person.getFirstName());
        assertEquals("Baine", person.getLastName());    
    }

    @Test
    @Order(2)
    public void testeDisablePerson() throws JsonMappingException, JsonProcessingException {

        repository.disabledPerson(person.getId());
        Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "firstName"));
        
        person = repository.findPersonsByName("ayr", pageable).getContent().get(0);

        assertFalse(person.getEnabled());
        assertEquals(111, person.getId());

        assertEquals("Sayres", person.getFirstName());
        assertEquals("Baine", person.getLastName());    
    }


}
