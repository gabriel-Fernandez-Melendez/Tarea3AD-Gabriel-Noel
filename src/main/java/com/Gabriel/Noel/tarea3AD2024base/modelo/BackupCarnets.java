package com.Gabriel.Noel.tarea3AD2024base.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "backups")
public class BackupCarnets {
	@Id
	private String id;
	private String fechaBackup;
	private List<String> carnets;

	public BackupCarnets(String fechaBackup, List<String> carnets) {
		this.fechaBackup = fechaBackup;
		this.carnets = carnets;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFechaBackup() {
		return fechaBackup;
	}

	public void setFechaBackup(String fechaBackup) {
		this.fechaBackup = fechaBackup;
	}

	public List<String> getCarnets() {
		return carnets;
	}

	public void setCarnets(List<String> carnets) {
		this.carnets = carnets;
	}


	
}