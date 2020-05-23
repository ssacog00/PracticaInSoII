/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;

import com.unileon.EJB.ListaDeseosFacadeLocal;
import com.unileon.modelo.ListaDeseos;
import com.unileon.modelo.Producto;
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

public class ListaDeseosController implements Serializable{
    
    @EJB
    private ListaDeseosFacadeLocal deseosEJB;
    
    private List<ListaDeseos> listaDeseos;
    
    
    @PostConstruct
    public void init() {
        listaDeseos = deseosEJB.findAll();
    }

    public List<ListaDeseos> getListaDeseos() {
        return listaDeseos;
    }

    public void setListaDeseos(List<ListaDeseos> listaDeseos) {
        this.listaDeseos = listaDeseos;
    }
    
    
    public void eliminarDeseo(ListaDeseos deseo){
        
        try {
            for(ListaDeseos d:listaDeseos){
                if(d.getIdDeseos() == deseo.getIdDeseos()){
                    deseo = d;
                    break;
                }
            }
            deseosEJB.remove(deseo);
            this.actualizarTabla();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Deseo eliminado"));
            
        } catch(Exception e) {
            System.out.println("Error al eliminar deseo: "+e.getMessage());
        }
        
    } 
    
    private void actualizarTabla() {
        listaDeseos = deseosEJB.findAll();
    }
    
    public void aniadirCarrito(ListaDeseos deseo){
        List<Producto> listaAux =  (List<Producto>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaCarrito");
        listaAux.add(deseo.getProducto());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaCarrito", listaAux);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto añadido al carrito de la compra"));
    }
}