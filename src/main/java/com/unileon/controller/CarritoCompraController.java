/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;

import com.unileon.EJB.LineaVentaFacadeLocal;
import com.unileon.EJB.VentaFacadeLocal;
import com.unileon.modelo.Producto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Sergio
 */
@Named
@SessionScoped

public class CarritoCompraController implements Serializable{
    
    @EJB
    private VentaFacadeLocal ventaEJB;
    
    @EJB
    private LineaVentaFacadeLocal lineaVentaEJB;
    
    private List<Producto> listaProductos = new ArrayList<>();

    @PostConstruct
    public void inicio(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaCarrito", listaProductos);
    }
    
    
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
    
    public void quitarProducto(Producto producto){
        List<Producto> listaAux =  (List<Producto>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaCarrito");
        
        for(Producto p:listaAux) {
            if(p.getIdProducto()==producto.getIdProducto()){
                producto = p;
                break;
            }
        }
        listaAux.remove(producto);
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaCarrito", listaAux);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto eliminado del carrito"));    }
}
