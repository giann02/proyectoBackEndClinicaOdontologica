package com.ProyectoIntegrador.controller;

import com.ProyectoIntegrador.entity.Odontologo;
import com.ProyectoIntegrador.entity.Paciente;
import com.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.ProyectoIntegrador.service.OdontologoService;
import com.ProyectoIntegrador.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/odontologos")
public class OdontologoController {
    private OdontologoService odontologoService;
    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    @PostMapping
    public ResponseEntity<Odontologo> registrarNuevoOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.agregarOdontologo(odontologo));
    }
    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoBuscado=odontologoService.buscarOdontologo(odontologo.getId());
        if (odontologoBuscado.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("Se actualizó el odontologo con " +
                    "apellido "+odontologo.getApellido());
        }
        else{
            return ResponseEntity.badRequest().body("El odontologo con id= "+
                    odontologo.getId()+" no existe en la BD." +
                    "No puede actualizar algo que no existe :(");
        }
    }
    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarOdontologo(){
        return ResponseEntity.ok(odontologoService.listaOdontologos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Long id){
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarOdontologo(id);
        if (odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {

        odontologoService.borrarOdontologo(id);
        return ResponseEntity.ok("Se eliminó al odontologo con id= "+id);
    }

}
