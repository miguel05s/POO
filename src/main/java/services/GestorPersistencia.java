package main.java.services;

import main.java.models.musica.Musica;
import main.java.models.playlist.Playlist;
import main.java.models.utilizador.Utilizador;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class GestorPersistencia {
    private static final String DIRETORIO_DADOS = "dados/";
    private static final String ARQUIVO_MUSICAS = "musicas.ser";
    private static final String ARQUIVO_PLAYLISTS = "playlists.ser";
    private static final String ARQUIVO_UTILIZADORES = "utilizadores.ser";

    public GestorPersistencia() {
        criarDiretorioSeNecessario();
    }

    private void criarDiretorioSeNecessario() {
        File diretorio = new File(DIRETORIO_DADOS);
        if (!diretorio.exists()) {
            boolean criado = diretorio.mkdirs();
            System.out.println("Diretório de dados " + (criado ? "criado" : "não foi possível criar") + " em: " + diretorio.getAbsolutePath());
        }
    }

    public void salvarMusicas(List<Musica> musicas) {
        System.out.println("Salvando " + musicas.size() + " músicas...");
        salvarObjeto(musicas, DIRETORIO_DADOS + ARQUIVO_MUSICAS);
    }

    public void salvarPlaylists(List<Playlist> playlists) {
        System.out.println("Salvando " + playlists.size() + " playlists...");
        salvarObjeto(playlists, DIRETORIO_DADOS + ARQUIVO_PLAYLISTS);
    }

    public void salvarUtilizadores(List<Utilizador> utilizadores) {
        System.out.println("Salvando " + utilizadores.size() + " utilizadores...");
        salvarObjeto(utilizadores, DIRETORIO_DADOS + ARQUIVO_UTILIZADORES);
    }

    @SuppressWarnings("unchecked")
    public List<Musica> carregarMusicas() {
        System.out.println("Tentando carregar músicas de: " + DIRETORIO_DADOS + ARQUIVO_MUSICAS);
        return (List<Musica>) carregarObjeto(DIRETORIO_DADOS + ARQUIVO_MUSICAS);
    }

    @SuppressWarnings("unchecked")
    public List<Playlist> carregarPlaylists() {
        System.out.println("Tentando carregar playlists de: " + DIRETORIO_DADOS + ARQUIVO_PLAYLISTS);
        return (List<Playlist>) carregarObjeto(DIRETORIO_DADOS + ARQUIVO_PLAYLISTS);
    }

    @SuppressWarnings("unchecked")
    public List<Utilizador> carregarUtilizadores() {
        System.out.println("Tentando carregar utilizadores de: " + DIRETORIO_DADOS + ARQUIVO_UTILIZADORES);
        return (List<Utilizador>) carregarObjeto(DIRETORIO_DADOS + ARQUIVO_UTILIZADORES);
    }

    private void salvarObjeto(Object objeto, String caminhoArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(objeto);
            System.out.println("Objeto salvo com sucesso em: " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em " + caminhoArquivo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Object carregarObjeto(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object objeto = ois.readObject();
            System.out.println("Objeto carregado com sucesso de: " + caminhoArquivo);
            return objeto;
        } catch (IOException e) {
            System.err.println("Erro de I/O ao carregar dados de " + caminhoArquivo + ": " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.err.println("Classe não encontrada ao carregar dados de " + caminhoArquivo + ": " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
} 