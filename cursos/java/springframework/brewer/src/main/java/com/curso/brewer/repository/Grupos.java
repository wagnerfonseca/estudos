package com.curso.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.brewer.model.Grupo;

@Repository
public interface Grupos extends JpaRepository<Grupo, Long> {

}
