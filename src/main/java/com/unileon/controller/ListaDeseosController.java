/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;

import com.unileon.EJB.ListaDeseosFacadeLocal;
import com.unileon.modelo.ListaDeseos;
import com.unileon.modelo.Producto;
import com.unileon.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
    
    private List<ListaDeseos> listaDeseos = new ArrayList<>();
    
    
    @PostConstruct
    public void init() {
        List<ListaDeseos> listaDeseosAux = deseosEJB.findAll();
        this.filtrarLista(listaDeseosAux);
    }
    
    private void filtrarLista(List<ListaDeseos> listaAux) {
        
        Usuario usAux = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        for(ListaDeseos d:listaAux) {
            if(usAux.getCliente().getIdCliente() == d.getCliente().getIdCliente()) {
                listaDeseos.add(d);
            }
        }
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
                    System.out.println("\n\n\nDESEO ENCONTRADO\n\n");
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
        Usuario usAux = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        listaDeseos = new ArrayList<>();
        List<ListaDeseos> listaDeseosAux = deseosEJB.findAll();
        
        for(ListaDeseos d:listaDeseosAux) {
            if(usAux.getCliente().getIdCliente() == d.getCliente().getIdCliente()) {
                listaDeseos.add(d);
            }
        }
    }
    
    public void aniadirCarrito(ListaDeseos deseo){
        List<Producto> listaAux =  (List<Producto>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaCarrito");
        listaAux.add(deseo.getProducto());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaCarrito", listaAux);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto añadido al carrito de la compra"));
    }
}