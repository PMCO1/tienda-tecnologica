package com.ejemplo.tienda_tecnologica.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ejemplo.tienda_tecnologica.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT p.proveedor.nombreEmpresa, SUM(p.stock) FROM Producto p GROUP BY p.proveedor.nombreEmpresa")
    List<Object[]> stockPorProveedor();

    @Query("SELECT p.proveedor.nombreEmpresa, SUM(p.stock) FROM Producto p WHERE p.proveedor.id = :proveedorId GROUP BY p.proveedor.nombreEmpresa")
    List<Object[]> stockPorProveedorFiltrado(@Param("proveedorId") Long proveedorId);

    List<Producto> findByProveedorId(Long proveedorId);
}