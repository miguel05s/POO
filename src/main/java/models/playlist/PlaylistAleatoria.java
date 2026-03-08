package main.java.models.playlist;

import main.java.models.musica.Musica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistAleatoria extends Playlist{
    public PlaylistAleatoria(String nome) {
        super(nome);
    }

    @Override
    public void reproduzir() {
        List<Musica> copia = new ArrayList<>(musicas);
        Collections.shuffle(copia);
        for (Musica m : copia) {
            m.reproduzir();
        }
    }
}
