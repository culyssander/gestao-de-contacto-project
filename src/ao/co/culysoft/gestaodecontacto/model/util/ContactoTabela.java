/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ao.co.culysoft.gestaodecontacto.model.util;

import ao.co.culysoft.gestaodecontacto.model.modelo.Contacto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author quitumba.ferreira
 */
public class ContactoTabela extends AbstractTableModel{
    private String [] colunas = {"Codigo","Nome","Idade","Telefone","Morada","Email","Data de Registo"};
    private List<Contacto> contactos;

    public ContactoTabela(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    @Override
    public int getRowCount() {
        return contactos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Contacto contacto = contactos.get(rowIndex);
        
        switch(columnIndex){
            case 0: return contacto.getCodigo();
            case 1: return contacto.getNome();
            case 2: return contacto.getIdade();
            case 3: return contacto.getTelefone();
            case 4: return contacto.getMorada();
            case 5: return contacto.getEmail();
            case 6: return contacto.getDataDeRegisto();
            default: return "";
        }
        
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column]; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; //To change body of generated methods, choose Tools | Templates.
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }
    
}
