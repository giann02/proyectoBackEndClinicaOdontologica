package com.ProyectoIntegrador.service;

import com.ProyectoIntegrador.dto.TurnoDTO;
import com.ProyectoIntegrador.entity.Odontologo;
import com.ProyectoIntegrador.entity.Paciente;
import com.ProyectoIntegrador.entity.Turno;
import com.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.ProyectoIntegrador.repository.OdontologoRepository;
import com.ProyectoIntegrador.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);
    private TurnoRepository turnoRepository;
    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public TurnoDTO agregarTurno (TurnoDTO turno){
        Turno turnoAGuardar=turnoDTOaTurno(turno);
        Turno turnoGuardado=turnoRepository.save(turnoAGuardar);
        LOGGER.info("Se inicio una operacion de guardado de turno con fecha " + turnoGuardado.getFecha());

        return turnoATurnoDTO(turnoGuardado);
    }
    public void eliminarTurno(Long id) throws ResourceNotFoundException {

        Optional<TurnoDTO> turnoAEliminar=buscarTurno(id);
        if (turnoAEliminar.isPresent()){
            turnoRepository.deleteById(id);
            LOGGER.warn("Se realizo una operacion de eliminado de turno con id " + id);
        }
        else{
            throw new ResourceNotFoundException("El turno a eliminar no existe" +
                    " en la base de datos, se intentó encontrar sin éxito en id= "+id);
        }

    }
    public void actualizarTurno(TurnoDTO turno){
        Turno turnoAActualizar=turnoDTOaTurno(turno);
        LOGGER.info("Se inicio una operacion de actualizado de turno con ID= " + turno.getId());
        turnoRepository.save(turnoAActualizar);
    }
    public Optional<TurnoDTO> buscarTurno(Long id){
        Optional<Turno> turnoBuscado=turnoRepository.findById(id);
        if (turnoBuscado.isPresent()){
            //turno encontrado
            LOGGER.info("Se inicio una operacion de busqueda de turno con ID " + id);
            return Optional.of(turnoATurnoDTO(turnoBuscado.get()));
        }
        else{
            //no se encuentra el turno
            return Optional.empty();
        }
    }
    public List<TurnoDTO> buscarTurnos(){
        LOGGER.info("Se inicio una operacion de listado de turnos ");
        List<Turno>turnosEncontrados=turnoRepository.findAll();
        List<TurnoDTO> respuesta= new ArrayList<>();
        for (Turno t:turnosEncontrados) {
            respuesta.add(turnoATurnoDTO(t));
        }
        return respuesta;
    }
    private TurnoDTO turnoATurnoDTO(Turno turno){
        //convertir ese turno en un turno DTO
        TurnoDTO respuesta=new TurnoDTO();
        respuesta.setId(turno.getId());
        respuesta.setFecha(turno.getFecha());
        respuesta.setOdontologo_id(turno.getOdontologo().getId());
        respuesta.setPaciente_id(turno.getPaciente().getId());
        return respuesta;
    }
    private Turno turnoDTOaTurno(TurnoDTO turnoDTO){
        Turno turno= new Turno();
        Paciente paciente= new Paciente();
        Odontologo odontologo= new Odontologo();
        //cargar los elementos
        paciente.setId(turnoDTO.getPaciente_id());
        odontologo.setId(turnoDTO.getOdontologo_id());
        turno.setId(turnoDTO.getId());
        turno.setFecha(turnoDTO.getFecha());
        //asociar cada elemento
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        //salida
        return turno;
    }

}
