/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ao.co.culysoft.gestaodecontacto.model.dao;

import ao.co.culysoft.gestaodecontacto.model.modelo.Contacto;

/**
 *
 * @author quitumba.ferreira
 */
public class TesteDAO {
    
    public static void main(String[] args) {
        Contacto c = new Contacto();
        ContactoDAO dao = new ContactoDAO();
        
        c.setCodigo(0);
        c.setNome("Quitumba Ferreira");
        c.setTelefone("244 ");
        
        System.out.println("Message: " + dao.save(c));
    }
}
