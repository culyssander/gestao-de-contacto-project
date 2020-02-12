/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ao.co.culysoft.gestaodecontacto.model.modelo;

import java.util.Date;

/**
 *
 * @author quitumba.ferreira
 */
public class Contacto {
    private Integer codigo;
    private String nome;
    private Date dataDeNascimento;
    private String telefone;
    private String morada;
    private String email;
    private String dataDeRegisto;

    public Contacto() {
    }

    public Contacto(Integer codigo, String nome, Date dataDeNascimento, String telefone, String morada, String email, String dataDeRegisto) {
        this.codigo = codigo;
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.telefone = telefone;
        this.morada = morada;
        this.email = email;
        this.dataDeRegisto = dataDeRegisto;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataDeRegisto() {
        return dataDeRegisto;
    }

    public void setDataDeRegisto(String dataDeRegisto) {
        this.dataDeRegisto = dataDeRegisto;
    }
    
    public int getIdade(){
        if(dataDeNascimento != null) {
            Long milesimos = new Date().getTime() - dataDeNascimento.getTime();
            Long idade = milesimos / 31536000000L;
            return idade.intValue();
        }
        return 0;
    }

}
