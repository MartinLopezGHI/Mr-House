/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.entidades;

import Egg.MrHouse30.enums.Cochera;
import Egg.MrHouse30.enums.PropiedadTipo;
import Egg.MrHouse30.enums.ProvinciaEnum;
import Egg.MrHouse30.enums.TransaccionPropiedad;
import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Ramiro
 */
@Entity
@Table(name = "publicacion")
public class Publicacion {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true)
    private Double precio;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PropiedadTipo propiedadTipo; //1=CASA 2=DEPARTAMENTO   
    private String m2;
    private String habitaciones;
    private String banos;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Cochera cochera;
    private String direccion;
    private String descripcion;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransaccionPropiedad transaccionPropiedad; //Tipo de transacción: Venta o Alquiler

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProvinciaEnum provincias;

    private boolean alta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne
    private Foto foto;

    public Publicacion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public PropiedadTipo getPropiedadTipo() {
        return propiedadTipo;
    }

    public void setPropiedadTipo(PropiedadTipo propiedadTipo) {
        this.propiedadTipo = propiedadTipo;
    }

    public String getM2() {
        return m2;
    }

    public void setM2(String m2) {
        this.m2 = m2;
    }

    public String getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(String habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getBanos() {
        return banos;
    }

    public void setBanos(String banos) {
        this.banos = banos;
    }

    public Cochera getCochera() {
        return cochera;
    }

    public void setCochera(Cochera cochera) {
        this.cochera = cochera;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TransaccionPropiedad getTransaccionPropiedad() {
        return transaccionPropiedad;
    }

    public void setTransaccionPropiedad(TransaccionPropiedad transaccionPropiedad) {
        this.transaccionPropiedad = transaccionPropiedad;
    }

    public ProvinciaEnum getProvincias() {
        return provincias;
    }

    public void setProvincias(ProvinciaEnum provincias) {
        this.provincias = provincias;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

}
