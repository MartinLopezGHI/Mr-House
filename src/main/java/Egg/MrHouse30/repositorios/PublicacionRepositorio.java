/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.repositorios;

import Egg.MrHouse30.entidades.Publicacion;
import Egg.MrHouse30.enums.ProvinciaEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author facuq
 */
public interface PublicacionRepositorio extends JpaRepository<Publicacion, String> {

    @Query("SELECT u FROM Publicacion u WHERE u.id = :id")

    public Publicacion buscarPorID(@Param("id") String id);

    @Query("SELECT u FROM Publicacion u WHERE u.provincias = :provincias")
    List<Publicacion> buscarPorCiudad(@Param("provincias") String provincias);

    @Query("SELECT u FROM Publicacion u WHERE u.provincias = :provincias")
    public Publicacion buscarPorAlta(@Param("provincias") ProvinciaEnum provincias);

    @Query("SELECT p from Publicacion p WHERE p.alta = true")
    List<Publicacion> buscaActivos(@Param("activos") String activos);

    @Query("SELECT p FROM Publicacion p WHERE p.propiedadTipo = :tipoPropiedad AND p.transaccionPropiedad = :transaccionPropiedad AND p.provincias = :provincias AND p.precio BETWEEN :precioMin AND :precioMax")
    List<Publicacion> buscarPropiedades(@Param("tipoPropiedad") String tipoPropiedad, @Param("transaccionPropiedad") String transaccionPropiedad, @Param("provincias") String provincias, @Param("precioMin") Double precioMin, @Param("precioMax") Double precioMax);
}
