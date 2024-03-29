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

import br.com.erudio.controller.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;

    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {

        logger.info("Finding All People!");

        var personPage = repository.findAll(pageable);

        var personVOsPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
        personVOsPage.map(
                p -> p.add(
                        linkTo(methodOn(PersonController.class)
                                .findById(p.getKey())).withSelfRel()));

      
        Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(personVOsPage, link);

    }

    public PagedModel<EntityModel<PersonVO>> findPersonByName(String firstname, Pageable pageable) {

        logger.info("Finding People by Name!");

        var personPage = repository.findPersonsByName(firstname, pageable);

        var personVOsPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
        personVOsPage.map(
                p -> p.add(
                        linkTo(methodOn(PersonController.class)
                                .findById(p.getKey())).withSelfRel()));

      
        Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(personVOsPage, link);

    }

    public PersonVO findById(Long id) {

        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));

        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;

    }

    public PersonVO create(PersonVO person) {
        if (person == null)
            throw new RequiredObjectIsNullException();
        logger.info("Creating one person");

        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person) {
        if (person == null)
            throw new RequiredObjectIsNullException();
        logger.info("Update one person");

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    @Transactional
    public PersonVO disabledPerson(Long id) {

        logger.info("Disabling one Person!");

        repository.disabledPerson(id);

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));

        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;

    }

    public void delete(Long id) {
        logger.info("Delete one person");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));

        repository.delete(entity);

    }
}
