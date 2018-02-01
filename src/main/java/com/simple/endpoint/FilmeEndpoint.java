package com.simple.endpoint;

import com.simple.error.ResourceNotFoundException;
import com.simple.models.Ator;
import com.simple.models.Filme;
import com.simple.repository.AtorRepository;
import com.simple.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1")
public class FilmeEndpoint {

    private final FilmeRepository rf;
    private final AtorRepository ar;

    @Autowired
    public FilmeEndpoint(FilmeRepository rf, AtorRepository ar) {
        this.rf = rf;
        this.ar = ar;
    }

    @GetMapping(path = "protected/filme")
    public ResponseEntity<?> listaFilmes(Pageable pageable){
        return new ResponseEntity<>(rf.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/filme/{id}")
    public ResponseEntity<?> sobreFilme(@PathVariable("id") long id, Authentication authentication) {
        System.out.printf(String.valueOf(authentication));
        verifyIfFilmeExists(id);
        Filme filme = rf.findOne(id);
        return new ResponseEntity<>(filme, HttpStatus.OK);
    }

    @GetMapping(path = "protected/filme/buscafilme/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome){
        return new ResponseEntity<>(rf.findByNomeIgnoreCaseContaining(nome), HttpStatus.OK);
    }

    @PostMapping(path = "admin/filme")
    public ResponseEntity<?> addFilme(@Valid @RequestBody Filme filme) {
        return new ResponseEntity<>(rf.save(filme), HttpStatus.CREATED);
    }

    @PostMapping(path = "admin/addAtorFilme/{id}")
    public ResponseEntity<?> addAtorFilme(@PathVariable long id, @Valid @RequestBody Ator ator) {
        verifyIfFilmeExists(id);
        Filme filme = rf.findById(id);
        filme.addAtor(ator);
        return new ResponseEntity<>(rf.save(filme), HttpStatus.CREATED);
    }

    @PutMapping(path = "admin/filme")
    public ResponseEntity<?> upFilme(@RequestBody Filme filme){
        verifyIfFilmeExists(filme.getId());
        rf.save(filme);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "admin/filme/{id}")
    public ResponseEntity<?> delFilme(@PathVariable long id) {
        verifyIfFilmeExists(id);
        rf.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "admin/ator/{id}")
    public ResponseEntity<?> delAtor(@PathVariable long id) {
        verifyIfAtorExists(id);
        ar.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfFilmeExists(long id) {
        if(rf.findOne(id) == null)
            throw new ResourceNotFoundException("Student not found for ID: "+ id);
    }

    private void verifyIfAtorExists(long id){
        if(ar.findOne(id) == null)
            throw new ResourceNotFoundException("Actor not found for ID:" + id);
    }

}