package main.java.models.utilizador;

import main.java.models.musica.Musica;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Utilizador implements Serializable {
    private String nome;
    private String email;
    private String morada;
    private String senha;
    private BigDecimal pontos;
    private List<Musica> historicoReproducoes;
    private LocalDateTime dataRegistro;

    // Construtor
    public Utilizador(String nome, String email, String morada, String senha) {
        this.nome = nome;
        this.email = email;
        this.morada = morada;
        this.senha = senha;
        this.pontos = BigDecimal.ZERO;
        this.historicoReproducoes = new ArrayList<>();
        this.dataRegistro = LocalDateTime.now();
    }

    // Método abstrato para adicionar pontos baseado no plano de subscrição
    public abstract void adicionarPontos();

    // Métodos de acesso aos dados do utilizador
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public BigDecimal getPontos() {
        return pontos;
    }

    public void setPontos(BigDecimal pontos) { 
        this.pontos = pontos;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    // Método para obter o histórico de reproduções
    public List<Musica> getHistoricoReproducoes() {
        return historicoReproducoes;
    }

    // Método para reproduzir música e adicionar ao histórico
    public void reproduzirMusica(Musica musica) {
        musica.reproduzir();
        historicoReproducoes.add(musica);
        adicionarPontos();  // Chama o método para atualizar os pontos do utilizador após cada reprodução
    }

    // Método para exibir as informações do utilizador
    public void info() {
        System.out.println("Utilizador: " + nome + " | Email: " + email + " | Morada: " + morada + " | Pontos: " + pontos.setScale(0, RoundingMode.FLOOR));
    }

    // Método para exibir o histórico de músicas reproduzidas
    public void exibirHistorico() {
        System.out.println("Histórico de Reproduções de " + nome + ":");
        for (Musica musica : historicoReproducoes) {
            System.out.println(" - " + musica.getNome() + " (" + musica.getGenero() + ")");
        }
    }

    // Novo método para verificar senha
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }
}
