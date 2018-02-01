package com.simple.repository;

import com.simple.models.Ator;
import org.springframework.data.repository.CrudRepository;

public interface AtorRepository extends CrudRepository<Ator, Long>{

    Ator findById(long id);

}