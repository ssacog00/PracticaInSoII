/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;


import com.unileon.EJB.ListaDeseosFacadeLocal;
import com.unileon.EJB.ProductoFacadeLocal;
import com.unileon.modelo.ListaDeseos;
import com.unileon.modelo.Producto;
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

public class PantallaInicioClienteController implements Serializable{
    
    @EJB
    private ProductoFacadeLocal productoEJB;
    
    @EJB
    private ListaDeseosFacadeLocal deseosEJB;
    
    private List<Producto> listaProductos;
    private Producto productoSeleccionado;
    private ListaDeseos deseo;
    private Usuario us;
    private int enumValue;
    
    @PostConstruct
    public void init() {
        us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        listaProductos = productoEJB.findAll();
        productoSeleccionado = new Producto();
        deseo = new ListaDeseos();
        enumValue = 0;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public int getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(int enumValue) {
        this.enumValue = enumValue;
    }
    
    
    public void nuevoDeseo() {
        try {
            deseo.setCliente(us.getCliente());
            deseo.setProducto(productoSeleccionado);
            deseo.setValoracion(enumValue);
            
            deseosEJB.create(deseo);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Deseo registrado"));
        } catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
    
}
