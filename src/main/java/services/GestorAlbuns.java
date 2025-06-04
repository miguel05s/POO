package main.java.services;

import main.java.models.musica.Album;
import main.java.models.musica.Musica;
import java.util.ArrayList;
import java.util.List;

public class GestorAlbuns {
    private List<Album> albuns = new ArrayList<>();
    
    public void adicionarAlbum(Album album) {
        albuns.add(album);
    }
    
    public List<Album> listarAlbuns() {
        return new ArrayList<>(albuns);
    }
    
    public boolean existeAlbum(String nome) {
        return albuns.stream().anyMatch(a -> a.getNome().equalsIgnoreCase(nome));
    }
    
    public Album buscarAlbum(String nome) {
        return albuns.stream()
                    .filter(a -> a.getNome().equalsIgnoreCase(nome))
                    .findFirst()
                    .orElse(null);
    }

    // Método para criar um novo álbum
    public Album criarAlbum(String nome, String artista, String editora) {
        if (existeAlbum(nome)) {
            throw new IllegalArgumentException("Já existe um álbum com esse nome.");
        }
        Album album = new Album(nome, artista, editora);
        adicionarAlbum(album);
        return album;
    }

    // Método para adicionar uma música a um álbum
    public void adicionarMusicaAoAlbum(String nomeAlbum, Musica musica) {
        Album album = buscarAlbum(nomeAlbum);
        if (album == null) {
            throw new IllegalArgumentException("Álbum não encontrado.");
        }
        album.adicionarMusica(musica);
    }

    // Método para obter informações detalhadas de um álbum
    public String obterInfoAlbum(String nome) {
        Album album = buscarAlbum(nome);
        if (album == null) {
            return "Álbum não encontrado.";
        }
        
        StringBuilder info = new StringBuilder();
        info.append(album.toString()).append("\n");
        info.append("Músicas no álbum:\n");
        for (Musica m : album.getMusicas()) {
            info.append(" - ").append(m.getNome()).append("\n");
        }
        return info.toString();
    }

    // Método para listar todos os álbuns com suas músicas
    public String listarAlbunsComMusicas() {
        if (albuns.isEmpty()) {
            return "Nenhum álbum registado.";
        }
        
        StringBuilder lista = new StringBuilder();
        for (Album a : albuns) {
            lista.append(a.toString()).append("\n");
            lista.append("Músicas no álbum:\n");
            for (Musica m : a.getMusicas()) {
                lista.append(" - ").append(m.getNome()).append("\n");
            }
            lista.append("\n");
        }
        return lista.toString();
    }
}
