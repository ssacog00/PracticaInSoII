/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;


import com.unileon.EJB.ClienteFacadeLocal;
import com.unileon.EJB.RolFacadeLocal;
import com.unileon.EJB.UsuarioFacadeLocal;
import com.unileon.modelo.Cliente;
import com.unileon.modelo.Encargado;
import com.unileon.modelo.Rol;
import com.unileon.modelo.Usuario;
import java.io.Serializable;
import java.util.Date;
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
    
    @EJB
    private ClienteFacadeLocal clienteEJB;
    
    private List<Cliente> listaClientes;
    
    
    @PostConstruct
    public void inicio(){
        user = new Usuario();
        cliente = new Cliente();
        encargado = new Encargado();
        rol = new Rol();
        listaClientes = clienteEJB.findAll();
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

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
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
    
    
    public String modificarCliente() {
        
        try {
            
            String nuevoNombre = cliente.getNombre();
            String nuevaApellido1 = cliente.getApellido1();
            String nuevoApelllido2 = cliente.getApellido2();
            String nuevoSexo = cliente.getSexo();
            Date nuevaFecha = cliente.getFechaNacimiento();

            for(Cliente c:listaClientes){
                if(c.getIdCliente() == cliente.getIdCliente()){
                    cliente = c;
                    cliente.setNombre(nuevoNombre);
                    cliente.setApellido1(nuevaApellido1);
                    cliente.setApellido2(nuevoApelllido2);
                    cliente.setSexo(nuevoSexo);
                    cliente.setFechaNacimiento(nuevaFecha);
                    
                    break;
                }
            }
            clienteEJB.edit(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Cliente modificado con exito"));

            return "/privado/encargado/pantallaInicio.xhtml";
               
        } catch(Exception e) {
            System.out.println("Error al modificar cliente: "+e.getMessage());
            return "";
        }
    }
}
