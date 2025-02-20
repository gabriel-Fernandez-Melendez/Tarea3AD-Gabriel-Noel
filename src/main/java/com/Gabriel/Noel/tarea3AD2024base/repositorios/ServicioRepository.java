package com.Gabriel.Noel.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

import com.Gabriel.Noel.tarea3AD2024base.dataCon.DataConnection;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Servicio;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;


@Repository
public class ServicioRepository {

    private  ObjectContainer db;

    
    public ServicioRepository()
    {
    	this.db = DataConnection.getInstance();
    }
    
    
    
    /**
     * Obtiene todos los servicios almacenados en la base de datos.
     * 
     * @return Lista de servicios disponibles.
     */
    public List<Servicio> obtenerTodosLosServicios() {
        return new ArrayList<>(db.query(Servicio.class));
    }

    /**
     * Guarda un nuevo servicio en la base de datos.
     * 
     * @param servicio Objeto Servicio a almacenar.
     */
    public void guardarServicio(Servicio servicio) {
        try {
            db.store(servicio);
            db.commit();
            System.out.println("Servicio guardado en DB4O: " + servicio);
        } catch (Exception e) {
            System.out.println("Error al guardar el servicio: " + e.getMessage());
        }
    }

    /**
     * Asigna paradas a un servicio determinado evitando duplicados.
     * 
     * @param servicioId     ID del servicio al que se le asignarán paradas.
     * @param nuevasParadas  Lista de IDs de las nuevas paradas a asignar.
     */
    public void asignarParadasAServicio(Long servicioId, List<Long> nuevasParadas) {
        try {
            List<Servicio> servicios = db.query(Servicio.class);

            for (Servicio servicio : servicios) {
                if (servicio.getId().equals(servicioId)) {

                    // Evitar duplicados antes de asignar paradas
                    for (Long idParada : nuevasParadas) {
                        if (!servicio.getIdParada().contains(idParada)) {
                            servicio.getIdParada().add(idParada);
                        }
                    }

                    // Guardar cambios en la base de datos
                    db.store(servicio);
                    db.commit();
                    System.out.println("Servicio actualizado en DB4O: " + servicio);
                    return;
                }
            }
            System.out.println("No se encontró el servicio con ID: " + servicioId);
        } catch (Exception e) {
            System.out.println("Error al asignar paradas: " + e.getMessage());
        }
    }


    public boolean actualizarServicio(Servicio servicio) {
        try {
            // Buscar el servicio en la base de datos
            List<Servicio> servicios = db.query(Servicio.class);

            for (Servicio s : servicios) {
                if (s.getId().equals(servicio.getId())) {
                    // Actualizar los datos
                    s.setNombre(servicio.getNombre());
                    s.setPrecio(servicio.getPrecio());
                    s.setIdParada(new ArrayList<>(servicio.getIdParada()));

                    // Guardar en la base de datos
                    db.store(s);
                    db.commit();
                    System.out.println("Servicio actualizado en DB4O: " + s);
                    return true;
                }
            }

            System.out.println("No se encontró el servicio con ID: " + servicio.getId());
            return false;

        } catch (Exception e) {
            System.out.println("Error al actualizar el servicio: " + e.getMessage());
            return false;
        }
    }
    
    

    /**
     * Cierra la conexión con la base de datos DB4O.
     */
    public void cerrarConexion() {
        db.close();
        System.out.println("Conexión a DB4O cerrada correctamente.");
    }
}
