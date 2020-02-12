/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ao.co.culysoft.gestaodecontacto.control.controller;

import ao.co.culysoft.gestaodecontacto.model.dao.ContactoDAO;
import ao.co.culysoft.gestaodecontacto.model.modelo.Contacto;
import ao.co.culysoft.gestaodecontacto.model.util.ContactoTabela;
import ao.co.culysoft.gestaodecontacto.view.formulario.TelaPrincipal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author quitumba.ferreira
 */
public class ContactoController implements ActionListener, MouseListener, KeyListener{
    private Contacto contacto;
    private ContactoDAO contactoDAO;
    private TelaPrincipal telaPrincipal;
    private ContactoTabela contactoTabela;
    private final Color SUCESSO = new Color(51, 255, 0);
    private final Color ERROR = new Color(255, 0, 0);
    private final Color PADRAO = new Color(102, 153, 255);
    

    public ContactoController(TelaPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        contactoDAO = new ContactoDAO();
        preencherTabelaContacto(contactoDAO.allContactos());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand().toLowerCase();
        
        switch(action) {
            case "add": add();
                break;
            case "edit": edit();
                break;
            case "remove": remove();
                break;
            case "save": save();
                break;
            case "cancel": cancel();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        String filter = telaPrincipal.getTxtFilterContacto().getText();
        if(!filter.equals("") || !filter.isEmpty()){
            preencherTabelaContacto(contactoDAO.filterContactosFromNomeOrTelefoneOrMorada(filter));
        }else{
            preencherTabelaContacto(contactoDAO.allContactos());
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        int linhaSelecionada = telaPrincipal.getTabelaContacto().getSelectedRow();
        contacto = contactoTabela.getContactos().get(linhaSelecionada);
        messageTelaPrincipal("LISTA DOS CONTACTOS", 2);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    private void add() {
        String textoNaTela = "Cadastro de contacto";
        message(textoNaTela, 2);
        showTelaRegistoContacto(true);
        //clean();
    }

    private void showTelaRegistoContacto(boolean opcao) {
        telaPrincipal.getDialogRegistoContacto().pack();
        telaPrincipal.getDialogRegistoContacto().setLocationRelativeTo(null);
        telaPrincipal.getDialogRegistoContacto().setVisible(opcao);
    }
    
    private void clean() {
        telaPrincipal.getTxtContactoCodigo().setText("0");
        telaPrincipal.getTxtContactoNome().setText("");
        telaPrincipal.getTxtContactoDataDeNascimento().setDate(null);
        telaPrincipal.getTxtContactoTelefone().setText("(244) ");
        telaPrincipal.getTxtContactoMorada().setText("");
        telaPrincipal.getTxtContactoEmail().setText("");
        contacto = null;
    }

    private void cancel() {
        showTelaRegistoContacto(false);
        clean();
    }

    private void save() {
        contacto = pegarOsValoresDosCampos(new Contacto());
        if (contacto != null) {
            String save = contactoDAO.save(contacto);
            if (save.startsWith("Contacto adicionado") || save.startsWith("Contacto editado")) {
                message(save, 1);
                preencherTabelaContacto(contactoDAO.allContactos());
                clean();
            } else {
                message(save, 0);
            }
        }
    }
    
    private boolean validaCampo(String texto) {
        if(!texto.equals("") || !texto.isEmpty()){
            return true;
        }
        message("Deves preencher o campo Nome", 0);
        return false;
    }
    
    private boolean validaCampoTelefone(String texto) {
        if(!texto.equals("(244) ") && !texto.isEmpty()){
            return true;
        }
        message("Deves preencher o campo telefone", 0);
        return false;
    }
    
    private Contacto pegarOsValoresDosCampos(Contacto c) {
        c.setCodigo(new Integer(telaPrincipal.getTxtContactoCodigo().getText()));
        c.setNome(telaPrincipal.getTxtContactoNome().getText());
        c.setDataDeNascimento(telaPrincipal.getTxtContactoDataDeNascimento().getDate());
        c.setTelefone(telaPrincipal.getTxtContactoTelefone().getText());
        c.setMorada(telaPrincipal.getTxtContactoMorada().getText());
        c.setEmail(telaPrincipal.getTxtContactoEmail().getText());  
        c.setDataDeRegisto(""+new Date());
        
        if(validaCampo(c.getNome()) && validaCampoTelefone(c.getTelefone())){
            return c;
        }
        return null;
    }
    
    private void message(String message, int erro) {
        switch (erro) {
            case 0:
                telaPrincipal.getLabelRegistoContacto().setBackground(ERROR);
                telaPrincipal.getLabelRegistoContacto().setText(message);
                break;
            case 1:
                telaPrincipal.getLabelRegistoContacto().setBackground(SUCESSO);
                telaPrincipal.getLabelRegistoContacto().setText(message);
                break;
            default:
                telaPrincipal.getLabelRegistoContacto().setBackground(PADRAO);
                telaPrincipal.getLabelRegistoContacto().setText(message);
                break;
        }
    }
 
    private void messageTelaPrincipal(String message, int erro) {
        switch (erro) {
            case 0:
                telaPrincipal.getLabelTelaPrincipal().setBackground(ERROR);
                telaPrincipal.getLabelTelaPrincipal().setText(message);
                break;
            case 1:
                telaPrincipal.getLabelTelaPrincipal().setBackground(SUCESSO);
                telaPrincipal.getLabelTelaPrincipal().setText(message);
                break;
            default:
                telaPrincipal.getLabelTelaPrincipal().setBackground(PADRAO);
                telaPrincipal.getLabelTelaPrincipal().setText(message);
                break;
        }
    }
    
    private void preencherTabelaContacto(List<Contacto> contactos) {
        contactoTabela = new ContactoTabela(contactos);
        telaPrincipal.getTabelaContacto().setModel(contactoTabela);
    }

    private void remove() {
        if(contacto != null){
            int confirma = JOptionPane.showConfirmDialog(null, "\nNome: " + contacto.getNome()
                    + "\nTelefone: " + contacto.getTelefone()
                    + "\nData de registo: " + contacto.getDataDeRegisto()
                    + "\n\nTens Certeza que desejas remover?", "Remover Contacto", JOptionPane.YES_NO_OPTION);
            if(confirma == JOptionPane.YES_OPTION) {
                messageTelaPrincipal(contactoDAO.remove(contacto.getCodigo()), 1);
                preencherTabelaContacto(contactoDAO.allContactos());
                contacto = null;
            }
        }else {
            messageTelaPrincipal("Deves selecionar um contacto na tabela", 0);
        }
    }

    private void edit() {
        if(contacto != null){
            preencherOsCampos();
            showTelaRegistoContacto(true);
        }else {
            messageTelaPrincipal("Deves selecionar um contacto na tabela", 0);
        }
    }
    
    private void preencherOsCampos() {
        telaPrincipal.getTxtContactoCodigo().setText(""+contacto.getCodigo());
        telaPrincipal.getTxtContactoNome().setText(contacto.getNome());
        telaPrincipal.getTxtContactoDataDeNascimento().setDate(contacto.getDataDeNascimento());
        telaPrincipal.getTxtContactoTelefone().setText(contacto.getTelefone());
        telaPrincipal.getTxtContactoMorada().setText(contacto.getMorada());
        telaPrincipal.getTxtContactoEmail().setText(contacto.getEmail());
    }
}
