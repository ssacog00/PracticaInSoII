/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.controller;

import com.unileon.EJB.MenuFacadeLocal;
import com.unileon.modelo.Menu;
import com.unileon.modelo.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Sergio
 */
@Named
@SessionScoped

public class MenuController implements Serializable{
    
    @EJB
    private MenuFacadeLocal menuEJB;
    
    private List<Menu> listaMenusDisponibles;
    private Usuario usuario;
    private MenuModel modelo = new DefaultMenuModel();;
    
    
    @PostConstruct
    public void inicio(){
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        listaMenusDisponibles = menuEJB.obtenerMenusUsuario(usuario);
        
        obtenerMenu();
    }
    
    public MenuModel obtenerMenu(){
        
        System.out.println("\n\nCARGANDO MENU\n\n");
        
        for (Iterator<Menu> iterator = listaMenusDisponibles.iterator(); iterator.hasNext();) {
            Menu menuActual = iterator.next();
            
            if(menuActual.getTipo().equals("I")){
                DefaultMenuItem item = new DefaultMenuItem(menuActual.getNombre());
                
                if(menuActual.getUrl()!=null){
                    item.setUrl(menuActual.getUrl());
                    System.out.println("ITEM ("+menuActual.getNombre()+") url: "+menuActual.getUrl());
                } else {
                    item.setUrl("/");
                    System.out.println("ITEM ("+menuActual.getNombre()+") url: NULL");
                }
                    
                
                if(menuActual.getMenu() == null){
                    modelo.addElement(item);
                }
            }
            
            if(menuActual.getTipo().equals("S")){
                DefaultSubMenu subMenu = new DefaultSubMenu(menuActual.getNombre());
                System.out.println("SUBMENU ("+menuActual.getNombre()+") url: "+menuActual.getUrl());
                
                //Buscar los hijos
                for(Menu m:listaMenusDisponibles){
                    //Recorremos otra vez toda la lista
                    if(m.getMenu()!=null){
                        //Tiene padre
                        if(m.getMenu().getIdMenu() == menuActual.getIdMenu()){
                            //Ademas el id de ese menu corresponde con el id del actual ==> ES HIJO
                            DefaultMenuItem item = new DefaultMenuItem(m.getNombre());
                            
                            if(m.getUrl()!=null){
                                item.setUrl(m.getUrl());
                                System.out.println("ITEM ("+m.getNombre()+") url: "+m.getUrl());
                            } else {
                                item.setUrl("/");
                                System.out.println("ITEM ("+m.getNombre()+") url: NULL");
                            }
                            subMenu.addElement(item);
                        }
                    }
                }
                
                modelo.addElement(subMenu);
            }
        }
        
        return modelo;
    }

    public void destruirSesion() throws IOException {
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("./../../");
    }
    
    
    public MenuModel getModelo() {
        return modelo;
    }

    public void setModelo(MenuModel modelo) {
        this.modelo = modelo;
    }
}
