/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.repositorios;

import Egg.MrHouse30.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ramiro
 */
@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {

    @Query("SELECT i FROM Foto i WHERE i.id = :id")
    Foto searchById(@Param("id") String id);
}
