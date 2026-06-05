package com.ejemplo.tienda_tecnologica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ejemplo.tienda_tecnologica.model.Proveedor;
import com.ejemplo.tienda_tecnologica.repository.ProveedorRepository;

@Service
public class ProveedorService {
	
	@Autowired
	private ProveedorRepository repositorio;
	
	public List<Proveedor> listarTodos() {
        return repositorio.findAll();
    }
	
	public void guardar(Proveedor proveedor) {
        repositorio.save(proveedor);
    }
	
	public Proveedor buscarPorId(Long id) {
        return repositorio.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
    }
	
	public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}
