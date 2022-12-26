package com.ProyectoIntegrador.service;

import com.ProyectoIntegrador.dto.TurnoDTO;
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
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void addTurno() {
        Odontologo odontologoToSave = new Odontologo("2323","Tom","R");
        Paciente pacienteToSave = new Paciente("Tomas","Rodriguez","232323", LocalDate.of(2003,5,5),
                "tom@gmail.com", new Domicilio("Chubut",123,"Bsas","Bsas"));
        Odontologo odontologoSaved = odontologoService.agregarOdontologo(odontologoToSave);
        Paciente pacienteSaved = pacienteService.agregarPaciente(pacienteToSave);
        TurnoDTO turnoToSave = new TurnoDTO(LocalDate.of(2003,5,5),odontologoSaved.getId(),pacienteSaved.getId());
        TurnoDTO turnoSaved = turnoService.agregarTurno(turnoToSave);
        assertEquals(1L,turnoSaved.getId());
    }
    @Test
    @Order(2)
    public void searchById() {
        Long idToSearch=1L;
        Optional<TurnoDTO> pacienteBuscado=turnoService.buscarTurno(idToSearch);
        assertNotNull(pacienteBuscado.get());
    }
    @Test
    @Order(3)
    public void searchList() {
        List<TurnoDTO> listTurno= turnoService.buscarTurnos();
        Integer listSize=1;
        assertEquals(listSize,listTurno.size());
    }
    @Test
    @Order(4)
    public void updateTurno() {
        Odontologo odontologoToSave = new Odontologo("2323","Tom","R");
        Paciente pacienteToSave = new Paciente("Tomas","Rodriguez","232323", LocalDate.of(2003,5,5),
                "tom@gmail.com", new Domicilio("Chubut",123,"Bsas","Bsas"));
        Odontologo odontologoSaved = odontologoService.agregarOdontologo(odontologoToSave);
        Paciente pacienteSaved = pacienteService.agregarPaciente(pacienteToSave);
        LocalDate dateToUpdate = LocalDate.of(2020,6,6);
        TurnoDTO turnoToUpdate= new TurnoDTO(1L,dateToUpdate,odontologoSaved.getId(),pacienteSaved.getId());
        turnoService.actualizarTurno(turnoToUpdate);
        assertEquals(dateToUpdate,turnoToUpdate.getFecha());
    }
    @Test
    @Order(5)
    public void deleteTurno() throws ResourceNotFoundException {
        Long idToDelete=1L;
        turnoService.eliminarTurno(idToDelete);
        Optional<TurnoDTO> turnoDeleted=turnoService.buscarTurno(idToDelete);
        assertFalse(turnoDeleted.isPresent());
    }
}