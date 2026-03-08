package main.java.models.musica;


public class MusicaMultimedia extends Musica {
    private String linkVideo;

    public MusicaMultimedia(String nome, String interprete, String editora, String letra,
                            String genero, int duracaoSegundos, String linkVideo) {
        super(nome, interprete, editora, letra, genero, duracaoSegundos);
        this.linkVideo = linkVideo;
    }

    @Override
    public void reproduzir() {
        super.reproduzir();
        System.out.println("🎬 Video disponível em: " + linkVideo);
    }

    public String getLinkVideo() {
        return linkVideo;
    }
}