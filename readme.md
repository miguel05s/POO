# SpotifUM

**Programação Orientada a Objetos** · Licenciatura em Ciências da Computação · Universidade do Minho · 2024/2025

---

## Descrição

Aplicação Java que simula uma plataforma de streaming musical, desenvolvida com foco na aplicação dos princípios de programação orientada a objetos — encapsulamento, herança, polimorfismo e abstração.

O sistema permite gerir músicas, utilizadores e playlists, suportando diferentes perfis de subscrição, reprodução de conteúdo, recomendações personalizadas e persistência de dados entre sessões.

## Estrutura do Projeto

```
src/
├── model/          # Classes de domínio (Musica, Playlist, Utilizador, Album, ...)
├── service/        # Lógica de negócio (GestorMusicas, GestorPlaylists, ...)
├── ui/             # Interfaces de consola (MenuPrincipal, LoginUI, PlayerUI, ...)
├── dto/            # Data Transfer Objects para importação/exportação
└── util/           # Utilitários (InputUtil, ConsoleUtil, Persistencia)
```

## Funcionalidades

- Gestão de músicas, álbuns e playlists (personalizadas, aleatórias, por género e duração)
- Sistema de utilizadores com planos de subscrição: Free, Premium Base e Premium Top
- Reprodução de músicas e playlists com registo de histórico
- Recomendações personalizadas com base no perfil de escuta
- Estatísticas globais e por período temporal
- Persistência de dados por serialização Java
- Importação de dados via ficheiros JSON

## Execução

```bash
javac -r src/ -d out/
java -cp out/ main.java.ui.Menu
```

Os dados são persistidos automaticamente na diretoria `dados/` no términus de cada sessão.

## Tecnologias

- Java 17
- Serialização Java
- JSON

## Autores

João Silva — A108483  
Matilde Domingues — A98982  
Miguel Silva — A109069
