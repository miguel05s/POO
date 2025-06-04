package main.java.services;

import main.java.models.musica.Musica;
import main.java.models.musica.MusicaExplicita;
import main.java.utils.DadosImportacao;
import main.java.utils.ImportadorJSON;
import java.util.ArrayList;
import java.util.List;

public class GestorMusicas {
    private List<Musica> musicas = new ArrayList<>();

    public void adicionarMusica(Musica musica) {
        musicas.add(musica);
    }

    public List<Musica> listarMusicas() {
        return new ArrayList<>(musicas);
    }

    public boolean existeMusica(String nome) {
        return musicas.stream().anyMatch(m -> m.getNome().equalsIgnoreCase(nome));
    }
    
    public Musica buscarMusica(String nome) {
        return musicas.stream()
                     .filter(m -> m.getNome().equalsIgnoreCase(nome))
                     .findFirst()
                     .orElse(null);
    }

    public boolean importarDeJSON(String caminho) {
        DadosImportacao dados = ImportadorJSON.importar(caminho);
        if (dados == null || dados.musicas == null) {
            return false;
        }

        for (var dto : dados.musicas) {
            if (!existeMusica(dto.nome)) {
                Musica musica;
                if (dto.explicita) {
                    musica = new MusicaExplicita(dto.nome, dto.interprete, dto.editora, 
                        dto.letra, dto.genero, dto.duracao);
                } else {
                    musica = new Musica(dto.nome, dto.interprete, dto.editora, 
                        dto.letra, dto.genero, dto.duracao);
                }
                musicas.add(musica);
            }
        }
        return true;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas.clear();
        this.musicas.addAll(musicas);
    }

    // Podes adicionar mais métodos conforme necessário (remover, procurar, etc.)
} 