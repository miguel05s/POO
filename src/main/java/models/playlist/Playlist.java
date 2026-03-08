package main.java.models.playlist;

import main.java.models.musica.Musica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Serializable {
    private String nome;
    protected List<Musica> musicas;
    private int reproducoes;
    protected boolean publica;
    private String criador;

    public Playlist(String nome) {
        this.nome = nome;
        this.musicas = new ArrayList<>();
        this.reproducoes = 0;
        this.publica = false;
        this.criador = "Sistema";
    }

    public Playlist(String nome, String criador) {
        this.nome = nome;
        this.musicas = new ArrayList<>();
        this.reproducoes = 0;
        this.publica = false;
        this.criador = criador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCriador() {
        return criador;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void adicionarMusica(Musica musica) {
        if (!musicas.contains(musica)) {
            musicas.add(musica);
        }
    }

    public void removerMusica(Musica musica) {
        musicas.remove(musica);
    }

    public int getTotalMusicas() {
        return musicas.size();
    }

    public int getReproducoes() {
        return reproducoes;
    }

    public void incrementarReproducoes() {
        this.reproducoes++;
    }

    public boolean isPublica() {
        return publica;
    }

    public void setPublica(boolean publica) {
        this.publica = publica;
    }

    public void exibirInfo() {
        System.out.println("\nNome da Playlist: " + nome);
        System.out.println("Criador: " + criador);
        System.out.println("Total de músicas: " + getTotalMusicas());
        System.out.println("Reproduções: " + reproducoes);
        System.out.println("\nMúsicas na playlist:");
        for (Musica musica : musicas) {
            System.out.println("- " + musica.getNome() + " (" + musica.getInterprete() + ")");
        }
    }

    public void reproduzir() {
        for (Musica musica : musicas) {
            musica.reproduzir();
        }
        incrementarReproducoes();
    }

    public int getDuracaoTotal() {
        return musicas.stream().mapToInt(Musica::getDuracao).sum();
    }
}