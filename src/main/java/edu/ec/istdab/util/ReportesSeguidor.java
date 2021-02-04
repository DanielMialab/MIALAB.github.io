package edu.ec.istdab.util;

import java.io.Serializable;

public class ReportesSeguidor implements Serializable{
	private int cantidad;
	private String publicador;
	
	public ReportesSeguidor() {
		
	}	
	
	public ReportesSeguidor(int cantidad, String publicador) {
		this.cantidad = cantidad;
		this.publicador = publicador;
	}
	
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getPublicador() {
		return publicador;
	}
	public void setPublicador(String publicador) {
		this.publicador = publicador;
	}
	
	
}
