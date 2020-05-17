/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.EJB;

import com.unileon.modelo.Menu;
import com.unileon.modelo.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sergio
 */
@Stateless
public class MenuFacade extends AbstractFacade<Menu> implements MenuFacadeLocal {

    @PersistenceContext(unitName = "practicainsoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(Menu.class);
    }
    
    @Override
    public List<Menu> obtenerMenusUsuario(Usuario us) {
                
        String consulta = "FROM Menu m WHERE m.rol.idRol=:param1";
        
        Query query = em.createQuery(consulta);
        query.setParameter("param1", us.getRol().getIdRol());
        
        List<Menu> resultado = query.getResultList();
        
        if(resultado.isEmpty()){
            System.out.println("No se han encontrado Menus para el usuario");
            return null;
        }
            
        else{
            System.out.println("Se han enviado Menus para el usuario");
            for(Menu m:resultado){
                System.out.println("\tNombre Menu: "+m.getNombre());
            }
            return resultado;
        }
    }
    
}
