/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.controladores;

import Egg.MrHouse30.entidades.Usuario;
import Egg.MrHouse30.enums.Roles;
import Egg.MrHouse30.excepciones.MyException;
import Egg.MrHouse30.servicios.UsuarioServicios;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ramiro
 */
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicios usuarioServicios;

    @GetMapping("/portal")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam Roles rol, ModelMap modelo, MultipartFile archivo, boolean isInmobiliaria) {
        try {
            usuarioServicios.registrar(archivo, nombre, email, password, password2, rol, isInmobiliaria);
            modelo.put("exito", "Usuario registrado correctamente");
            return "ingreso.html";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }
    }

    @GetMapping("/ingresar")
    public String login(@RequestParam(required = false) String error, ModelMap modelo
    ) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidas");
        }
        return "ingreso.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN','ROLE_PROPIETARIO','ROLE_INMOBILIARIA')")
    @GetMapping("/portal/inicio")
    public String inicio(HttpSession session) {
        Usuario logueadoC = (Usuario) session.getAttribute("usuariosession");
        if (logueadoC.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "index.html";
    }

    @GetMapping("/publicar")
    public String publicar() {
        return "publicar.html";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto.html";
    }

    @GetMapping("/propiedades")
    public String propiedades() {
        return "propiedades.html";
    }

    @GetMapping("/inmobiliarias")
    public String inmobiliarias() {
        return "inmobiliarias.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN','ROLE_PROPIETARIO','ROLE_INMOBILIARIA')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "perfil.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN','ROLE_PROPIETARIO','ROLE_INMOBILIARIA')")
    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile file, @PathVariable String id, @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo, boolean isInmobiliaria) {

        try {
            usuarioServicios.modificar(file, id, nombre, email, password, password2, isInmobiliaria);

            modelo.put("exito", "Usuario actualizado correctamente!");

            return "inicio.html";
        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "perfil.html";
        }

    }
}
