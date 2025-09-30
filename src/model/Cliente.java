package model;

public class Cliente {
    private int idCliente;
    private String nome;
    private String cidade;

    // construtores, getters e setters
    public Cliente() {}
    public Cliente(int id, String nome, String cidade) {
        this.idCliente = id;
        this.nome = nome;
        this.cidade = cidade;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int id) { this.idCliente = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
}
