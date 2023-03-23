/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.entidades;

import Egg.MrHouse30.enums.Roles;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Ramiro
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private String email;
    private String password;

    @Column(name = "is_inmobiliaria")
    private boolean isInmobiliaria;

    @Enumerated(EnumType.STRING)
    private Roles rol;

    @OneToMany(mappedBy = "usuario")
    private List<Publicacion> posts;

    @OneToOne
    private Foto foto;

    public Usuario() {
    }

    public boolean isIsInmobiliaria() {
        return isInmobiliaria;
    }

    public void setIsInmobiliaria(boolean isInmobiliaria) {
        this.isInmobiliaria = isInmobiliaria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public List<Publicacion> getPosts() {
        return posts;
    }

    public void setPosts(List<Publicacion> posts) {
        this.posts = posts;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

}
