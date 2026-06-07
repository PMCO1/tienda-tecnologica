package com.ejemplo.tienda_tecnologica.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ejemplo.tienda_tecnologica.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    List<Proveedor> findByNombreEmpresaContainingIgnoreCase(String nombreEmpresa);
}