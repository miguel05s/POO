package main.java.models.playlist;

import main.java.models.musica.Musica;

import java.util.List;

public class PlaylistFavoritos extends Playlist {

    public PlaylistFavoritos(String nome, List<Musica> recomendadas) {
        super(nome);
        this.publica = false;
        this.musicas.addAll(recomendadas);
    }

    @Override
    public void reproduzir() {
        System.out.println("🎧 A reproduzir playlist de favoritos...");
        for (Musica m : musicas) {
            m.reproduzir();
        }
    }
}