package com.otmanel.loncotoAgile.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.otmanel.loncotoAgile.metier.Intervention;

public interface InterventionRepository extends PagingAndSortingRepository<Intervention, Integer> {
	// intervention par site
		Page<Intervention> findByEquipment_Salle_Etage_Batiment_Site_Name(String name, Pageable p);
		/*@Query("select i from Intervention as i ")
		Page<Intervention> findAllBySite(@Param("name") String name);*/
		// intervention par status
		Page<Intervention> findByStatusEquals(String status, Pageable page);
		// intervention par client
		Page<Intervention> findByEquipmentClientNameEquals(String name, Pageable p);
		// intervention par date de fin
		Page<Intervention> findByDateOfCompletionEquals(LocalDateTime dateOfCompletion, Pageable p);
		// intervention par intervenant
		Page<Intervention> findByIntervenantFirstnameEquals(String firstname, Pageable p);
		// intervention par date de dedbut
		Page<Intervention> findByInterventionDateEquals(LocalDateTime interventionDate, Pageable p);
		
		// on chercher a +1/-1 jours de la mission qu on souhaite programmer pour ne pas 
		// se retoruver avc un traitement lourd + filtre approfondi par programmation dans le service
		List<Intervention> findByIntervenantIdAndInterventionDateAndDateOfCompletion(int id, LocalDateTime start, LocalDateTime end);
}
