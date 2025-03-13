package com.Gabriel.Noel.tarea3AD2024base.services;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa.ServicioRepository;

import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con los servicios en la base de datos DB4O.
 */
@Service
public class ServiciosService {

    private final ServicioRepository servicioRepository;

    /**
     * Constructor del servicio que inyecta el repositorio de servicios.
     * 
     * @param servicioRepository Repositorio de servicios para manejar la persistencia.
     */
    public ServiciosService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    /**
     * Obtiene la lista de todos los servicios almacenados en la base de datos.
     * 
     * @return Lista de objetos Servicio.
     */
    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepository.obtenerTodosLosServicios();
    }

    /**
     * Guarda un nuevo servicio en la base de datos.
     * 
     * @param servicio Objeto Servicio a almacenar.
     */
    public void crearServicio(Servicio servicio) {
        servicioRepository.guardarServicio(servicio);
    }

    /**
     * Asigna una lista de paradas a un servicio específico, evitando duplicados.
     * 
     * @param servicioId ID del servicio al que se le asignarán las paradas.
     * @param idParadas  Lista de IDs de las paradas a asignar.
     */
    public void asignarParadasAServicio(Long servicioId, List<String> nombreParadas) {
        servicioRepository.asignarParadasAServicio(servicioId, nombreParadas);
    }
    
    
    /**
     * Actualiza los datos de un servicio existente.
     *
     * @param servicio Servicio con los datos modificados.
     * @return `true` si se actualizó correctamente, `false` en caso de error.
     */
    public boolean actualizarServicio(Servicio servicio) {
        return servicioRepository.actualizarServicio(servicio);
    }

    /**
     * Cierra la conexión con la base de datos DB4O.
     */
    public void cerrarConexion() {
        servicioRepository.cerrarConexion();
    }
}
