package com.unitec.amigos;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorHola {

    //ESTE PRIMER RECURSO ES TU HOLA MUNDO DE UN SERVICIO REST QUE USA EL METODO GET
    @GetMapping("/hola")
    public String saludar(){

      return "Hola Desde Mi Primer Servicio REST";
    }
}
