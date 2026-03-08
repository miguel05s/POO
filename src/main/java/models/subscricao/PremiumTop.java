package main.java.models.subscricao;

import main.java.models.utilizador.Utilizador;

import java.math.BigDecimal;

public class PremiumTop extends PremiumBase {
    @Override
    public String getNomePlano() {
        return "PremiumTop";
    }

    @Override
    public boolean podeGerarPlaylistFavoritos() {
        return true; 
    }

    @Override
    public BigDecimal calcularPontos(Utilizador utilizador) {
        return utilizador.getPontos().multiply(new BigDecimal("0.025"));
    }

    public int pontosIniciais(){
        //100 iniciais
        return 100;
    }
}