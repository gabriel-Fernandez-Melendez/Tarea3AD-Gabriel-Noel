package com.Gabriel.Noel.tarea3AD2024base.modelo;

import java.util.ArrayList;

public class ConjuntoContratado {
	private Long id;
	private Double precio_total;
	private char modo_de_pago;
	private String detalle_pedido;
	
	//coleccion de los conjuntos de servicios que se contratan
	private ArrayList<Servicio> servicios;
	
	public ConjuntoContratado() {
		
	}
	public ConjuntoContratado(Long id, Double precio_total, char modo_de_pago, String detalle_pedido) {
		this.id = id;
		this.precio_total = precio_total;
		this.modo_de_pago = modo_de_pago;
		this.detalle_pedido = detalle_pedido;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getPrecio_total() {
		return precio_total;
	}
	public void setPrecio_total(Double precio_total) {
		this.precio_total = precio_total;
	}
	public char getModo_de_pago() {
		return modo_de_pago;
	}
	public void setModo_de_pago(char modo_de_pago) {
		this.modo_de_pago = modo_de_pago;
	}
	public String getDetalle_pedido() {
		return detalle_pedido;
	}
	public void setDetalle_pedido(String detalle_pedido) {
		this.detalle_pedido = detalle_pedido;
	}
	
	public ArrayList<Servicio> getServicios() {
		return servicios;
	}
	public void setServicios(ArrayList<Servicio> servicios) {
		this.servicios = servicios;
	}
	@Override
	public String toString() {
		return "ConjuntoContratado [id=" + id + ", precio_total=" + precio_total + ", modo_de_pago=" + modo_de_pago
				+ ", detalle_pedido=" + detalle_pedido + "]";
	}

	
}
