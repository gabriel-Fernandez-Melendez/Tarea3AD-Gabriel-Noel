package com.Gabriel.Noel.tarea3AD2024base.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Gabriel.Noel.tarea3AD2024base.services.BackupCarnetsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/backup")
public class BackupCarnetsController {
    @Autowired
    private BackupCarnetsService backupCarnetsService;

    @GetMapping
    public String generarBackup() {
        backupCarnetsService.generarBackupCarnets();
        return "Backup guardado en MongoDB.";
    }
}

