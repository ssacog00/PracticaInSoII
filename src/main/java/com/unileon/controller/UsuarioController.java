/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;


import com.unileon.EJB.RolFacadeLocal;
import com.unileon.EJB.UsuarioFacadeLocal;
import com.unileon.modelo.Cliente;
import com.unileon.modelo.Encargado;
import com.unileon.modelo.Rol;
import com.unileon.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Sergio
 */
@Named
@ViewScoped

public class UsuarioController implements Serializable{
    
    @EJB
    private RolFacadeLocal rolEJB;
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    private Usuario user;
    private Cliente cliente;
    private Encargado encargado;
    private Rol rol;
    
    
    @PostConstruct
    public void inicio(){
        user = new Usuario();
        cliente = new Cliente();
        encargado = new Encargado();
        rol = new Rol();
    }
    

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Encargado getEncargado() {
        return encargado;
    }

    public void setEncargado(Encargado encargado) {
        this.encargado = encargado;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    
    public String createCliente() {
        try {
            user.setCliente(cliente);
            rol.setIdRol(2);
            user.setRol(rol);
            
            usuarioEJB.create(user);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Cliente registrado"));
            return "/index.xhtml";
        } catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
            return "";
        }
    }
    
    public String createCliente2() {
        try {
            user.setCliente(cliente);
            rol.setIdRol(2);
            user.setRol(rol);
            
            usuarioEJB.create(user);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Cliente registrado"));
            return "/privado/encargado/pantallaInicio.xhtml";
        } catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
            return "";
        }
    }
    
    public String createEncargado() {
        try {
            encargado.setProductosAniadidos(0);
            user.setEncargado(encargado);
            rol.setIdRol(1);
            user.setRol(rol);
            
            usuarioEJB.create(user);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Encargado registrado"));
            return "/privado/encargado/pantallaInicio.xhtml";
        } catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
            return "";
        }
    }
}
