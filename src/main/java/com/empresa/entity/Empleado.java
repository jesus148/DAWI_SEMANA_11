package com.empresa.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "empleado")
public class Empleado {
	
	//en el jasper repórt en los field , los nombre iguales a la clase guia
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmpleado;
	private String nombres;
	private String apellidos;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "America/Lima")
	private Date fechaNacimiento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss" , timezone = "America/Lima")
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss" , timezone = "America/Lima")
	private Date fechaActualizacion;
	
	private int estado;
	
	@ManyToOne
	@JoinColumn(name = "idPais")
	private Pais pais;
	
	
	
	
	
	
	//atributos para el reporte estado
	public String getReporteestado() {
		return estado == 1? "Activo" : "Inactivo";
		
	}
	
	public String getReportepais() {
		return pais.getIdPais() + "- "+  pais.getNombre();
		
	}
	
	
	public String getReportefechaNacimiento() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(fechaNacimiento);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}





