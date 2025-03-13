package com.Gabriel.Noel.tarea3AD2024base.repositorios.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.modelo.BackupCarnets;
@Repository
public interface BackupCarnetsRepository extends MongoRepository<BackupCarnets, String> {
}
