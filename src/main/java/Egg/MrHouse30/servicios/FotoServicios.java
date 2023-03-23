/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Egg.MrHouse30.servicios;

import Egg.MrHouse30.entidades.Foto;
import Egg.MrHouse30.excepciones.MyException;
import Egg.MrHouse30.repositorios.FotoRepositorio;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Service
public class FotoServicios {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional
    public Foto save(MultipartFile file) throws MyException {
        validar(file);
        try {
            Foto foto = new Foto();
            foto.setMime(file.getContentType());
            foto.setNombre(file.getName());
            foto.setContenido(file.getBytes());
            return fotoRepositorio.save(foto);

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            return null;
        }
    }

    @Transactional
    public Foto update(MultipartFile file, String idImagen) throws MyException {
        if (file != null) {
            try {

                Foto foto = new Foto();

                if (idImagen != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idImagen);

                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }

                foto.setMime(file.getContentType());

                foto.setNombre(file.getName());

                foto.setContenido(file.getBytes());

                return fotoRepositorio.save(foto);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }

    private void validar(MultipartFile file) throws MyException {
        if (null == file) {
            throw new MyException("La imágen no es válida.");
        }
    }

    @Transactional(readOnly = true)
    public List<Foto> listarTodos() {
        return fotoRepositorio.findAll();
    }
}
