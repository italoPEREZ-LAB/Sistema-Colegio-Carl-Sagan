package com.cibertec.colegio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.colegio.model.Grado;
import com.cibertec.colegio.service.GradoService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/grado")
public class GradoController {

	@Autowired
    private GradoService gradoService;



    @GetMapping
    public List<Grado> listar() {
        return gradoService.listarTodos();
    }
    
    @PostMapping
    public Grado crearProducto(@RequestBody Grado grado) {
        return gradoService.guardar(grado);
    }

    @GetMapping("/{id}")
    public Grado obtenerProducto(@PathVariable Integer id) {
        return gradoService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
    	gradoService.eliminar(id);
    }
	
}
