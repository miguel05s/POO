package main.java.models.subscricao;

import main.java.models.utilizador.Utilizador;

import java.math.BigDecimal;

public class PremiumBase implements Subscricao {
    @Override
    public String getNomePlano() {
        return "PremiumBase"; // Nome do plano
    }

    @Override
    public boolean podeCriarPlaylist() {
        return true; // Usuários PremiumBase podem criar playlists
    }

    @Override
    public boolean podeAvancarMusica() {
        return true; // Usuários PremiumBase podem avançar músicas
    }

    @Override
    public boolean podeRetrocederMusica() {
        return true; // Usuários PremiumBase podem retroceder músicas
    }

    @Override
    public boolean podeGerarPlaylistFavoritos() {
        return false; // Usuários PremiumBase não podem gerar playlists de favoritos
    }

    @Override
    public BigDecimal calcularPontos(Utilizador utilizador) {
        return new BigDecimal(10); // Usuários PremiumBase ganham 10 pontos por música
    }
}