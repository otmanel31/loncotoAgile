package com.otmanel.loncotoAgile.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.otmanel.loncotoAgile.metier.Intervention;
import com.otmanel.loncotoAgile.repositories.InterventionRepository;

@Service
public class PlannificatorService {
	
	@Autowired
	private InterventionRepository interventionDao;
	
	public void setInterventionDao(InterventionRepository i) {
		this.interventionDao = i;
	}
	
	// find one
	public Intervention getIntervention(int id){
		return this.interventionDao.findOne(id);
	}
	
	// find all
	public Page<Intervention> liste(Pageable page){
		return this.interventionDao.findAll(page);
	}
	
	//find by status
	public Page<Intervention> listeFilteredByStatus(Pageable page, String status){
		return this.interventionDao.findByStatusEquals(status, page);
	}
	
	// find by date intervention beginning
	public Page<Intervention> listeFilteredByDateIntervention(Pageable page, LocalDateTime date){
		return this.interventionDao.findByInterventionDateEquals(date, page);
	}
	
	// find by client
	public Page<Intervention> listeFilteredByClient(Pageable page, String client){
		return this.interventionDao.findByEquipmentClientNameEquals(client, page);
	}
	
	// find by site
	public Page<Intervention> listeFilteredBySite(Pageable page, String site){
		return this.interventionDao.findByEquipment_Salle_Etage_Batiment_Site_Name(site, page);
	}
	
	// create
	public Intervention plannifier(Intervention i) {
		if (i == null || i.getIntervenant() == null) throw new InterventionException("La mission ne peut etre NULL ou vous devez fournir un intervenant");
		if (i.getInterventionDate() == null ) throw new InterventionException("Une une date d'intervention doit etre présente");
		if (i.getInterventionDate() != null && i.getDateOfCompletion() != null)
			if (i.getDateOfCompletion().isBefore(i.getInterventionDate())) throw new InterventionException("Une fin d'intervention doit etre superieur a la date de debut");
		long duration = (i.getInterventionDate().until(i.getDateOfCompletion(), ChronoUnit.MINUTES));
		if (duration < 30 || duration > 240) throw new InterventionException("Une intervention ne peut etre superieur a 'h ou inferieur à min");
		
		// liste des conflits possible 
		List<Intervention> conflict = this.interventionDao.findByIntervenantIdAndInterventionDateAndDateOfCompletion(i.getIntervenant().getId(), i.getInterventionDate().minusDays(1), i.getDateOfCompletion().plusDays(1));
		for(Intervention ii : conflict) {
			if (ii.getDateOfCompletion().isBefore(i.getInterventionDate()) || ii.getInterventionDate().isAfter(i.getDateOfCompletion()))
				continue;
			else throw new IntervenantAvailableException("L'intervenant est indisponible sur ce créneau");
		}
		i = this.interventionDao.save(i);
		return i;
	}

	public static class InterventionException extends RuntimeException{

		public InterventionException(String message) {
			super(message);
		}
	}
	public static class IntervenantAvailableException extends RuntimeException{

		public IntervenantAvailableException(String message) {
			super(message);
		}
	}
}
