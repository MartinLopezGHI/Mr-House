/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.servicios;

import Egg.MrHouse30.entidades.Foto;
import Egg.MrHouse30.entidades.Publicacion;
import Egg.MrHouse30.excepciones.MyException;
import Egg.MrHouse30.repositorios.PublicacionRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Service
public class PublicacionServicios {

    @Autowired
    PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private FotoServicios fotoServicios;

    @Transactional
    public void publicar(MultipartFile archivo, Publicacion publicacionV) throws MyException {
        //P=PERSISTIR V=VALIDAR
        Publicacion propiedadP = validar(publicacionV);

        Foto foto = fotoServicios.save(archivo);
        propiedadP.setFoto(foto);
        publicacionRepositorio.save(propiedadP);
    }

    //PublicacionP = publicacion persistida  PublicacionC = publicacion con nuevos cambios
    @Transactional
    public void modificar(MultipartFile archivo, Publicacion publicacionC) throws MyException {
        try {
            Publicacion publicacionP = validarCambios(publicacionC, buscarPorId(publicacionC.getId()));

            if (archivo != null && !archivo.isEmpty()) {
                publicacionP.setFoto(fotoServicios.save(archivo));
            }

            publicacionRepositorio.save(publicacionP);
        } catch (MyException e) {
            throw new MyException("No se editó la propiedad");
        }
    }

    @Transactional
    public Publicacion buscarPorId(String id) throws MyException {
        Optional<Publicacion> op = publicacionRepositorio.findById(id);
        if (op.isPresent()) {
            return op.get();
        } else {
            throw new MyException("No se encontró la publicación solicitada.");

        }
    }

    @Transactional
    public void bajaPropiedad(Publicacion publicacion) throws MyException {

        Optional<Publicacion> op = publicacionRepositorio.findById(publicacion.getId());
        if (op.isPresent()) {
            Publicacion aux = op.get();
            aux.setAlta(false);
            publicacionRepositorio.save(aux);
        }
    }

    @Transactional
    public void altaPropiedad(Publicacion publicacion) throws MyException {

        Optional<Publicacion> op = publicacionRepositorio.findById(publicacion.getId());
        if (op.isPresent()) {
            Publicacion aux = op.get();
            aux.setAlta(false);
            publicacionRepositorio.save(aux);
        }
    }

    @Transactional
    public void eliminar(Publicacion publicacion) throws MyException {
        if (publicacion.getId() == null || publicacion.getId().isEmpty()) {
            throw new MyException("El id esta vacio o es nulo");
        }
        Optional<Publicacion> op = publicacionRepositorio.findById(publicacion.getId());
        if (op.isPresent()) {
            Publicacion aux = op.get();
            publicacionRepositorio.delete(aux);
        }
    }

    private Publicacion validar(Publicacion publicacion) throws MyException {
        publicacion.setAlta(true);
        if (publicacion.getPrecio() == null) {
            throw new MyException("El precio no puede ser nulo");
        }
        if (publicacion.getM2().isEmpty() || publicacion.getM2() == null) {
            throw new MyException("La cantidad de metros cuadrados no puede ser nula");
        }
        if (publicacion.getHabitaciones().isEmpty() || publicacion.getHabitaciones() == null) {
            throw new MyException("La cantidad de habitaciones no puede ser nula");
        }
        if (publicacion.getBanos().isEmpty() || publicacion.getBanos() == null) {
            throw new MyException("La cantidad de baños no puede ser nula");
        }
        if (publicacion.getDescripcion().isEmpty() || publicacion.getDescripcion() == null) {
            throw new MyException("La descripción no puede ser nula o estar vacía");
        }
        if (publicacion.getDireccion().isEmpty() || publicacion.getDireccion() == null) {
            throw new MyException("La dirección no puede ser nula o estar vacía");
        }
        if (publicacion.getProvincias() == null) {
            throw new MyException("La provincia no puede ser nula o estar vacía");
        }
        return publicacion;
    }

    //PublicacionP = publicacion persistida  PublicacionC = publicación con nuevos cambios
    private Publicacion validarCambios(Publicacion publicacionP, Publicacion publicacionC) throws MyException {

        if (publicacionC.getM2().equals(publicacionP.getM2())
                && publicacionC.getPrecio().equals(publicacionP.getPrecio())
                && publicacionC.getPropiedadTipo().equals(publicacionP.getPropiedadTipo())
                && publicacionC.getHabitaciones().equals(publicacionP.getHabitaciones())
                && publicacionC.getBanos().equals(publicacionP.getBanos())
                && publicacionC.getCochera().equals(publicacionP.getCochera())
                && publicacionC.getDireccion().equals(publicacionP.getDireccion())
                && publicacionC.getDescripcion().equals(publicacionP.getDescripcion())
                && publicacionC.getFoto().equals(publicacionP.getFoto())
                && publicacionC.getTransaccionPropiedad().equals(publicacionP.getTransaccionPropiedad())
                && publicacionC.getProvincias().equals(publicacionP.getProvincias())) {
            throw new MyException("No existen cambios para editar");
        }
        if (!publicacionC.getPrecio().equals(publicacionP.getPrecio())) {
            publicacionP.setPrecio(publicacionC.getPrecio());
        }
        if (!publicacionC.getPropiedadTipo().equals(publicacionP.getPropiedadTipo())) {
            publicacionP.setPropiedadTipo(publicacionC.getPropiedadTipo());
        }
        if (!publicacionC.getM2().equals(publicacionP.getM2())) {
            publicacionP.setHabitaciones(publicacionC.getHabitaciones());
        }
        if (!publicacionC.getHabitaciones().equals(publicacionP.getHabitaciones())) {
            publicacionP.setHabitaciones(publicacionC.getHabitaciones());
        }
        if (!publicacionC.getBanos().equals(publicacionP.getBanos())) {
            publicacionP.setBanos(publicacionC.getBanos());
        }
        if (!publicacionC.getCochera().equals(publicacionP.getCochera())) {
            publicacionP.setCochera(publicacionC.getCochera());
        }
        if (!publicacionC.getDireccion().equals(publicacionP.getDireccion())) {
            publicacionP.setDireccion(publicacionC.getDireccion());
        }

        if (!publicacionC.getDescripcion()
                .equals(publicacionP.getDescripcion())) {
            publicacionP.setDescripcion(publicacionC.getDescripcion());
        }

        if (!publicacionC.getFoto()
                .equals(publicacionP.getFoto())) {
            publicacionP.setFoto(publicacionC.getFoto());
        }

        if (!publicacionC.getProvincias().equals(publicacionP.getProvincias())) {
            publicacionP.setDireccion(publicacionC.getDireccion());
        }
        return publicacionP;
    }
}
