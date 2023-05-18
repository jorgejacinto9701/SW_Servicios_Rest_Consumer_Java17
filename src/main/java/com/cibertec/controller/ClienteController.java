package com.cibertec.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cibertec.entity.Categoria;
import com.cibertec.entity.Cliente;
import com.cibertec.entity.Respuesta;


@Controller
public class ClienteController {

	String URL_CLIENTE = "https://api-cibertec-moviles.herokuapp.com/servicio/cliente";
	String URL_CATEGORIA = "https://api-cibertec-moviles.herokuapp.com/servicio/util/listaCategoriaDeLibro";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/verCrudCliente")
	public String verBoleta() {
		return "crudCliente";
	}  
	
	@RequestMapping("/consultaCrudCliente")
	@ResponseBody
	public List<Cliente> consulta(String filtro){
		ResponseEntity<List<Cliente>> responseEntity = 	restTemplate.exchange(URL_CLIENTE, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cliente>>(){});
		List<Cliente> lstCliente = responseEntity.getBody();
		return lstCliente;
	}
	
	@RequestMapping("/cargaCategoria")
	@ResponseBody
	public List<Categoria> carga(){
		ResponseEntity<List<Categoria>> responseEntity = 	restTemplate.exchange(URL_CATEGORIA, HttpMethod.GET, null, new ParameterizedTypeReference<List<Categoria>>(){});
		List<Categoria> lstCategoria = responseEntity.getBody();
		return lstCategoria;
	}
	
	@RequestMapping("/eliminaFisicaCrudCliente")
	@ResponseBody
	public Respuesta eliminaFisica(String idCliente){
		//1 Elimina
		restTemplate.delete(URL_CLIENTE + "/" + idCliente);
		
		//2 Listar todos
		ResponseEntity<List<Cliente>> responseEntity = 	restTemplate.exchange(URL_CLIENTE, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cliente>>(){});
		List<Cliente> lstCliente = responseEntity.getBody();
		
		Respuesta objRespuesta = new Respuesta();
		objRespuesta.setDatos(lstCliente);
		objRespuesta.setMensaje("Eliminación exitosa");
		return objRespuesta;
	}
	
	@RequestMapping("/registraCrudCliente")
	@ResponseBody
	public Respuesta registra(Cliente objCliente){
		objCliente.setEstado(1);
		objCliente.setFechaRegistro(new Date());
		
		//1 Registro
		HttpEntity<Cliente> request = new HttpEntity<Cliente>(objCliente);
		restTemplate.postForObject(URL_CLIENTE, request, Cliente.class);
		
		//2 Listar todos
		ResponseEntity<List<Cliente>> responseEntity = 	restTemplate.exchange(URL_CLIENTE, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cliente>>(){});
		List<Cliente> lstCliente = responseEntity.getBody();
		
		Respuesta objRespuesta = new Respuesta();
		objRespuesta.setDatos(lstCliente);
		objRespuesta.setMensaje("Registro exitoso");
		return objRespuesta;
	}
	
	@RequestMapping("/actualizaCrudCliente")
	@ResponseBody
	public Respuesta actualiza(Cliente objCliente){
		
		//1 Busca por ID 
		ResponseEntity<Cliente> responseEntity1 = 	restTemplate.exchange(URL_CLIENTE +"/"+objCliente.getIdCliente(), HttpMethod.GET, null, new ParameterizedTypeReference<Cliente>(){});
		Cliente objActual = responseEntity1.getBody();
		
		//2 Actualizo los datos de cliente
		objActual.setNombre(objCliente.getNombre());
		objActual.setEstado(objCliente.getEstado());
		objActual.setDni(objCliente.getDni());
		objActual.setCategoria(objCliente.getCategoria());
		
		//3 Actualizo Cliente
		HttpEntity<Cliente> request = new HttpEntity<Cliente>(objCliente);
		restTemplate.put(URL_CLIENTE, request, Cliente.class);
		
		//4 Listar todos
		ResponseEntity<List<Cliente>> responseEntity2 = 	restTemplate.exchange(URL_CLIENTE, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cliente>>(){});
		List<Cliente> lstCliente = responseEntity2.getBody();
		
		Respuesta objRespuesta = new Respuesta();
		objRespuesta.setDatos(lstCliente);
		objRespuesta.setMensaje("Actualización exitosa");
		return objRespuesta;
	}
	
	@RequestMapping("/eliminaLogicaCrudCliente")
	@ResponseBody
	public Respuesta eliminacionLogica(String idCliente){
		//1 Busca por ID 
		ResponseEntity<Cliente> responseEntity1 = 	restTemplate.exchange(URL_CLIENTE +"/"+idCliente, HttpMethod.GET, null, new ParameterizedTypeReference<Cliente>(){});
		Cliente objActual = responseEntity1.getBody();
		
		//2 Cambio el estado
		int estadoNuevo = objActual.getEstado() == 0? 1 : 0;
		objActual.setEstado(estadoNuevo);
		
		//3 Actualizo Cliente
		HttpEntity<Cliente> request = new HttpEntity<Cliente>(objActual);
		restTemplate.put(URL_CLIENTE, request, Cliente.class);

		//4 Listar todos
		ResponseEntity<List<Cliente>> responseEntity2 = 	restTemplate.exchange(URL_CLIENTE, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cliente>>(){});
		List<Cliente> lstCliente = responseEntity2.getBody();

		Respuesta objRespuesta = new Respuesta();
		objRespuesta.setDatos(lstCliente);
		objRespuesta.setMensaje("Eliminación exitosa");
		return objRespuesta;
	}
	
	
}
