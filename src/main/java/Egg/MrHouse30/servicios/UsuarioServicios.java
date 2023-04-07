/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.servicios;

import Egg.MrHouse30.entidades.Foto;
import Egg.MrHouse30.entidades.Usuario;
import Egg.MrHouse30.enums.Roles;
import Egg.MrHouse30.excepciones.MyException;
import Egg.MrHouse30.repositorios.UsuarioRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ramiro
 */
@Service
public class UsuarioServicios implements UserDetailsService {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    FotoServicios fotoservicios;

    //Crear Usuario
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2, Roles rol, boolean isInmobiliaria) throws MyException {

        validar(nombre, email, password, password2, archivo, isInmobiliaria);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(rol != null ? rol : Roles.USUARIO); // Asignar el rol por defecto si no se recibió un valor
        Foto foto = fotoservicios.save(archivo);
        usuario.setFoto(foto);

        usuarioRepositorio.save(usuario);
    }

    private void validar(String nombre, String email, String password, String password2, MultipartFile archivo, boolean isInmobiliaria) throws MyException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("El nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MyException("El email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacia , ni tener menos de 6 caracteres");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas deben ser iguales");
        }
        if (archivo == null) {
            throw new MyException("La foto no puede ser nula");
        }
        try {
            if (archivo.getBytes() == null || archivo.getBytes().length == 0) {
                throw new MyException("La foto debe tener contenido");
            }
        } catch (IOException ex) {
            throw new MyException("Error al leer el archivo de imagen: " + ex.getMessage());
        }
        if (!(isInmobiliaria == true || isInmobiliaria == false)) {
            throw new MyException("Debe específicar si desea crear una cuenta de tipo inmobiliaria o usuario por defecto.");
        }

    }

    @Transactional
    public void modificar(MultipartFile archivo, String id,
            String nombre, String email,
            String password, String password2, boolean isInmobiliaria) throws MyException {

        validar(nombre, email, password, password2, archivo, isInmobiliaria);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            usuario.setPassword(encriptada);
            String idImagen = null;
            if (usuario.getFoto() != null) {
                idImagen = usuario.getFoto().getId();
            }
            Foto foto = fotoservicios.update(archivo, idImagen);
            usuario.setFoto(foto);

            usuarioRepositorio.save(usuario);
        } else {
            throw new MyException("No se encontró el usuario solicitado");
        }
    }
    //Metodo para listar clientes

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    //Metodo GetOne para editar
    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }

    @Transactional
    public void eliminarUsuario(String id) throws MyException {
        if (id == null || id.isEmpty()) {
            throw new MyException("El id esta vacio o es nulo");
        }
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuarioRepositorio.delete(usuario);
        }
    }

    //Método para cargar por nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        } else {
            return null;
        }
    }

}
