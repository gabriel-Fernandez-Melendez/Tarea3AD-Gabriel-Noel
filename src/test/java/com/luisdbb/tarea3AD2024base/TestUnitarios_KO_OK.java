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
import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.CarnetRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.CredencialesRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ParadasRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ParadasSelladasRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.PeregrinoRepository;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaSelladaService;
import com.Gabriel.Noel.tarea3AD2024base.services.ParadaService;
import com.Gabriel.Noel.tarea3AD2024base.services.PeregrinoService;

@ExtendWith(MockitoExtension.class)
public class TestUnitarios_KO_OK {

    /**
     * 1) Campos @Mock:
     *    - Se crean "repositorios falsos" que NO acceden a una BD real.
     *    - Mockito nos permite simular su comportamiento en los tests.
     */
    @Mock
    private CredencialesRepository credencialesRepo;
    @Mock
    private PeregrinoRepository peregrinoRepo;
    @Mock
    private ParadasRepository paradasRepo;
    @Mock
    private CarnetRepository carnetRepo;
    @Mock
    private ParadasSelladasRepository paradasSelladasRepo;

    /**
     * 2) Campos @InjectMocks:
     *    - Se inyectan los mocks anteriores en los servicios reales.
     *    - Así, cuando el servicio llame a su repositorio, en realidad estará usando el mock.
     */
    @InjectMocks
    private CredencialesService credencialesService;
    @InjectMocks
    private PeregrinoService peregrinoService;
    @InjectMocks
    private ParadaService paradaService;
    @InjectMocks
    private ParadaSelladaService paradaSelladaService;

    /**
     * PRUEBA: Loguearse con credenciales válidas (OK).
     * Escenario:
     * - Se crea un objeto Credenciales (mockeado).
     * - Se simula su guardado (aunque en realidad no se persiste).
     * - Se simula su recuperación por nombre de usuario.
     * - Se comprueba que la contraseña coincide.
     */
    @Test
    void pruebaLoginValido() {
        // Creamos objeto Credenciales de prueba
        Credenciales cred = new Credenciales();
        cred.setId(1L);
        cred.setNombreUsuario("usuarioValido");
        cred.setContraseñaUsuario("passValida");
        cred.setTipo(Usuarios.Peregrino);

        // Simulamos que al "guardar" el repositorio devuelva este mismo objeto con ID
        when(credencialesRepo.save(any(Credenciales.class))).thenReturn(cred);
        credencialesService.GuardarCredenciales(cred);

        // Simulamos que al buscar por nombre de usuario, se retorne el mismo objeto
        when(credencialesRepo.findByNombreUsuario("usuarioValido")).thenReturn(cred);
        Credenciales encontrado = credencialesService.obtenerCredencialesPorNombreUsuario("usuarioValido");

        // Validaciones
        assertNotNull(encontrado);
        assertEquals("passValida", encontrado.getContraseñaUsuario());
    }

    /**
     * PRUEBA: Loguearse con credenciales inválidas (KO).
     * Escenario:
     * - Se busca un usuario que no existe en el mock.
     * - Se espera que retorne null.
     */
    @Test
    void pruebaLoginInvalido() {
        // Simulamos que no existe ese nombre de usuario en el mock
        when(credencialesRepo.findByNombreUsuario("inexistente")).thenReturn(null);

        // Llamada al servicio
        Credenciales c = credencialesService.obtenerCredencialesPorNombreUsuario("inexistente");

        // Validación: debe ser null
        assertNull(c, "No debe existir este usuario");
    }

    /**
     * PRUEBA: Registrar Peregrino (OK).
     * Escenario:
     * - Se crean Credenciales, Parada (como parada inicial) y Carnet.
     * - Se simula el guardado de cada uno con un ID.
     * - Se asocia todo al Peregrino y se comprueba que se guardó con éxito.
     */
    @Test
    void registroPeregrinoOK() {
        // Credenciales
        Credenciales cred = new Credenciales();
        cred.setId(2L);
        cred.setNombreUsuario("pereUser");
        cred.setContraseñaUsuario("perePass");
        cred.setCorreo_usuario("pereUser@example.com");
        cred.setTipo(Usuarios.Peregrino);
        // Al guardar, simulamos que vuelve el mismo objeto con ID
        when(credencialesRepo.save(any(Credenciales.class))).thenReturn(cred);
        Credenciales credGuardadas = credencialesService.GuardarCredenciales(cred);

        // Parada inicial (para el carnet)
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

        // Validaciones
        assertNotNull(resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertNotNull(resultado.getCarnet());
        assertNotNull(resultado.getCredenciales());
    }

    /**
     * PRUEBA: Registrar Peregrino con datos inválidos (KO).
     * Escenario:
     * - Se crea un Peregrino con nombre vacío.
     * - Se asume que PeregrinoService lanza IllegalArgumentException.
     */
    @Test
    void registroPeregrinoKO() {
        Peregrino p = new Peregrino();
        p.setNombre("");
        p.setNacionalidad("España");

        // Se espera que PeregrinoService lance una excepción si el nombre es vacío
        assertThrows(IllegalArgumentException.class, () -> {
            peregrinoService.GuardarPeregrino(p);
        });
    }

    /**
     * PRUEBA: Modificar Peregrino (OK).
     * Escenario:
     * - Se crea un Peregrino con datos iniciales.
     * - Se simula guardarlo (asignando ID).
     * - Se cambia el nombre y se guarda de nuevo.
     */
    @Test
    void modificarPeregrinoOK() {
        // Peregrino inicial
        Peregrino p = new Peregrino();
        p.setId(3L);
        p.setNombre("Maria");
        p.setNacionalidad("Italia");
        when(peregrinoRepo.save(any(Peregrino.class))).thenReturn(p);
        Peregrino guardado = peregrinoService.GuardarPeregrino(p);

        // Modificamos
        guardado.setNombre("Maria Modificada");
        when(peregrinoRepo.save(any(Peregrino.class))).thenReturn(guardado);
        Peregrino modificado = peregrinoService.GuardarPeregrino(guardado);

        assertEquals("Maria Modificada", modificado.getNombre());
    }

    /**
     * PRUEBA: Modificar Peregrino con datos inválidos (KO).
     * Escenario:
     * - Peregrino con nombre válido.
     * - Se modifica para dejar el nombre vacío.
     * - Se espera que lance IllegalArgumentException.
     */
    @Test
    void modificarPeregrinoKO() {
        Peregrino p = new Peregrino();
        p.setId(4L);
        p.setNombre("Carlos");
        p.setNacionalidad("Portugal");
        when(peregrinoRepo.save(any(Peregrino.class))).thenReturn(p);
        Peregrino guardado = peregrinoService.GuardarPeregrino(p);

        // Dejamos el nombre vacío
        guardado.setNombre("");
        assertThrows(IllegalArgumentException.class, () -> {
            peregrinoService.GuardarPeregrino(guardado);
        });
    }

    /**
     * PRUEBA: Guardar Parada (OK).
     * Escenario:
     * - Se crean credenciales para el responsable.
     * - Se crea la Parada con datos correctos.
     * - Se guarda y se comprueba el ID asignado.
     */
    @Test
    void guardarParadaOK() {
        // Credenciales para el responsable
        Credenciales cred = new Credenciales();
        cred.setId(5L);
        cred.setNombreUsuario("respUser");
        cred.setContraseñaUsuario("respPass");
        cred.setCorreo_usuario("resp@example.com");
        cred.setTipo(Usuarios.Responsable_Parada);
        when(credencialesRepo.save(any(Credenciales.class))).thenReturn(cred);
        Credenciales credGuardadas = credencialesService.GuardarCredenciales(cred);

        // Parada
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

    /**
     * PRUEBA: Guardar Parada con datos inválidos (KO).
     * Escenario:
     * - Se crea Parada con nombre vacío.
     * - Se espera que ParadaService lance IllegalArgumentException.
     */
    @Test
    void guardarParadaKO() {
        Parada parada = new Parada();
        parada.setNombre("");
        parada.setRegion('Q');
        assertThrows(IllegalArgumentException.class, () -> {
            paradaService.guardarParada(parada);
        });
    }

    /**
     * PRUEBAS: Listados (OK).
     * - Simulan la llamada a findAll() y devuelven una lista vacía.
     * - Simplemente comprobamos que la lista no es null.
     */
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

    @Test
    void sellarParadaOK() {
    	// Crear un peregrino de prueba
        Peregrino peregrino = new Peregrino();
        peregrino.setId(1L);
        
        // Crear una parada de prueba
        Parada parada = new Parada();
        parada.setId(10L);
        
        // Definir la fecha del sellado (por ejemplo, hoy)
        LocalDate fecha = LocalDate.now();
        
        // Crear el objeto ParadaSellada a guardar y simular que se le asigna un ID (por ejemplo, 100L)
        ParadaSellada ps = new ParadaSellada(peregrino, parada, fecha);
        ps.setId(100L);
        
        // Configurar el mock: se simula que NO existe sellado previo (devuelve false)
        when(paradasSelladasRepo.existeSelladoEnFecha(1L, 10L, fecha)).thenReturn(false);
        // Al guardar, el mock retorna el objeto con ID asignado
        when(paradasSelladasRepo.save(any(ParadaSellada.class))).thenReturn(ps);
        
        // Llamada al método del servicio que guarda el sellado
        ParadaSellada resultado = paradaSelladaService.guardarParadaSellada(ps);
        
        // Validaciones: se espera que el resultado no sea null y tenga el ID asignado (100L)
        assertNotNull(resultado, "El sellado debería guardarse correctamente.");
        assertNotNull(resultado.getId(), "El sellado guardado debe tener un ID asignado.");
        assertEquals(100L, resultado.getId());
    }

    @Test
    void sellarParadaKO() {
    	// Crear un peregrino de prueba
        Peregrino peregrino = new Peregrino();
        peregrino.setId(2L);
        
        // Crear una parada de prueba
        Parada parada = new Parada();
        parada.setId(20L);
        
        // Definir la fecha del sellado
        LocalDate fecha = LocalDate.now();
        
        // Crear el objeto ParadaSellada (sin asignar ID, ya que no se guardará)
        ParadaSellada ps = new ParadaSellada(peregrino, parada, fecha);
        
        // Configurar el mock: se simula que YA existe un sellado previo (devuelve true)
        when(paradasSelladasRepo.existeSelladoEnFecha(2L, 20L, fecha)).thenReturn(true);
        
        // Llamada al método del servicio, se espera que retorne null (no se guarda duplicado)
        ParadaSellada resultado = paradaSelladaService.guardarParadaSellada(ps);
        
        // Validación: se espera que el resultado sea null
        assertNull(resultado, "No se debe guardar un sellado duplicado para el mismo peregrino, parada y fecha.");
    }
}
