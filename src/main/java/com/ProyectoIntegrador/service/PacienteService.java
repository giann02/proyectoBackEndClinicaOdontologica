package com.ProyectoIntegrador.service;



import com.ProyectoIntegrador.entity.Odontologo;
import com.ProyectoIntegrador.entity.Paciente;
import com.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.ProyectoIntegrador.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);
    private PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente agregarPaciente(Paciente paciente){
        LOGGER.info("Se inicio una operacion de guardado del odontologo con apellido " + paciente.getApellido());
        return pacienteRepository.save(paciente);
    }

    public void actualizarPaciente(Paciente paciente){
        LOGGER.info("Se inicio una operacion de actualizado de paciente con ID= " + paciente.getId());
        pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPaciente(Long id){
        LOGGER.info("Se inicio una operacion de busqueda de pacientes con ID " + id);
        return pacienteRepository.findById(id);
    }

    public List<Paciente> listaPacientes(){
        LOGGER.info("Se inicio una operacion de listado de pacientes ");
        return pacienteRepository.findAll();
    }

    public void borrarPaciente(Long id) throws ResourceNotFoundException{

        Optional<Paciente> pacienteAEliminar=buscarPaciente(id);
        if (pacienteAEliminar.isPresent()){
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se realizo una operacion de eliminado de paciente con id " + id);
            
        }
        else{
            throw new ResourceNotFoundException("El paciente a eliminar no existe" +
                    " en la base de datos, se intentó encontrar sin éxito en id= "+id);
        }

    }

    public Optional<Paciente> buscarPacienteByEmail(String email){
        return pacienteRepository.findByEmail(email);
    }

}
