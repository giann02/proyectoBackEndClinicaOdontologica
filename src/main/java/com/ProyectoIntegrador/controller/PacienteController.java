package com.ProyectoIntegrador.controller;


import com.ProyectoIntegrador.entity.Paciente;
import com.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.ProyectoIntegrador.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteService pacienteService;
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @PostMapping
    public ResponseEntity<Paciente> registrarNuevoPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.agregarPaciente(paciente));
    }
    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPaciente(paciente.getId());
        if (pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("Se actulizó el paciente con " +
                    "apellido "+paciente.getApellido());
        }
        else{
            return ResponseEntity.badRequest().body("El paciente con id= "+
                    paciente.getId()+" no existe en la BD." +
                    "No puede actualizar algo que no existe :(");
        }
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> buscarPacientes(){
        return ResponseEntity.ok(pacienteService.listaPacientes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long id){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPaciente(id);
        if (pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        pacienteService.borrarPaciente(id);
        return ResponseEntity.ok("Se eliminó al paciente con id= " + id);
    }
}
