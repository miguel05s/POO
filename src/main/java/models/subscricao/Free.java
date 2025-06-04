package main.java.models.subscricao;

import main.java.models.utilizador.Utilizador;

import java.math.BigDecimal;

public class Free implements Subscricao{

    @Override
    public String getNomePlano() {
        return "Free";
    }

    @Override
    public boolean podeCriarPlaylist() {
        return false;
    }

    @Override
    public boolean podeAvancarMusica() {
        return false;
    }

    @Override
    public boolean podeRetrocederMusica() {
        return false;
    }

    @Override
    public boolean podeGerarPlaylistFavoritos() {
        return false;
    }

    @Override
    public BigDecimal calcularPontos(Utilizador utilizador) {
        return new BigDecimal(5); // por música
    }
}
