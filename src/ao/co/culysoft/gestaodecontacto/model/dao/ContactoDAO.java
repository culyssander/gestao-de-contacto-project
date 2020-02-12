/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ao.co.culysoft.gestaodecontacto.model.dao;

import ao.co.culysoft.gestaodecontacto.model.conexao.Conexao;
import ao.co.culysoft.gestaodecontacto.model.modelo.Contacto;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author quitumba.ferreira
 */
public class ContactoDAO {
    
    public String save(Contacto c) {
        return c.getCodigo() == 0 ? add(c) : edit(c);
    }

    private String add(Contacto c) {
        StringBuilder sql = new StringBuilder()
                .append("INSERT INTO contacto (nome, data_nascimento, telefone, morada, email, data_registo) ")
                .append("VALUES(?, ?, ?, ?, ?, ?)");
        try(PreparedStatement ps = Conexao.obterConexao().prepareStatement(sql.toString())) {
            addParameterToContacto(ps, c);
            return ps.executeUpdate() > 0 ? "Contacto adicionado com sucesso":"Não foi possivel adicionar";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String edit(Contacto c) {
        StringBuilder sql = new StringBuilder()
                .append("UPDATE contacto SET nome = ?, data_nascimento = ?, telefone = ?, morada = ?, email = ?, data_registo = ? ")
                .append("WHERE codigo = ?");
        try(PreparedStatement ps = Conexao.obterConexao().prepareStatement(sql.toString())) {
            addParameterToContacto(ps, c);
            return ps.executeUpdate() > 0 ? "Contacto editado com sucesso":"Não foi possivel editar";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private void addParameterToContacto(PreparedStatement ps, Contacto c) throws SQLException {
        ps.setString(1, c.getNome());
        ps.setDate(2, new Date(c.getDataDeNascimento().getTime()));
        ps.setString(3, c.getTelefone());
        ps.setString(4, c.getMorada());
        ps.setString(5, c.getEmail());
        ps.setString(6, c.getDataDeRegisto());
        
        if(c.getCodigo() > 0){
            ps.setInt(7, c.getCodigo());
        }
    }
    
    public String remove(int codigo) {
        StringBuilder sql = new StringBuilder()
                .append("DELETE FROM contacto WHERE codigo = ?");
        try(PreparedStatement ps = Conexao.obterConexao().prepareStatement(sql.toString())) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0 ? "Contacto removido com sucesso":"Não foi possivel remover";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    
    public List<Contacto> allContactos() {
        StringBuilder sql = new StringBuilder()
                .append("SELECT * FROM contacto");
        List<Contacto> contactos =  new ArrayList<>();
        try (PreparedStatement ps = Conexao.obterConexao().prepareStatement(sql.toString());
                ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                contactos.add(preencherContacto(rs, new Contacto()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactos;
    }
//codigo, nome, data_nascimento, telefone, morada, email, data_registo
    private Contacto preencherContacto(ResultSet rs, Contacto contacto) throws SQLException {
        contacto.setCodigo(rs.getInt("codigo"));
        contacto.setNome(rs.getString("nome"));
        contacto.setDataDeNascimento(rs.getDate("data_nascimento"));
        contacto.setTelefone(rs.getString("telefone"));
        contacto.setMorada(rs.getString("morada"));
        contacto.setEmail(rs.getString("email"));
        contacto.setDataDeRegisto(rs.getString("data_registo"));
        
        return contacto;
    }
    
        public List<Contacto> filterContactosFromNomeOrTelefoneOrMorada(String filter) {
        StringBuilder sql = new StringBuilder()
                    .append("SELECT * FROM contacto WHERE nome LIKE ? OR telefone LIKE ? OR morada LIKE ?");
            List<Contacto> contactos = new ArrayList<>();
            try (PreparedStatement ps = Conexao.obterConexao().prepareStatement(sql.toString())) {
                ps.setString(1, "%"+filter+"%");
                ps.setString(2, "%"+filter+"%");
                ps.setString(3, "%"+filter+"%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        contactos.add(preencherContacto(rs, new Contacto()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
        }
        return contactos;
    }
}
