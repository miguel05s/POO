package main.java.models.musica;

public class MusicaExplicita extends Musica {

    public MusicaExplicita(String nome, String interprete, String editora, String letra,
                           String genero, int duracao) {
        super(nome, interprete, editora, letra, genero, duracao);
    }

    @Override
    public void reproduzir() {
        System.out.println("⚠️ AVISO: Conteúdo explícito");
        super.reproduzir();
    }

    @Override
    public String toString() {
        return super.toString() + " [EXPLÍCITA]";
    }
}