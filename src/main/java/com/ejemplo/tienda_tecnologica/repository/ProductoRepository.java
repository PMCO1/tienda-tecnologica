package com.ejemplo.tienda_tecnologica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.tienda_tecnologica.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
