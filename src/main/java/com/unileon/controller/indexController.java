/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;

import com.unileon.EJB.UsuarioFacadeLocal;
import com.unileon.modelo.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Sergio
 */
@Named
@ViewScoped

public class indexController implements Serializable{
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    private Usuario usuario;

    
    @PostConstruct
    public void inicio(){
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
     public String verificarUsuario(){
        
        Usuario usuarioAux = usuarioEJB.verificarUsuario(usuario); 
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioAux);

        if(usuarioAux!=null){
            System.out.println("Usuario recibido en el controlador");
            return "/privado/pantallaInicio.xhtml";
        } else {
            System.out.println("Usuario NULL en el controlador");
            return "/permisosInsuficientes.xhtml";
        }
         
    }
    
}
