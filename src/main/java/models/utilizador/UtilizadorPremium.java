package main.java.models.utilizador;

import java.math.BigDecimal;
import main.java.models.subscricao.Subscricao;

public class UtilizadorPremium extends Utilizador {
    private Subscricao plano;

    public UtilizadorPremium(String nome, String email, String morada, String senha, Subscricao plano) {
        super(nome, email, morada, senha);
        this.plano = plano;
        this.setPontos(this.getPontos().add(BigDecimal.valueOf(plano.pontosIniciais())));
    }

    @Override
    public void adicionarPontos() {
        BigDecimal incremento = plano.calcularPontos(this);
        this.setPontos(this.getPontos().add(incremento));
    }

    public Subscricao getPlano() {
        return plano;
    }

    public void setPlano(Subscricao plano) {
        this.plano = plano;
    }
}