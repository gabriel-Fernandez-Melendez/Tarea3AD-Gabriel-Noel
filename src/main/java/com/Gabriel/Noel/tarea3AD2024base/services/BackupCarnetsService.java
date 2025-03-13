package com.Gabriel.Noel.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.BackupCarnets;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa.CarnetRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.mongo.BackupCarnetsRepository;

import java.util.Date;
import java.util.List;

@Service

public class BackupCarnetsService {
    @Autowired
    private BackupCarnetsRepository backupCarnetsRepository;

    public void generarBackupCarnets() {
        BackupCarnets backup = new BackupCarnets(new Date(), List.of());
        backupCarnetsRepository.save(backup);
    }

}
