package com.ProyectoIntegrador.controller;

import com.ProyectoIntegrador.dto.TurnoDTO;
import com.ProyectoIntegrador.entity.Odontologo;
import com.ProyectoIntegrador.entity.Paciente;
import com.ProyectoIntegrador.entity.Turno;
import com.ProyectoIntegrador.exception.BadRequestException;
import com.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.ProyectoIntegrador.service.OdontologoService;
import com.ProyectoIntegrador.service.PacienteService;
import com.ProyectoIntegrador.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;
    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }


    @PostMapping
    public ResponseEntity<TurnoDTO> registarTurno(@RequestBody TurnoDTO turno) throws BadRequestException {
        ResponseEntity<TurnoDTO> respuesta;

        if (pacienteService.buscarPaciente(turno.getPaciente_id()).isPresent()&&
                odontologoService.buscarOdontologo(turno.getOdontologo_id()).isPresent()
        ){
            respuesta=ResponseEntity.ok(turnoService.agregarTurno(turno));
        }
        else{

            throw new BadRequestException("No se puede registrar un turno cuando no exista " +
                    "un odontologo y/o un paciente");

        }
        return respuesta;
    }
    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody TurnoDTO turno){

        ResponseEntity<TurnoDTO> respuesta;

        if(turnoService.buscarTurno(turno.getId()).isPresent()){
            //es un id válido
            if (pacienteService.buscarPaciente(turno.getPaciente_id()).isPresent()&&
                    odontologoService.buscarOdontologo(turno.getOdontologo_id()).isPresent()
            ){
                //ambos existen en la BD
                //podemos registrar el turno sin problemas, indicamos ok (200)
                turnoService.actualizarTurno(turno);
                return ResponseEntity.ok("Se actualizó el turno con id= "+turno.getId());
            }
            else{
                //uno o ambos no existen, debemos bloquear la operación
                return ResponseEntity.badRequest().body("Error al actualizar, verificar si el" +
                        " odontologo y/o el paciente existen en la base de datos.");
            }
        }
        else{
            //error con el id
            return ResponseEntity.badRequest().body("No se puede actualizar un turno" +
                    " que no exista en la base de datos.");
        }
    }
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> buscarTurnos(){
        return ResponseEntity.ok(turnoService.buscarTurnos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurnoPorId(@PathVariable Long id){
        Optional<TurnoDTO> turnoBuscado= turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Se eliminó al turno con id= " + id);
    }
}
