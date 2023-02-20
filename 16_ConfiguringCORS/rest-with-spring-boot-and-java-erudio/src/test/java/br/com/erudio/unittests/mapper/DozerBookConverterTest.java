package br.com.erudio.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.unittests.mapper.mocks.MockBook;

public class DozerBookConverterTest {
    
    MockBook inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockBook();
    }

    @Test
    public void parseEntityToVOTest() throws ParseException {
        BookVO output = DozerMapper.parseObject(inputObject.mockEntity(), BookVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("Author0", output.getAuthor());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(formatter.parse("19/07/2020"), output.getLaunchDate());
        assertEquals(0.0, output.getPrice());
        assertEquals("Title Book0", output.getTitle());
    }

    @Test
    public void parseEntityListToVOListTest() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        List<BookVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), BookVO.class);
        BookVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("Author0", outputZero.getAuthor());
        assertEquals(formatter.parse("19/07/2020"), outputZero.getLaunchDate());
        assertEquals(0.0, outputZero.getPrice());
        assertEquals("Title Book0", outputZero.getTitle());
        
        BookVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("Author7", outputSeven.getAuthor());
        assertEquals(formatter.parse("19/07/2020"), outputSeven.getLaunchDate());
        assertEquals(7.0, outputSeven.getPrice());
        assertEquals("Title Book7", outputSeven.getTitle());
        
        BookVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("Author12", outputTwelve.getAuthor());
        assertEquals(formatter.parse("19/07/2020"), outputTwelve.getLaunchDate());
        assertEquals(12.0, outputTwelve.getPrice());
        assertEquals("Title Book12", outputTwelve.getTitle());
    }

    @Test
    public void parseVOToEntityTest() throws ParseException {
        Book output = DozerMapper.parseObject(inputObject.mockVO(), Book.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Author0", output.getAuthor());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(formatter.parse("19/07/2020"), output.getLaunchDate());
        assertEquals(0.0, output.getPrice());
        assertEquals("Title Book0", output.getTitle());
    }

    @Test
    public void parserVOListToEntityListTest() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        List<Book> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Book.class);

        Book outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Author0", outputZero.getAuthor());
        assertEquals(formatter.parse("19/07/2020"), outputZero.getLaunchDate());
        assertEquals(0.0, outputZero.getPrice());
        assertEquals("Title Book0", outputZero.getTitle());
        
        Book outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Author7", outputSeven.getAuthor());
        
        assertEquals(formatter.parse("19/07/2020"),outputSeven.getLaunchDate());
        assertEquals(7.0, outputSeven.getPrice());
        assertEquals("Title Book7", outputSeven.getTitle());
        
        Book outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Author12", outputTwelve.getAuthor());
        assertEquals(formatter.parse("19/07/2020"), outputTwelve.getLaunchDate());
        assertEquals(12.0, outputTwelve.getPrice());
        assertEquals("Title Book12", outputTwelve.getTitle());
    }
}
