/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.repositorios;

import Egg.MrHouse30.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ramiro
 */
@Repository
public interface UsuarioRepositorio extends  JpaRepository<Usuario, String> {
    
    @Query("SELECT a FROM Usuario a WHERE a.id = :id")
    public Usuario buscarPorID(@Param("id") String id);
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    
    public Usuario buscarPorEmail(@Param("email") String email); 
    
}
