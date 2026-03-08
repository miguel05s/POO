package main.java.ui;

import main.java.models.musica.Album;
import main.java.models.musica.Musica;
import main.java.services.GestorAlbuns;
import main.java.services.GestorMusicas;
import java.util.List;
import java.util.Scanner;
import main.java.utils.InputUtil;

public class AlbumUI extends MenuBase {
    private final GestorAlbuns gestorAlbuns;
    private final GestorMusicas gestorMusicas;

    public AlbumUI(Scanner scanner, GestorAlbuns gestorAlbuns, GestorMusicas gestorMusicas) {
        super(scanner, "Gestão de Álbuns");
        this.gestorAlbuns = gestorAlbuns;
        this.gestorMusicas = gestorMusicas;
    }

    @Override
    protected void mostrarOpcoes() {
        System.out.println("1. Criar Álbum");
        System.out.println("2. Listar Álbuns");
        System.out.println("3. Adicionar Música ao Álbum");
        System.out.println("4. Remover Música do Álbum");
        System.out.println("5. Voltar");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> criarAlbum();
            case 2 -> listarAlbuns();
            case 3 -> adicionarMusica();
            case 4 -> removerMusica();
            case 5 -> System.out.println("A voltar ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 5;
    }

    private void criarAlbum() {
        String nome = InputUtil.lerTexto(scanner, "Nome do álbum: ");
        if (gestorAlbuns.existeAlbum(nome)) {
            System.out.println("Já existe um álbum com esse nome.");
            return;
        }

        String artista = InputUtil.lerTexto(scanner, "Artista: ");
        String editora = InputUtil.lerTexto(scanner, "Editora: ");

        Album album = new Album(nome, artista, editora);
        gestorAlbuns.adicionarAlbum(album);
        System.out.println("Álbum criado com sucesso!");
    }

    private void listarAlbuns() {
        List<Album> albuns = gestorAlbuns.listarAlbuns();
        StringBuilder resultado = new StringBuilder();
        
        if (albuns.isEmpty()) {
            resultado.append("Nenhum álbum registado.");
        } else {
            albuns.forEach(a -> {
                resultado.append("\nÁlbum: ").append(a.getNome())
                        .append("\nArtista: ").append(a.getArtista())
                        .append("\nEditora: ").append(a.getEditora())
                        .append("\nNúmero de músicas: ").append(a.getMusicas().size())
                        .append("\nDuração total: ").append(a.getDuracaoTotal()).append(" segundos")
                        .append("\nMúsicas no álbum:");
                a.getMusicas().forEach(m -> 
                    resultado.append("\n - ").append(m.getNome()));
                resultado.append("\n");
            });
        }
        
        mostrarResultado(resultado.toString());
    }

    private void adicionarMusica() {
        String nomeAlbum = InputUtil.lerTexto(scanner, "Nome do álbum: ");
        Album album = gestorAlbuns.buscarAlbum(nomeAlbum);
        if (album == null) {
            System.out.println("Álbum não encontrado.");
            return;
        }

        String nomeMusica = InputUtil.lerTexto(scanner, "Nome da música: ");
        Musica musica = gestorMusicas.buscarMusica(nomeMusica);
        if (musica == null) {
            System.out.println("Música não encontrada.");
            return;
        }

        album.adicionarMusica(musica);
        System.out.println("Música adicionada com sucesso!");
    }

    private void removerMusica() {
        String nomeAlbum = InputUtil.lerTexto(scanner, "Nome do álbum: ");
        Album album = gestorAlbuns.buscarAlbum(nomeAlbum);
        if (album == null) {
            System.out.println("Álbum não encontrado.");
            return;
        }

        String nomeMusica = InputUtil.lerTexto(scanner, "Nome da música: ");
        Musica musica = gestorMusicas.buscarMusica(nomeMusica);
        if (musica == null) {
            System.out.println("Música não encontrada.");
            return;
        }

        album.getMusicas().remove(musica);
        System.out.println("Música removida com sucesso!");
    }
}
