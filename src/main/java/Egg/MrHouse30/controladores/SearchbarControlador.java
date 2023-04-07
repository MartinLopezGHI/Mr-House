package Egg.MrHouse30.controladores;

import Egg.MrHouse30.entidades.Publicacion;
import Egg.MrHouse30.repositorios.PublicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * @author Ramiro
 */
@Controller
public class SearchbarControlador {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @GetMapping("/buscarpropiedades")
    public String index() {
        return "index";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam("tipoPropiedad") String tipoPropiedad, @RequestParam("transaccionPropiedad") String transaccionPropiedad, @RequestParam("provincia") String provincia, @RequestParam("precioMin") Double precioMin, @RequestParam("precioMax") Double precioMax, Model modelo) {
        List<Publicacion> publicacion = publicacionRepositorio.buscarPublicaciones(tipoPropiedad, transaccionPropiedad, provincia, precioMin, precioMax);
        modelo.addAttribute("propiedades", publicacion);
        return "propiedades";
    }
}
