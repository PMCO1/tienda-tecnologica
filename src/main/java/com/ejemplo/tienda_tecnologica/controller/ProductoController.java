package com.ejemplo.tienda_tecnologica.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ejemplo.tienda_tecnologica.model.Producto;
import com.ejemplo.tienda_tecnologica.service.ProductoService;
import com.ejemplo.tienda_tecnologica.service.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public String ver(@RequestParam(required = false, defaultValue = "") String buscar, Model modelo) {
        modelo.addAttribute("productos", productoService.buscar(buscar));
        modelo.addAttribute("buscar", buscar);
        return "lista_productos";
    }

    @GetMapping("/new_producto")
    public String mostrarForm(Model modelo) {
        modelo.addAttribute("producto", new Producto());
        modelo.addAttribute("proveedores", proveedorService.listarTodos());
        return "form_productos";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Producto producto,
                       BindingResult result,
                       Model modelo,
                       RedirectAttributes flash) {
        if (result.hasErrors()) {
            modelo.addAttribute("proveedores", proveedorService.listarTodos());
            return "form_productos";
        }
        try {
            productoService.guardar(producto);
            flash.addFlashAttribute("exito", "Producto guardado correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }

    @GetMapping("/edite/{id}")
    public String edite(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.buscarPorId(id));
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "form_productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        try {
            productoService.eliminar(id);
            flash.addFlashAttribute("exito", "Producto eliminado correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el producto");
        }
        return "redirect:/productos";
    }

    @GetMapping("/reportes")
    public String reportes(@RequestParam(required = false) Long proveedorId, Model model) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Lista de proveedores para el select
            model.addAttribute("proveedores", proveedorService.listarTodos());
            model.addAttribute("proveedorSeleccionado", proveedorId);

            if (proveedorId != null) {
                // Modo filtrado — solo el proveedor seleccionado
                List<Map<String, Object>> datosFiltrados = productoService.obtenerStockPorProveedorFiltrado(proveedorId);
                List<Producto> productosFiltrados = productoService.obtenerProductosPorProveedor(proveedorId);
                model.addAttribute("stockPorProveedor", datosFiltrados);
                model.addAttribute("stockJson", mapper.writeValueAsString(datosFiltrados));
                model.addAttribute("productosFiltrados", productosFiltrados);
            } else {
                // Modo general — todos los proveedores
                List<Map<String, Object>> datos = productoService.obtenerStockPorProveedor();
                model.addAttribute("stockPorProveedor", datos);
                model.addAttribute("stockJson", mapper.writeValueAsString(datos));
                model.addAttribute("productosFiltrados", null);
            }

        } catch (Exception e) {
            model.addAttribute("stockPorProveedor", List.of());
            model.addAttribute("stockJson", "[]");
            model.addAttribute("productosFiltrados", null);
        }
        return "reportes";
    }
}