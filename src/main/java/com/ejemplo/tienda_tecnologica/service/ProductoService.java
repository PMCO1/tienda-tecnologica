package com.ejemplo.tienda_tecnologica.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Producto> buscar(String texto) {
        if (texto == null || texto.isBlank()) {
            return repositorio.findAll();
        }
        return repositorio.findByNombreContainingIgnoreCase(texto);
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

    public List<Map<String, Object>> obtenerStockPorProveedor() {
        List<Object[]> resultado = repositorio.stockPorProveedor();
        return convertirResultado(resultado);
    }

    public List<Map<String, Object>> obtenerStockPorProveedorFiltrado(Long proveedorId) {
        List<Object[]> resultado = repositorio.stockPorProveedorFiltrado(proveedorId);
        return convertirResultado(resultado);
    }

    public List<Producto> obtenerProductosPorProveedor(Long proveedorId) {
        return repositorio.findByProveedorId(proveedorId);
    }

    private List<Map<String, Object>> convertirResultado(List<Object[]> resultado) {
        List<Map<String, Object>> datos = new ArrayList<>();
        for (Object[] fila : resultado) {
            Map<String, Object> map = new HashMap<>();
            map.put("proveedor", fila[0]);
            map.put("stock", fila[1]);
            datos.add(map);
        }
        return datos;
    }
}