package main.java.models.playlist;

import main.java.models.musica.Musica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistPersonalizada extends Playlist {

    private int posicaoAtual = 0;
    private boolean aleatoria;

    public PlaylistPersonalizada(String nome, boolean publica, boolean aleatoria) {
        super(nome);
        this.publica = publica;
        this.aleatoria = aleatoria;
    }

    public void avancar() {
        posicaoAtual = (posicaoAtual + 1) % musicas.size();
    }

    public void retroceder() {
        posicaoAtual = (posicaoAtual - 1 + musicas.size()) % musicas.size();
    }

    @Override
    public void reproduzir() {
        List<Musica> listaParaReproducao = aleatoria ? new ArrayList<>(musicas) : musicas;
        if (aleatoria) Collections.shuffle(listaParaReproducao);

        for (Musica m : listaParaReproducao) {
            m.reproduzir();
        }
    }
}