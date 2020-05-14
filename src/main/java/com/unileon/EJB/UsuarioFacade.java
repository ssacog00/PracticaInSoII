/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unileon.EJB;

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
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "practicainsoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
     @Override
    public Usuario verificarUsuario(Usuario us) {
        
        String consulta = "FROM Usuario u WHERE u.user=:param1 and u.password=:param2";
        
        Query query = em.createQuery(consulta);
        
        query.setParameter("param1", us.getUser());
        query.setParameter("param2", us.getPassword());
        
        List<Usuario> resultado = query.getResultList();
        
        if(resultado.isEmpty()){
            System.out.println("Usuario no encontrado");
            return null;
        }
            
        else{
            System.out.println("Usuario SI encontrado");
            return resultado.get(0);
        }       
    }
}
