package com.Gabriel.Noel.tarea3AD2024base.services;

import com.Gabriel.Noel.tarea3AD2024base.modelo.EnvioACasa;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.EnvioACasaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Servicio que gestiona los envíos a casa.
 */
@Service
public class EnvioACasaService {

    private final EnvioACasaRepository envioRepository = new EnvioACasaRepository();

    /**
     * Registra un nuevo envío en la base de datos.
     * @param
     */
    public void registrarEnvio(EnvioACasa envio) 
    {
        envioRepository.guardarEnvio(envio);
    }

    /**
     * Obtiene la lista de todos los envíos registrados.
     * @return
     */
    public List<EnvioACasa> obtenerTodosLosEnvios() 
    {
        return envioRepository.obtenerTodosLosEnvios();
    }

    /**
     * Obtiene los envíos realizados desde una parada específica.
     * @param
     * @return
     */
    public List<EnvioACasa> obtenerEnviosPorParada(Long idParada) 
    {
        return envioRepository.obtenerEnviosPorParada(idParada);
    }

    
//    public void eliminarEnvio(Long id) {
//        envioRepository.eliminarEnvio(id);
//    }
}
