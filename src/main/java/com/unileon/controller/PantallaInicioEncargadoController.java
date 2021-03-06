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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sergio
 */
@Named
@ViewScoped

public class PantallaInicioEncargadoController implements Serializable{
    
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
    }
    
    public void modificarProducto() {
        try {
            String nuevoNombre = productoSeleccionado.getNombre();
            String nuevaDescripcion = productoSeleccionado.getDescripcion();
            Float nuevoPrecio = productoSeleccionado.getPrecio();
            
            for(Producto p:listaProductos){
                if(p.getIdProducto() == productoSeleccionado.getIdProducto()){
                    productoSeleccionado = p;
                    productoSeleccionado.setNombre(nuevoNombre);
                    productoSeleccionado.setDescripcion(nuevaDescripcion);
                    productoSeleccionado.setPrecio(nuevoPrecio);
                    break;
                }
            }
            productoEJB.edit(productoSeleccionado);
            this.actualizarTabla();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto modificado con exito"));
        } catch(Exception e) {
            System.out.println("Error al modificar producto: "+e.getMessage());
        }
    }
    
    public void actualizarTabla() {
        listaProductos = productoEJB.findAll();
    }
}
