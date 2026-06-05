package com.ejemplo.tienda_tecnologica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.tienda_tecnologica.model.Producto;
import com.ejemplo.tienda_tecnologica.repository.ProductoRepository;



@Service
public class ProductoService {
	
	@Autowired
    private ProductoRepository repositorio;
	
	public List<Producto> listarTodos() {
        return repositorio.findAll();
    }

    public void guardar(Producto producto) {
        repositorio.save(producto);
    }
    public Producto buscarPorId(Long id) {
        return repositorio.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}
