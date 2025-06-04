package main.java.models.musica;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Album implements Serializable {
    private String nome;
    private String artista;
    private String editora;
    private List<Musica> musicas;

    public Album(String nome, String artista, String editora) {
        this.nome = nome;
        this.artista = artista;
        this.editora = editora;
        this.musicas = new ArrayList<>();
    }

    public void adicionarMusica(Musica m) {
        musicas.add(m);
    }

    public List<Musica> getMusicas() {
        return new ArrayList<>(musicas); // proteção contra alterações externas
    }

    public String getNome() {
        return nome;
    }

    public String getArtista() {
        return artista;
    }

    public String getEditora() {
        return editora;
    }

    public int getDuracaoTotal() {
        return musicas.stream().mapToInt(Musica::getDuracao).sum();
    }

    @Override
    public String toString() {
        return "Álbum: " + nome + " | Artista: " + artista + " | Nº Músicas: " + musicas.size();
    }
}