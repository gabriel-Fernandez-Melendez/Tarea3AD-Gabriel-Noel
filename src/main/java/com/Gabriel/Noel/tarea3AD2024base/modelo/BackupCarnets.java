package com.Gabriel.Noel.tarea3AD2024base.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "backups")
public class BackupCarnets {
	@Id
	private String id;
	private Date fechaBackup;
	private List<Carnet> carnets;

	public BackupCarnets(Date fechaBackup, List<Carnet> carnets) {
		this.fechaBackup = fechaBackup;
		this.carnets = carnets;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFechaBackup() {
		return fechaBackup;
	}

	public void setFechaBackup(Date fechaBackup) {
		this.fechaBackup = fechaBackup;
	}

	public List<Carnet> getCarnets() {
		return carnets;
	}

	public void setCarnets(List<Carnet> carnets) {
		this.carnets = carnets;
	}


	
}