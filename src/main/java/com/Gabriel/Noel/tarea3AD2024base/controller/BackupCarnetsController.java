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
        backupCarnetsService.generarBackupCarnets(); //el string representa un  html to guapo en el navegador cuando lo ejecutes noel
        return "<!DOCTYPE html>\r\n"
        		+ "<html>\r\n"
        		+ "<head>\r\n"
        		+ "    <style>\r\n"
        		+ "        body {\r\n"
        		+ "            margin: 0;\r\n"
        		+ "            padding: 0;\r\n"
        		+ "            display: flex;\r\n"
        		+ "            flex-direction: column;\r\n"
        		+ "            justify-content: center;\r\n"
        		+ "            align-items: center;\r\n"
        		+ "            min-height: 100vh;\r\n"
        		+ "            background-color: #3498db; /* Blue background */\r\n"
        		+ "            font-family: Arial, sans-serif;\r\n"
        		+ "        }\r\n"
        		+ "        \r\n"
        		+ "        .main-text {\r\n"
        		+ "            font-size: 2.5rem;\r\n"
        		+ "            text-align: center;\r\n"
        		+ "            color: white; /* White text for better contrast */\r\n"
        		+ "            margin-bottom: 20px;\r\n"
        		+ "            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);\r\n"
        		+ "        }\r\n"
        		+ "        \r\n"
        		+ "        .secondary-text {\r\n"
        		+ "            font-size: 1.2rem;\r\n"
        		+ "            text-align: center;\r\n"
        		+ "            color: #ecf0f1; /* Light gray for secondary text */\r\n"
        		+ "            margin-top: 20px;\r\n"
        		+ "        }\r\n"
        		+ "    </style>\r\n"
        		+ "</head>\r\n"
        		+ "<body>\r\n"
        		+ "    <div class=\"main-text\">Los datos de MongoDB se han actualizado</div>\r\n"
        		+ "    <div class=\"secondary-text\">Entre en su visualizador de Mongo para ver los datos</div>\r\n"
        		+ "</body>\r\n"
        		+ "</html>";
    }
}

