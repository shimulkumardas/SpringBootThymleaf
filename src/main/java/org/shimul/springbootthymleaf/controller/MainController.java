package org.shimul.springbootthymleaf.controller;

import org.shimul.springbootthymleaf.form.PersonForm;
import org.shimul.springbootthymleaf.model.Person;
import java.awt.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	private static ArrayList<Person> persons=new ArrayList<Person>();
	
	static {
		persons.add(new Person("Bill","Gates"));
		persons.add(new Person("Steve","jobs"));
	}
	//inject via application.properties
	@Value("${welcome.message}")
	private String message;
	
	@Value("${error.message}")
	private String errorMessage;
	@RequestMapping(value= {"/personList"},method=RequestMethod.GET)
	public String personList(Model model) {
		model.addAttribute("persons", persons);
		return "personList";
		}
	@RequestMapping(value= {"/addPerson"},method=RequestMethod.GET)
	public String showAddPersonPage(Model model) {
		PersonForm personForm=new PersonForm();
		model.addAttribute("PersonForm", personForm);
		return "personForm";
	}
	@RequestMapping(value= {"/addPerson"},method=RequestMethod.POST)
	public String savePerson(Model model,//
			@ModelAttribute("personForm")PersonForm personForm) {
		String firstName=personForm.getFirstName();
		String lastName=personForm.getLastName();
		if(firstName!=null && firstName.length()>0//
				&&lastName!=null && lastName.length()>0) {
			Person newPerson=new Person(firstName,lastName);
			persons.add(newPerson);
			return"redirect:/PersonList";
		}
		model.addAttribute("errorMessage", errorMessage);
		return "addPerson";
		
	}
}