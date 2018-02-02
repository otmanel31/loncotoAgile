package com.otmanel.loncotoAgile.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.otmanel.loncotoAgile.metier.Group;

public interface GroupRepository extends CrudRepository<Group, Integer>{

}
