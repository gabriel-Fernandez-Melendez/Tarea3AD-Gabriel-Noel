package com.Gabriel.Noel.tarea3AD2024base.services;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.ServicioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosService {

	@Autowired
    private ServicioRepository servicioRepository;
    
    /**
     * Guarda un nuevo servicio en la base de datos.
     * @param servicio El servicio a guardar.
     * @return `true` si se guardó correctamente, `false` si hubo un error.
     */
    public boolean crearServicio(Servicio servicio) {
        if (servicio == null || servicio.getNombre().isEmpty() || servicio.getPrecio() == null || servicio.getPrecio() < 0) {
            System.out.println("Error: Datos del servicio inválidos.");
            return false;
        }
        servicioRepository.guardarServicio(servicio);
        return true;
    }

    /**
     * Obtiene todos los servicios almacenados en la base de datos.
     * @return Lista de servicios.
     */
    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepository.recogerServicios();
    }

    /**
     * Asigna paradas a un servicio específico.
     * @param servicioId ID del servicio a modificar.
     * @param idParadas Lista de IDs de paradas a asignar.
     * @return `true` si se asignaron correctamente, `false` en caso de error.
     */
    public boolean asignarParadasAServicio(Long servicioId, List<Long> idParadas) {
        if (servicioId == null || idParadas == null || idParadas.isEmpty()) {
            System.out.println("Error: Datos de asignación inválidos.");
            return false;
        }
        servicioRepository.asignarParadasAServicio(servicioId, idParadas);
        System.out.println("Parada asignada correctamente al servicio metodo asignarParadasAServicio en Service");
        return true;
    }
    
    
    public void cerrarConexion() {
        servicioRepository.cerrarConexion();
    }
}
