package br.com.fgr.emergencia.modelos;

public class Usuario {

    private String nome;
    private String login;
    private String senha;
    private String email;
    private long telefone;
    private long ligarPara;
    private Coordenada localizacao;

    public Usuario() {

    }

    public String getNome() {

        return nome;

    }

    public void setNome(String nome) {

        this.nome = nome;

    }

    public String getLogin() {

        return login;

    }

    public void setLogin(String login) {

        this.login = login;

    }

    public String getSenha() {

        return senha;

    }

    public void setSenha(String senha) {

        this.senha = senha;

    }

    public String getEmail() {

        return email;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    public long getTelefone() {

        return telefone;

    }

    public void setTelefone(long telefone) {

        this.telefone = telefone;

    }

    public long getLigarPara() {

        return ligarPara;

    }

    public void setLigarPara(long ligarPara) {

        this.ligarPara = ligarPara;

    }

    public Coordenada getLocalizacao() {

        return localizacao;

    }

    public void setLocalizacao(Coordenada localizacao) {

        this.localizacao = localizacao;

    }

}