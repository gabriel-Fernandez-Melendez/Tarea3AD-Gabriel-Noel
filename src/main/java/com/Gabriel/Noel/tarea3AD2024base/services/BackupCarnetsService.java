package com.Gabriel.Noel.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.controller.ExportarCarnetXMLController;
import com.Gabriel.Noel.tarea3AD2024base.dataeXistdb.ExistdbConnection;
import com.Gabriel.Noel.tarea3AD2024base.modelo.BackupCarnets;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa.CarnetRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.mongo.BackupCarnetsRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class BackupCarnetsService {
    @Autowired
    private BackupCarnetsRepository backupCarnetsRepository;
    @Autowired
    private CarnetService c;


    public void generarBackupCarnets() {
    	 Date currentDate = new Date();
         System.out.println("Current Date and Time: " + currentDate);

         // Format the date and time
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String formattedDate = formatter.format(currentDate);
         //y ahora la lista de carnets

         List<Carnet>carnets =c.ListaDeCarnets();
         List<String> carnets_xml = new ArrayList<>();
         for (Carnet c: carnets) {	
        	String xml= ExistdbConnection.convertirCarnetAXml(c);
        	 carnets_xml.add(xml);
         }
         
        BackupCarnets backup = new BackupCarnets(formattedDate,carnets_xml);
        backupCarnetsRepository.save(backup);
    }

}
