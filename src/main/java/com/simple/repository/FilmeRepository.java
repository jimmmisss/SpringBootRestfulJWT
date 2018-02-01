package com.simple.repository;

import com.simple.models.Filme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FilmeRepository extends PagingAndSortingRepository<Filme, Long> {

    Filme findById(long id);

    List<Filme> findByNomeIgnoreCaseContaining(String nome);

    @Query(value = "SELECT f.*, count(a) total FROM filmes f LEFT JOIN atores a ON f.id = a.filme_id group by f.id, a.filme_id ORDER BY f.nome ASC", nativeQuery = true)
    Iterable<Filme> findFilmeByContemAtor();

}