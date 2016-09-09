package com.curso.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.brewer.model.Estilo;

@Repository
public interface Estilos extends JpaRepository<Estilo, Long> {

}
