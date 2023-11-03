package com.empresa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{

	
	
	
	
	
	@Query("select e from Empleado e where e.nombres like ?1 or e.apellidos like ?1")
	public List<Empleado> listaEmpleadoNombreApellidoLike(String filtro);

	
	
	
	
	@Query("select e from Empleado e where e.nombres = ?1 and e.apellidos = ?2")
	public List<Empleado> listaEmpleadoNombreApellidoIgual(String nombre, String apellido);

	
	
	
	
	@Query("select e from Empleado e where e.nombres = ?1 and e.apellidos = ?2 and e.idEmpleado != ?3")
	public List<Empleado> listaEmpleadoNombreApellidoIgualActualiza(String nombre, String apellido, int idEmpleado);
	
	
	
	//CONSULTA COMPLEJA POR VARIAS FORMAS
	//nueva consulta por estados 
	// "( ?2== -1 or e.pais.idPais =?2 ) ") : ver del ciclo pasado consulta completa 
	//   usando ternario en consulta Personalizada                 usando or  , recordar dejar espacio al final en el and
	
	@Query("select e from Empleado e where ( e.estado = ?1 ) and  "
			+ "( ?2 = -1 or e.pais.idPais =?2 ) and "
			+ "(e.nombres like ?3 or e.apellidos like ?3 ) and "
			+ " ( e.fechaNacimiento >= ?4 ) and "
			+ "( e.fechaNacimiento <= ?5 )")
	public List<Empleado> listaConsultaEmpleado(int estado ,int idPais , String nomApe , Date desde , Date hasta);
	
	
	

}
