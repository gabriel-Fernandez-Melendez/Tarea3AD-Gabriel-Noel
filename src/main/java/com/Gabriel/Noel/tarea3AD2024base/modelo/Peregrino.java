package com.Gabriel.Noel.tarea3AD2024base.modelo;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//prueba git
@Entity
@Table(name = "peregrinos") // Nombre de la tabla en la base de datos
public class Peregrino implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100) // Nombre del peregrino
	private String nombre;

	@Column(nullable = false, length = 50) // Nacionalidad del peregrino
	private String nacionalidad;

	//nota para noel , para que cargaran correctamente los datos en la tabla de exportarXML tuve que agregar fetchtype.EAGER a todas
	
	// Relación Uno a Uno con Carnet
	@OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true,fetch = FetchType.EAGER)
	@JoinColumn(name = "carnet_id", referencedColumnName = "id", nullable = false) // Clave foránea hacia Carnet
	private Carnet carnet;

	// Relación Uno a Muchos con Estancia
	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	private List<Estancia> estancias = new ArrayList<>();

	// Relación Uno a Muchos con ParadaSellada
	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	private List<ParadaSellada> paradasSelladas = new ArrayList<>();
	// magia divina de un  indio de youtube https://www.youtube.com/watch?v=Ivm-Rep-ayw
	@OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name = "Credenciales_id", referencedColumnName = "id", nullable = false) //preguntar a luis por que de forma bidirecional no 
	private Credenciales credenciales;

	// Constructor por defecto
	public Peregrino() {
	}

	// Constructor con parámetros
	public Peregrino(Long id, String nombre, String nacionalidad, Carnet carnet, List<Estancia> estancias,
			List<ParadaSellada> paradasSelladas, Credenciales credenciales) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.carnet = carnet;
		this.estancias = estancias;
		this.paradasSelladas = paradasSelladas;
		this.credenciales = credenciales;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Carnet getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}

	public List<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(List<Estancia> estancias) {
		this.estancias = estancias;
	}

	public List<ParadaSellada> getParadasSelladas() {
		return paradasSelladas;
	}

	public void setParadasSelladas(List<ParadaSellada> paradasSelladas) {
		this.paradasSelladas = paradasSelladas;
	}
	
	//getters y setters de la relacion onetoone
		public Credenciales getCredenciales() {
		return credenciales;
	}

	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}


	// Métodos hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, nacionalidad, carnet, estancias, paradasSelladas);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Peregrino other = (Peregrino) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(nacionalidad, other.nacionalidad) && Objects.equals(carnet, other.carnet)
				&& Objects.equals(estancias, other.estancias) && Objects.equals(paradasSelladas, other.paradasSelladas);
	}

	// Método toString
	@Override
	public String toString() {
		return "Peregrino [id=" + id + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", carnet=" + carnet
				+ ", estancias=" + estancias + ", paradasSelladas=" + paradasSelladas + "]";
	}
}
