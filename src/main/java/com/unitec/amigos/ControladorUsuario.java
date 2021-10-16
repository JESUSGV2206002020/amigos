package com.unitec.amigos;
//Vamos america

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorUsuario {
    //AQUI VA UN METODO QUE REPRESENTA CADA UNO DE LOS ESTADOS QUE VAMOS A TRANSFERIR ES DECIR
    //VA EN UN GET, POST, PUT, DELETE COMO MINIMO.

    //AQUI VIENE YA EL USO DE LA INVERSION DE CONTROL
    @Autowired RepositorioUsuario repoUsuario;

    //IMPLEMENTAMOS EL CODIGO PARA GUARDAR UN USUARIO EN MONGODB
    @PostMapping("/usuario")
    public Estatus guardar(@RequestBody String json)throws  Exception{
        //PRIMERO LEEMOS Y CONVERTIMOS EL OBJETO JSON A OBJETO JAVA
        ObjectMapper mapper = new ObjectMapper();
        Usuario u=mapper.readValue(json,Usuario.class);

        //ESTE USUARIO, YA EN FORMATO JSON LO GUARDAMOS EN MONGODB
        repoUsuario.save(u);

        //CREAMOS UN OBJETO DE TIPO ESTATUS Y ESTE OBJETO LO RETORNAMOS AL CLIENTE(ANDROID O POSTMAN)
        Estatus estatus=new Estatus();
        estatus.setSuccess(true);
        estatus.setMensaje("¡Tu usuario se guardo con exito!");
        return estatus;
    }

    @GetMapping("/usuario/{id}")
        public Usuario obtenerPorId(@PathVariable String id)throws Exception{

        //LEEMOS UN USUARIO CON EL METODO FINDBYID PASANDOLE COMO ARGUMENTO EL ID(EMAIL) QUE QUEREMOS
        //APOYANDONOS DEL REPOUSUARIO
        Usuario u = repoUsuario.findById(id).get();
        return u;

    }
    //METODO PARA BUSCAR TODOS
    @GetMapping("/usuario")
    public List<Usuario> buscarTodos(){

        return repoUsuario.findAll();
    }

    //METODO PARA ACTUALIZAR
    @PutMapping("/usuario")
    public Estatus actualizar(@RequestBody String json)throws Exception{
        //PRIMEROS DEBEMOS VERIFICAR QUE EXISTE, POR LO TANTO PRIMERO LO BUSCAMOS
        ObjectMapper mapper=new ObjectMapper();
        Usuario u=mapper.readValue(json, Usuario.class);
        Estatus e=new Estatus();
        if(repoUsuario.findById(u.getEmail()).isPresent()){
            //LO VOLVEMOS A GUARDAR
            repoUsuario.save(u);
            e.setMensaje("Usuario se actualizo con exito");
            e.setSuccess(true);

        }else{
            e.setMensaje("Este usuario no exite, no se actualizara");
            e.setSuccess(false);
        }
        return e;
    }

    @DeleteMapping("/usuario/{id}")
    public Estatus borrar(@PathVariable String id){
        Estatus estatus=new Estatus();
        if(repoUsuario.findById(id).isPresent()){
            repoUsuario.deleteById(id);
            estatus.setMensaje("Usuario borrado con exito");
            estatus.setSuccess(true);
        }else{
            estatus.setMensaje("Este usuario no existe");
            estatus.setSuccess(false);
        }
        return estatus;
    }
}
