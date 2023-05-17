package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.erudio.controller.BookController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookVO> assembler;

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
        logger.info("Finding All Books!");

        var bookPage = repository.findAll(pageable);

        var bookVOsPage = bookPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
        bookVOsPage.map(
                p -> p.add(
                        linkTo(methodOn(BookController.class)
                                .findById(p.getKey())).withSelfRel()));

      
        Link link = linkTo(methodOn(BookController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(bookVOsPage, link);
    }

    public BookVO findById(Long id) {

        logger.info("Finding one Book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));

        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;

    }

    public BookVO create(BookVO book) {
        if (book == null)
            throw new RequiredObjectIsNullException();
        logger.info("Creating one book");

        var entity = DozerMapper.parseObject(book, Book.class);
        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {
        if (book == null)
            throw new RequiredObjectIsNullException();
        logger.info("Update one book");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));

        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());

        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id) {
        logger.info("Delete one book");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));

        repository.delete(entity);

    }

}
