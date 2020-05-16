/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;

import com.unileon.EJB.ProductoFacadeLocal;
import com.unileon.modelo.Producto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Sergio
 */
@Named
@ViewScoped

public class pantallaInicioClienteController implements Serializable{
    
    @EJB
    private ProductoFacadeLocal productoEJB;
    
    private List<Producto> listaProductos;
    private Producto productoSeleccionado;
    
    @PostConstruct
    public void init() {
        listaProductos = productoEJB.findAll();
        
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
        
        System.out.println("\n\nENTROOOOOOOO\n\n");
        System.out.println(this.productoSeleccionado.getNombre());
    }
    
    
    
}
