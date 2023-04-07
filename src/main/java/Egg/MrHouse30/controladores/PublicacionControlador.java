/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.controladores;

import Egg.MrHouse30.entidades.Publicacion;
import Egg.MrHouse30.enums.Cochera;
import Egg.MrHouse30.enums.PropiedadTipo;
import Egg.MrHouse30.enums.ProvinciaEnum;
import Egg.MrHouse30.enums.TransaccionPropiedad;
import Egg.MrHouse30.excepciones.MyException;
import Egg.MrHouse30.servicios.PublicacionServicios;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Controller
@RequestMapping("/publicaciones")
public class PublicacionControlador {

    @Autowired
    private PublicacionServicios publicacionServicios;

    @GetMapping("/")
    public String propiedades() {
        return "propiedades.html"; //CAMBIAR EL NOMBRE DE LA VISTA A "PUBLICACIONES"
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_INMOBILIARIA')")
    @GetMapping("/publicar")
    public String publicar(ModelMap modelo) {
        modelo.addAttribute("provincias", ProvinciaEnum.values());
        modelo.addAttribute("transaccionPropiedad", TransaccionPropiedad.values());
        modelo.addAttribute("propiedadTipo", PropiedadTipo.values());
        modelo.addAttribute("cochera", Cochera.values());
        return "publicar.html";
    }

    @PostMapping("/publicado")
    public String registro(@ModelAttribute("provincias") ProvinciaEnum provincias,
            @ModelAttribute("transaccionPropiedad") TransaccionPropiedad transaccionPropiedad,
            @RequestParam Double precio, @ModelAttribute("propiedadTipo") PropiedadTipo propiedadTipo,
            @RequestParam String m2, @RequestParam String habitaciones, @RequestParam String banos,
            @ModelAttribute("cochera") Cochera cochera, @RequestParam String direccion,
            @RequestParam String descripcion, ModelMap modelo,
            @RequestParam("archivo") MultipartFile archivo) throws MyException {

        Publicacion publicacionV = new Publicacion();
        publicacionV.setPrecio(precio);
        publicacionV.setPropiedadTipo(propiedadTipo);
        publicacionV.setM2(m2);
        publicacionV.setHabitaciones(habitaciones);
        publicacionV.setBanos(banos);
        publicacionV.setCochera(cochera);
        publicacionV.setDireccion(direccion);
        publicacionV.setDescripcion(descripcion);
        publicacionV.setTransaccionPropiedad(transaccionPropiedad);
        publicacionV.setProvincias(provincias);
        publicacionServicios.publicar(archivo, publicacionV);
        try {
            publicacionServicios.publicar(archivo, publicacionV);
            modelo.put("exito", "La publicación se realizó correctamente");
            return "propiedades.html";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("precio", precio);
            modelo.put("propiedadTipo", propiedadTipo);
            modelo.put("m2", m2);
            modelo.put("habitaciones", habitaciones);
            modelo.put("banos", banos);
            modelo.put("cochera", cochera);
            modelo.put("direccion", direccion);
            modelo.put("descripcion", descripcion);
            modelo.put("provincias", provincias);
            modelo.put("transaccionPropiedad", transaccionPropiedad);
            return "publicar.html";
        }
    }
}
