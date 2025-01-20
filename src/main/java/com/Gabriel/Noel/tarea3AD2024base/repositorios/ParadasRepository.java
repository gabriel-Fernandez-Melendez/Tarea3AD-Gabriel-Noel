package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;

@Repository
public interface ParadasRepository extends JpaRepository<Parada, Long> {

//    // Buscar paradas por región
//    List<Parada> findByRegion(char region);
//    
//    //Buscar parada por nombre
//    Parada findByNombreIgnoreCase (String nombre);
//
//    // Buscar paradas asociadas a un responsable
//    Parada findByIdUsuario(Long idResponsable);
//    
//    // Busca parada por id de parada
//    //Parada findByIdParada(Long idParada);
//    
//    // Busca Parada asociadas al peregrino
//    //Parada findByIdPeregrino(Long idPeregrino);
}
