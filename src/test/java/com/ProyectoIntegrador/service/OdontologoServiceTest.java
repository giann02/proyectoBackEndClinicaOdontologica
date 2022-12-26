package com.ProyectoIntegrador.service;

import com.ProyectoIntegrador.entity.Domicilio;
import com.ProyectoIntegrador.entity.Odontologo;
import com.ProyectoIntegrador.entity.Paciente;
import com.ProyectoIntegrador.exception.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest

class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologoTest(){
        Odontologo odontologoAGuardar= new Odontologo("A54","Gian","Panigatti");
        Odontologo odontologoGuardado=odontologoService.agregarOdontologo(odontologoAGuardar);
        assertEquals(1L,odontologoGuardado.getId());
    }
    @Test
    @Order(2)
    public void buscarOdontologoPorIdTest(){
        Long idABuscar=1L;
        Optional<Odontologo> odontologoBuscado=odontologoService.buscarOdontologo(idABuscar);
        assertNotNull(odontologoBuscado.get());
    }
    @Test
    @Order(3)
    public void buscarOdontologosTest(){
        List<Odontologo> odontologos= odontologoService.listaOdontologos();
        //por la cantidad de los pacientes
        Integer cantidadEsperada=1;
        assertEquals(cantidadEsperada,odontologos.size());
    }
    @Test
    @Order(4)
    public void actualizarOdontologoTest(){
        Odontologo odontologoAActualizar = new Odontologo("A34","Gian","Panigatti");
        odontologoService.actualizarOdontologo(odontologoAActualizar);
        Optional<Odontologo> odontologoActualizado= odontologoService.buscarOdontologo(odontologoAActualizar.getId());
        assertEquals("Gian",odontologoActualizado.get().getNombre());
    }
    @Test
    @Order(5)
    public void eliminarOdontologoTest() throws ResourceNotFoundException {
        Long idAEliminar=1L;
        odontologoService.borrarOdontologo(idAEliminar);
        Optional<Odontologo> odontologoEliminado=odontologoService.buscarOdontologo(idAEliminar);
        assertFalse(odontologoEliminado.isPresent());
    }

}