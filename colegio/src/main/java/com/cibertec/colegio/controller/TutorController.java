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

import com.cibertec.colegio.model.Tutor;
import com.cibertec.colegio.service.TutorService;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;



    @GetMapping
    public List<Tutor> listar() {
        return tutorService.listarTodos();
    }
    
    @PostMapping
    public Tutor crearProducto(@RequestBody Tutor tutor) {
        return tutorService.guardar(tutor);
    }

    @GetMapping("/{id}")
    public Tutor obtenerProducto(@PathVariable Integer id) {
        return tutorService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
    	tutorService.eliminar(id);
    }

}