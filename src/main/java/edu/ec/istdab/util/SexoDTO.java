package edu.ec.istdab.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class SexoDTO {
	
	private List<String> sexos;
	
	public SexoDTO() {
		String[] arreglo = {"Masculino","Femenino"};
		this.sexos=new ArrayList<>(Arrays.asList(arreglo));
	}

	public List<String> getSexos() {
		return sexos;
	}
	
}