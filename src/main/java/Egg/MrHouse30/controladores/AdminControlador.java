/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.controladores;

import Egg.MrHouse30.entidades.Usuario;
import Egg.MrHouse30.excepciones.MyException;
import Egg.MrHouse30.servicios.UsuarioServicios;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ramiro
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private UsuarioServicios usuarioServicios;

    @GetMapping("/dashboard")
    public String panelAdmin() {
        return "index.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> clientes = usuarioServicios.listarUsuarios();
        modelo.addAttribute("clientes", clientes);
        return "usuarios.html";
    }

    @GetMapping("/editarUsuario/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {
        modelo.put("cliente", usuarioServicios.getOne(id));
        return "editar.html";
    }

    @PostMapping("/editarUsuario/{id}")
    public String modificar(@PathVariable String id, String nombre, String email,
           String password, String password2, MultipartFile archivo, ModelMap modelo, boolean isInmobiliaria) {
        try {
            usuarioServicios.modificar(archivo, id, nombre, email, password, password2, isInmobiliaria);
            return "redirect:../usuarios";
        } catch (MyException ex) {
            modelo.put("error!", ex.getMessage());
            return "editar.html";
        }
    }

    @GetMapping("/eliminarUsuario/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {
        try {
            usuarioServicios.eliminarUsuario(id);
            return "redirect:../usuarios";
        } catch (MyException ex) {
            modelo.put("error!", ex.getMessage());
        }
        return "usuarios.html";
    }
}
