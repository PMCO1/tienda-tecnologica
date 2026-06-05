package com.ejemplo.tienda_tecnologica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ejemplo.tienda_tecnologica.model.Producto;
import com.ejemplo.tienda_tecnologica.service.ProductoService;
import com.ejemplo.tienda_tecnologica.service.ProveedorService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	 @Autowired
	    private ProductoService productoService;

	    @Autowired
	    private ProveedorService proveedorService;

	    @GetMapping
	    public String ver(Model modelo) {
	        modelo.addAttribute("productos", productoService.listarTodos());
	        return "lista_productos";
	    }

	    @GetMapping("/new_producto")
	    public String mostrarForm(Model modelo) {
	        modelo.addAttribute("producto", new Producto());
	        modelo.addAttribute("proveedores", proveedorService.listarTodos());
	        return "form_productos";
	    }

	    @PostMapping("/save")
	    public String save(@ModelAttribute Producto producto, RedirectAttributes flash) {
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
	            flash.addFlashAttribute("exito", "producto eliminado correctamente");
	        } catch (Exception e) {
	            flash.addFlashAttribute("error", "Error al eliminar el producto");
	        }
	        return "redirect:/productos";
	    }
}
