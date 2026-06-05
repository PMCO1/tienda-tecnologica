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

import com.ejemplo.tienda_tecnologica.model.Proveedor;
import com.ejemplo.tienda_tecnologica.service.ProveedorService;


@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
	
	@Autowired
    private ProveedorService proveedorService;
	
	@GetMapping
    public String ver(Model modelo) {
        modelo.addAttribute("proveedores", proveedorService.listarTodos());
        return "lista_proveedores";
    }

    @GetMapping("/new_proveedor")
    public String mostrarForm(Model modelo) {
        modelo.addAttribute("proveedor", new Proveedor());
        return "form_proveedores";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute Proveedor proveedor, RedirectAttributes flash) {
        try {
            proveedorService.guardar(proveedor);
            flash.addFlashAttribute("exito", "Proveedor guardado correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar el proveedor: " + e.getMessage());
        }
        return "redirect:/proveedores";
    }
    
    @GetMapping("/edite/{id}")
    public String edite(@PathVariable Long id, Model model) {
        model.addAttribute("proveedor", proveedorService.buscarPorId(id));
        return "form_proveedores";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        try {
            proveedorService.eliminar(id);
            flash.addFlashAttribute("exito", "Proveedor eliminado correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el proveedor");
        }
        return "redirect:/proveedores";
    }
}
