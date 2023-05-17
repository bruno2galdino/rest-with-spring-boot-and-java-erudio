package br.com.erudio.unittests.mapper.mocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.model.Book;

public class MockBook {


    public Book mockEntity() throws ParseException {
        return mockEntity(0);
    }
    
    public BookVO mockVO() throws ParseException {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() throws ParseException {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() throws ParseException {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
            System.out.println("Numeto mockEntity Gerado"+i);
        }
        return books;
    }
    
    public Book mockEntity(Integer number) throws ParseException {
        Book book = new Book();
        book.setTitle("Title Book" + number);
        book.setAuthor("Author" + number);
        book.setId(number.longValue());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        book.setLaunchDate(formatter.parse("19/07/2020"));
        book.setPrice(number.floatValue());
        
        return book;
    }

    public BookVO mockVO(Integer number) throws ParseException {
        BookVO book = new BookVO();
        book.setTitle("Title Book" + number);
        book.setAuthor("Author" + number);
        book.setKey(number.longValue());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        book.setLaunchDate(formatter.parse("19/07/2020"));
        book.setPrice(number.floatValue());
        return book;
    }

}
