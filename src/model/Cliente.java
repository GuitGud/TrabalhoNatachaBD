package model;

public class Cliente {
    private int idCliente;
    private String nome;
    private String cidade;

    public Cliente() {}

    public Cliente(int idCliente, String nome, String cidade) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cidade = cidade;
    }

    // Getters e Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return idCliente + " - " + nome + " - " + cidade;
    }
}
