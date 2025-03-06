package com.luisdbb.tarea3AD2024base;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.CarnetRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.CredencialesRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ParadasRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.PeregrinoRepository;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;

@ExtendWith(MockitoExtension.class)
public class TestUnitarios_KO_OK {

    @Mock
    private CredencialesRepository credencialesRepo;
    @Mock
    private PeregrinoRepository peregrinoRepo;
    @Mock
    private ParadasRepository paradasRepo;
    @Mock
    private CarnetRepository carnetRepo;

    @InjectMocks
    private CredencialesService credencialesService;
    @InjectMocks
    private PeregrinoService peregrinoService;
    @InjectMocks
    private ParadaService paradaService;

    @Test
    void pruebaLoginValido() {
        Credenciales cred = new Credenciales();
        cred.setId(1L);
        cred.setNombreUsuario("usuarioValido");
        cred.setContraseñaUsuario("passValida");
        cred.setTipo(Usuarios.Peregrino);

        when(credencialesRepo.save(any(Credenciales.class))).thenReturn(cred);
        credencialesService.GuardarCredenciales(cred);

        when(credencialesRepo.findByNombreUsuario("usuarioValido")).thenReturn(cred);
        Credenciales encontrado = credencialesService.obtenerCredencialesPorNombreUsuario("usuarioValido");

        assertNotNull(encontrado);
        assertEquals("passValida", encontrado.getContraseñaUsuario());
    }

    @Test
    void pruebaLoginInvalido() {
        when(credencialesRepo.findByNombreUsuario("inexistente")).thenReturn(null);
        Credenciales c = credencialesService.obtenerCredencialesPorNombreUsuario("inexistente");
        assertNull(c, "No debe existir este usuario");
    }

    @Test
    void registroPeregrinoOK() {
        // Credenciales
        Credenciales cred = new Credenciales();
        cred.setId(2L);
        cred.setNombreUsuario("pereUser");
        cred.setContraseñaUsuario("perePass");
        cred.setCorreo_usuario("pereUser@example.com");
        cred.setTipo(Usuarios.Peregrino);
        when(credencialesRepo.save(any(Credenciales.class))).thenReturn(cred);
        Credenciales credGuardadas = credencialesService.GuardarCredenciales(cred);

        // Parada inicial
        Parada parada = new Parada();
        parada.setId(10L);
        parada.setNombre("ParadaInicial");
        parada.setRegion('X');
        parada.setResponsable("Resp");
        parada.setCredenciales(credGuardadas);
        when(paradasRepo.save(any(Parada.class))).thenReturn(parada);
        Parada paradaGuardada = paradaService.guardarParada(parada);

        // Carnet
        Carnet carnet = new Carnet();
        carnet.setId(100L);
        carnet.setDistancia(0.0);
        carnet.setFechaexp(LocalDate.now());
        carnet.setParadaInicial(paradaGuardada);
        when(carnetRepo.save(any(Carnet.class))).thenReturn(carnet);
        Carnet carnetGuardado = carnetRepo.save(carnet);

        // Peregrino
        Peregrino p = new Peregrino();
        p.setId(1000L);
        p.setNombre("Juan");
        p.setNacionalidad("España");
        p.setCarnet(carnetGuardado);
        p.setCredenciales(credGuardadas);

        when(peregrinoRepo.save(any(Peregrino.class))).thenReturn(p);
        Peregrino resultado = peregrinoService.GuardarPeregrino(p);

        assertNotNull(resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertNotNull(resultado.getCarnet());
        assertNotNull(resultado.getCredenciales());
    }

    @Test
    void registroPeregrinoKO() {
        // Se asume que si el nombre es vacío, PeregrinoService lanza IllegalArgumentException
        Peregrino p = new Peregrino();
        p.setNombre("");
        p.setNacionalidad("España");
        assertThrows(IllegalArgumentException.class, () -> {
            peregrinoService.GuardarPeregrino(p);
        });
    }

    @Test
    void modificarPeregrinoOK() {
        Peregrino p = new Peregrino();
        p.setId(3L);
        p.setNombre("Maria");
        p.setNacionalidad("Italia");
        when(peregrinoRepo.save(any(Peregrino.class))).thenReturn(p);
        Peregrino guardado = peregrinoService.GuardarPeregrino(p);

        guardado.setNombre("Maria Modificada");
        when(peregrinoRepo.save(any(Peregrino.class))).thenReturn(guardado);
        Peregrino modificado = peregrinoService.GuardarPeregrino(guardado);

        assertEquals("Maria Modificada", modificado.getNombre());
    }

    @Test
    void modificarPeregrinoKO() {
        Peregrino p = new Peregrino();
        p.setId(4L);
        p.setNombre("Carlos");
        p.setNacionalidad("Portugal");
        when(peregrinoRepo.save(any(Peregrino.class))).thenReturn(p);
        Peregrino guardado = peregrinoService.GuardarPeregrino(p);

        guardado.setNombre("");
        assertThrows(IllegalArgumentException.class, () -> {
            peregrinoService.GuardarPeregrino(guardado);
        });
    }

    @Test
    void guardarParadaOK() {
        Credenciales cred = new Credenciales();
        cred.setId(5L);
        cred.setNombreUsuario("respUser");
        cred.setContraseñaUsuario("respPass");
        cred.setCorreo_usuario("resp@example.com");
        cred.setTipo(Usuarios.Responsable_Parada);
        when(credencialesRepo.save(any(Credenciales.class))).thenReturn(cred);
        Credenciales credGuardadas = credencialesService.GuardarCredenciales(cred);

        Parada parada = new Parada();
        parada.setId(6L);
        parada.setNombre("ParadaTest");
        parada.setRegion('Z');
        parada.setResponsable("ResponsableParada");
        parada.setCredenciales(credGuardadas);
        when(paradasRepo.save(any(Parada.class))).thenReturn(parada);
        Parada paradaGuardada = paradaService.guardarParada(parada);

        assertNotNull(paradaGuardada.getId());
        assertEquals("ParadaTest", paradaGuardada.getNombre());
    }

    @Test
    void guardarParadaKO() {
        Parada parada = new Parada();
        parada.setNombre("");
        parada.setRegion('Q');
        assertThrows(IllegalArgumentException.class, () -> {
            paradaService.guardarParada(parada);
        });
    }

    @Test
    void listarCredenciales() {
        when(credencialesRepo.findAll()).thenReturn(List.of());
        List<Credenciales> lista = credencialesService.ListaDeCredenciales();
        assertNotNull(lista);
    }

    @Test
    void listarPeregrinos() {
        when(peregrinoRepo.findAll()).thenReturn(List.of());
        List<Peregrino> lista = peregrinoService.ListaDePeregrinos();
        assertNotNull(lista);
    }

    @Test
    void listarParadas() {
        when(paradasRepo.findAll()).thenReturn(List.of());
        List<Parada> lista = paradaService.ListaDeParadas();
        assertNotNull(lista);
    }

    // Placeholders de sellarParada
    @Test
    void sellarParadaOK() {
        assertTrue(true);
    }

    @Test
    void sellarParadaKO() {
        assertTrue(true);
    }
}
