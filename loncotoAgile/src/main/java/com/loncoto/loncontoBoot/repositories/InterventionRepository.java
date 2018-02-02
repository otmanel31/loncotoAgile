package com.loncoto.loncontoBoot.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.loncoto.loncontoBoot.metier.Intervention;

public interface InterventionRepository extends PagingAndSortingRepository<Intervention, Integer> {

}
