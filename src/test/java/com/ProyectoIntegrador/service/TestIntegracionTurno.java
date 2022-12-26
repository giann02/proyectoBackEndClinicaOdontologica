package com.ProyectoIntegrador.service;

import com.ProyectoIntegrador.dto.TurnoDTO;
import com.ProyectoIntegrador.entity.Domicilio;
import com.ProyectoIntegrador.entity.Odontologo;
import com.ProyectoIntegrador.entity.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TestIntegracionTurno {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private MockMvc mockMvc;

    public void setInitialValues(){
        Domicilio domicilio = new Domicilio("Chubut",11,"San isidro","Bsas");
        Odontologo odontologo = new Odontologo("AAA22323","Tomas","Rodriguez");
        Paciente paciente = new Paciente("Tomass","Rodriguezz","23232", LocalDate.of(2003,05,05),"tom@gmail.com",domicilio);
        Odontologo odontologoSaved = odontologoService.agregarOdontologo(odontologo);
        Paciente pacienteSaved = pacienteService.agregarPaciente(paciente);
        TurnoDTO turnoDTO = new TurnoDTO(LocalDate.of(2000,5,2),odontologoSaved.getId(),pacienteSaved.getId());
        turnoService.agregarTurno(turnoDTO);
    }

    @Test
    public void listTurnos() throws Exception {
        setInitialValues();
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }

}
