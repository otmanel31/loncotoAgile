package com.otmanel.loncotoAgile.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import com.otmanel.loncotoAgile.metier.Intervention;
import com.otmanel.loncotoAgile.repositories.InterventionRepository;
import com.otmanel.loncotoAgile.services.PlannificatorService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value="/extended_api/interventions")
@Log4j
public class InterventionController {
	
//	@Autowired
//	private InterventionRepository interventionRepo;
	
	@Autowired
	private PlannificatorService planifService;
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/pliste", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Intervention> findAll(@PageableDefault(page=0, size=4) Pageable page,
			@RequestParam("status") Optional<String> status,
			@RequestParam("site") Optional<String> site,
			@RequestParam("client") Optional<String> client,
			@RequestParam("date") Optional<String> date)
	{
		this.log.info("status intervention: " + status.orElse("no status"));
		this.log.info("site intervention: " + site.orElse("no site"));
		this.log.info("client intervention: " + client.orElse("no clients"));
		this.log.info("date intervention: " + status.orElse("no date"));
		
		if (status.isPresent()) return this.planifService.listeFilteredByStatus(page, status.get());
		if (site.isPresent()) return this.planifService.listeFilteredBySite(page, site.get());
		if (client.isPresent()) return this.planifService.listeFilteredByClient(page, client.get());
		if (date.isPresent()) return this.planifService.listeFilteredByDateIntervention(page, LocalDateTime.parse(date.get()));
		
		return this.planifService.liste(page);
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/create", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Intervention create(@RequestBody Intervention i) {
		this.log.info("in create ********************************");
		return this.planifService.plannifier(i);
	}
	
	/* OLD VERSION with planificator service
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/pliste", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Intervention> findAll(@PageableDefault(page=0, size=4) Pageable p,
			@RequestParam("status") Optional<String> status,
			@RequestParam("site") Optional<String> site,
			@RequestParam("client") Optional<String> client,
			@RequestParam("date") Optional<String> date)
	{
		this.log.info("status intervention: " + status.orElse("no status"));
		this.log.info("site intervention: " + site.orElse("no site"));
		this.log.info("client intervention: " + client.orElse("no clients"));
		this.log.info("date intervention: " + status.orElse("no date"));
		
		if (status.isPresent()) return this.interventionRepo.findByStatusEquals(status.get(), p);
		// tbd if (site.isPresent()) return this.interventionRepo.
		if (client.isPresent()) return this.interventionRepo.findByEquipmentClientNameEquals(client.get(), p);
		if (date.isPresent()) return this.interventionRepo.findByInterventionDateEquals(LocalDateTime.parse(date.get()), p);
		
		return this.interventionRepo.findAll(p);
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/intervention/{id:[0-9].+}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Intervention getOne(@PathVariable("id") int id) {
		return this.interventionRepo.findOne(id);
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/create", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Intervention save(@RequestBody Intervention i) {
		return this.interventionRepo.save(i);
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/update", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Intervention update(@RequestBody Intervention i) {
		return this.interventionRepo.save(i);
	}
	
	public String delete(@PathVariable("id") int id){
		try {
			this.interventionRepo.delete(id);
			return "success";
		} catch (Exception e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "sa pue");
		}
	}
	*/
}
