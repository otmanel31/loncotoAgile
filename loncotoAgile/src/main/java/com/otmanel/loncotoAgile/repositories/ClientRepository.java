package com.otmanel.loncotoAgile.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.otmanel.loncotoAgile.metier.Client;

public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {

}
