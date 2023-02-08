package br.com.erudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import br.com.erudio.services.PersonServices;
import br.com.erudio.model.Person;


@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonServices service;

	@RequestMapping(method=RequestMethod.POST ,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person){
		return service.create(person);
	}

	@RequestMapping(method=RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public Person uptdate(@RequestBody Person person) {
		return service.update(person);
	}

	@RequestMapping(value = "/{id}", 
		method=RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findById(@PathVariable(value = "id") String id) {
		return service.findById(id);
	}

	@RequestMapping(value = "/{id}", 
		method=RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") String id) {
		service.delete(id);
	}

	@RequestMapping(
		method=RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findByAll(){
		return service.findAll();
	}

}
