package com.simple.repository;

import com.simple.models.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<Usuario, Long>{

    Usuario findByUsuario(String usuario);

}
