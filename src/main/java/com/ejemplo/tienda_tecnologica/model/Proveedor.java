package com.ejemplo.tienda_tecnologica.model;

import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name= "proveedor")
public class Proveedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private String nombreEmpresa;
	private String contacto;
	private String telefono;
	
	 @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Producto> productos;

	 public Long getId() {
		 return id;
	 }

	 public void setId(Long id) {
		 this.id = id;
	 }

	 public String getNombreEmpresa() {
		 return nombreEmpresa;
	 }

	 public void setNombreEmpresa(String nombreEmpresa) {
		 this.nombreEmpresa = nombreEmpresa;
	 }

	 public String getContacto() {
		 return contacto;
	 }

	 public void setContacto(String contacto) {
		 this.contacto = contacto;
	 }

	 public String getTelefono() {
		 return telefono;
	 }

	 public void setTelefono(String telefono) {
		 this.telefono = telefono;
	 }

	 public List<Producto> getProductos() {
		 return productos;
	 }

	 public void setProductos(List<Producto> productos) {
		 this.productos = productos;
	 }

	
}
