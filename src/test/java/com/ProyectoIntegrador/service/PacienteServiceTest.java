package com.ProyectoIntegrador.service;

import com.ProyectoIntegrador.entity.Domicilio;
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
class PacienteServiceTest {


    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPacienteTest(){
        Paciente pacienteAGuardar= new Paciente("Gian","Luca","43921189",LocalDate.of(2022,12,12),"G@GMAIL.COM",new Domicilio("Gallo",344,"CABA","BSAS"));
        Paciente pacienteGuardado=pacienteService.agregarPaciente(pacienteAGuardar);
        assertEquals(1L,pacienteGuardado.getId());
    }
    @Test
    @Order(2)
    public void buscarPacientePorIdTest(){
        Long idABuscar=1L;
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPaciente(idABuscar);
        assertNotNull(pacienteBuscado.get());
    }
    @Test
    @Order(3)
    public void buscarPacientesTest(){
        List<Paciente> pacientes= pacienteService.listaPacientes();
        //por la cantidad de los pacientes
        Integer cantidadEsperada=1;
        assertEquals(cantidadEsperada,pacientes.size());
    }
    @Test
    @Order(4)
    public void actualizarPacienteTest(){
        Paciente pacienteAActualizar= new Paciente(1L,"Ezequiel","Baspineiro"
                ,"5161", LocalDate.of(2022,11,28),"prueba@gmail.com",
                new Domicilio(1L,"Calle a",548,"Salta Capital","Salta"));
        pacienteService.actualizarPaciente(pacienteAActualizar);
        Optional<Paciente> pacienteActualizado= pacienteService.buscarPaciente(pacienteAActualizar.getId());
        assertEquals("Ezequiel",pacienteActualizado.get().getNombre());
    }
    @Test
    @Order(5)
    public void eliminarPacienteTest() throws ResourceNotFoundException {
        Long idAEliminar=1L;
        pacienteService.borrarPaciente(idAEliminar);
        Optional<Paciente> pacienteEliminado=pacienteService.buscarPaciente(idAEliminar);
        assertFalse(pacienteEliminado.isPresent());
    }


     
}