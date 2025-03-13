package com.Gabriel.Noel.tarea3AD2024base.repositorios.mongo;


import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarnetMongoRepository extends MongoRepository<Carnet, String> {
}