package br.com.erudio.unittests.mockito.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import br.com.erudio.services.BookServices;
import br.com.erudio.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServicesTest {

    MockBook input;

    @InjectMocks
    private BookServices service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws ParseException{
        Book entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertNotNull(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author1", result.getAuthor());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(formatter.parse("19/07/2020"), result.getLaunchDate());
        assertEquals(1,00, result.getPrice());
        assertEquals("Title Book1", result.getTitle());
    }
    
        
    @Test
    void testFindAll() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        List<Book> list = input.mockEntityList();
       
        when(repository.findAll()).thenReturn(list);

        var people = service.findAll();
        assertNotNull(people);
        assertEquals(14, people.size());

        var bookOne = people.get(1);
        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());
        assertNotNull(bookOne.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author1", bookOne.getAuthor());
        assertEquals(formatter.parse("19/07/2020"), bookOne.getLaunchDate());
        assertEquals(1.0, bookOne.getPrice());
        assertEquals("Title Book1", bookOne.getTitle());

        var bookFour = people.get(4);
        assertNotNull(bookFour);
        assertNotNull(bookFour.getKey());
        assertNotNull(bookFour.getLinks());
        assertNotNull(bookFour.toString().contains("links: [</book/v1/4>;rel=\"self\"]"));
        assertEquals("Author4", bookFour.getAuthor());
        assertEquals(formatter.parse("19/07/2020"), bookFour.getLaunchDate());
        assertEquals(4.0, bookFour.getPrice());
        assertEquals("Title Book4", bookFour.getTitle());
        
        
    }

    @Test
    void testCreate() throws ParseException{
        Book entity = input.mockEntity(1);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author1", result.getAuthor());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(formatter.parse("19/07/2020"), result.getLaunchDate());
        assertEquals(1.0, result.getPrice());
        assertEquals("Title Book1", result.getTitle());
    }

    @Test
    void testCreateWithNullBook(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullBook(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
        

    @Test
    void testUpdate() throws ParseException{
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author1", result.getAuthor());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(formatter.parse("19/07/2020"), result.getLaunchDate());
        assertEquals(1.0, result.getPrice());
        assertEquals("Title Book1", result.getTitle());
        
    }

    @Test
    void testDelete() throws ParseException{
        Book entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }
}
    