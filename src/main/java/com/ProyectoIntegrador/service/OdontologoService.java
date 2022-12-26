package com.ProyectoIntegrador.service;

import com.ProyectoIntegrador.entity.Odontologo;
import com.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.ProyectoIntegrador.repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    private OdontologoRepository odontologoRepository;
    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }
    public Odontologo agregarOdontologo(Odontologo odontologo){
        LOGGER.info("Se inicio una operacion de guardado del odontologo con apellido " + odontologo.getApellido());
        return odontologoRepository.save(odontologo);
    }
    public void actualizarOdontologo(Odontologo odontologo){
        LOGGER.info("Se inicio una operacion de actualizado de odontologo con ID= " + odontologo.getId());
        odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarOdontologo(Long id){
        LOGGER.info("Se inicio una operacion de busqueda de odontologo con ID " + id);
        return odontologoRepository.findById(id);
    }

    public List<Odontologo> listaOdontologos(){
        LOGGER.info("Se inicio una operacion de listado de odontologos ");
        return odontologoRepository.findAll();
    }

    public void borrarOdontologo(Long id) throws ResourceNotFoundException {

        Optional<Odontologo> odontologoAEliminar=buscarOdontologo(id);
        if (odontologoAEliminar.isPresent()){
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se realizo una operacion de eliminado de odontologo con id " + id);
        }
        else{
            throw new ResourceNotFoundException("El odontologo a eliminar no existe" +
                    " en la base de datos, se intentó encontrar sin éxito en id= "+id);
        }

    }


}
