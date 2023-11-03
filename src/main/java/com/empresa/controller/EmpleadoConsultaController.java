package com.empresa.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empresa.entity.Empleado;
import com.empresa.service.EmpleadoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@CommonsLog
public class EmpleadoConsultaController {

	
	@Autowired
	private EmpleadoService service;
	
	
	@GetMapping("/verConsultaEmpleado")
	public String verInicio() {
		return "consultaEmpleado";
	}
	
	
	//String fecDesde, String fecHasta  : las fechas del jsp se reciben en string entonces debes convertir a date
	@GetMapping("/consultaEmpleado")
	@ResponseBody
	public List<Empleado> listaEmpleado(int estado ,int idPais , String nomApe, String fecDesde, String fecHasta ){
	
		//"%" + nomApe + "%" : el nombre buscara tanto en nombre y apellido
		return service.listaConsultaEmpleado(estado , idPais , "%" + nomApe + "%" , Date.valueOf(fecDesde) ,Date.valueOf(fecHasta) );
	}
	
	
	
	
	
	//recordar control + shift  + o (importaciones para el metodo del jasper report)
	
	@GetMapping("/reporteEmpleadoPdf")
	@ResponseBody
	public void report(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			//PASO 1: Obtener el dataSource que va generar el reporte
			List<Empleado> lstSalida = service.listaPorNombreApellidoLike("%"); //lista todo para el reporte
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);
			
			//PASO 2: Obtener el archivo que contiene el diseño del reporte
			///ReporteEmpleados.jasper NOMBRE DEL JASPER REPORT EN > reportes el paquete
			String fileDirectory = request.getServletContext().getRealPath("/WEB-INF/reportes/reportesEmpleados.jasper");
			log.info(">>> " + fileDirectory);
			FileInputStream stream   = new FileInputStream(new File(fileDirectory));
			
			//PASO 3: Parámetros adicionales
			Map<String,Object> params = new HashMap<String,Object>();
		
			
			//PASO 4: Enviamos dataSource, diseño y parámetros para generar el PDF
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);//crea el reporte
			
			//PASO 5: Enviar el PDF generado
			response.setContentType("application/x-pdf");
		    response.addHeader("Content-disposition", "attachment; filename=reportesEmpleados.pdf");

			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	
	
		
	
}
