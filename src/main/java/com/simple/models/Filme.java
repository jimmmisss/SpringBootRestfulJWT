package com.simple.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "filmes")
public class Filme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String nome;
    private Integer ano;
    private String classificacao;
    private String genero;
    private String data;

    //ENTITY GRAPHS
    @JsonBackReference
    @OneToMany(mappedBy = "filme", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Ator> atores;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Ator> getAtores() {
        return atores;
    }

    public void setAtores(List<Ator> atores) {
        this.atores = atores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filme)) return false;
        Filme filme = (Filme) o;
        return getId() == filme.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    public void addAtor(Ator ator) {
        if (atores == null) {
            atores = new ArrayList<Ator>();
        }
        ator.setFilme(this);
        atores.add(ator);
    }

    public void addAtores(List<Ator> atores) {
        for (Ator ator : atores) {
            addAtor(ator);
        }
    }

}
