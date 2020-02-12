/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ao.co.culysoft.gestaodecontacto.model.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author quitumba.ferreira
 */
public class Conexao {
    private static Connection conectar;
    
    public static Connection obterConexao(){
        try {            
            conectar = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestao-de-contacto?useTimezone=true&serverTimezone=GMT", "root", "1234");
        } catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
            e.printStackTrace();
        }
        return conectar;
    }
    
    public static void main(String[] args) {
        obterConexao();
    }
}
