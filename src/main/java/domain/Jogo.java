package domain;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random; // Necessário para o posicionamento aleatório

public class Jogo {

    // Comentário sobre a mudança de Array para List
    // Tivemos que mudar de array para List para que os personagens pudessem ser
    // removidos do time e do tabuleiro de forma eficiente após serem derrotados.

    private Tabuleiro tabuleiro;
    private Scanner scanner;
    private Bot bot;

    // Lista para gerenciar personagens de cada time (Times 1 e 2)
    private List<Personagem> time1;
    private List<Personagem> time2;
    private final int MAX_PERSONAGENS = 3;

    // Atributos de Log e Controle de Turno
    private List<RegistroJogada> historicoJogadas;
    private int turnoAtual = 1;


    public Jogo() {
        this.tabuleiro = new Tabuleiro();
        this.scanner = new Scanner(System.in);
        this.bot = new Bot(); // Inicializa o Bot

        // Inicializa as LISTAS
        this.time1 = new ArrayList<>();
        this.time2 = new ArrayList<>();
        this.historicoJogadas = new ArrayList<>();
    }

    /**
     * Ponto de entrada do jogo.
     */
    public void iniciar() {
        // ... (código de seleção de modo e cabeçalho, mantido igual) ...
        System.out.println("=========================================");
        System.out.println("     Início do Jogo - A Batalha de Westeros");
        System.out.println("=========================================");

        int escolha = 0;
        while (escolha != 1 && escolha != 2) {
            System.out.println("Escolha o Modo de Jogo:");
            System.out.println("1. Jogador Humano vs. Jogador Humano");
            System.out.println("2. Jogador Humano vs. Bot (Máquina)");
            System.out.print("Sua escolha: ");
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
            }
            scanner.nextLine();
            if (escolha != 1 && escolha != 2) {
                System.out.println("Opção inválida. Por favor, digite 1 ou 2.");
            }
        }
        // Fim da seleção de modo

        // 1. Configuração dos Personagens para o Time 1 (Sempre Humano)
        configurarTime(time1, "Jogador 1 (Time Stark/Lannister)");

        if (escolha == 1) {
            // 2. Configuração dos Personagens para o Time 2 (Humano)
            configurarTime(time2, "Jogador 2 (Time Targaryen/Lannister)");
            iniciarPartida(ModoJogo.HUMANO_VS_HUMANO);
        } else {
            // 2. Configuração dos Personagens para o Time 2 (Bot)
            configurarTimeBot(time2);
            iniciarPartida(ModoJogo.HUMANO_VS_BOT);
        }
    }

    // ====================================================================
    // MÉTODOS DE CONFIGURAÇÃO (Agora usam List e time.add())
    // ====================================================================

    private void configurarTime(List<Personagem> time, String nomeJogador) {
        System.out.println("\n--- Configuração de " + nomeJogador + " ---");

        Casa casaPadrao = Casa.getSTARK(); // Exemplo
        Posicao posPadrao = new Posicao(0, 0);

        for (int i = 0; i < MAX_PERSONAGENS; i++) {
            System.out.print("Digite o nome para o Personagem " + (i + 1) + " (Casa " + casaPadrao.getNome() + "): ");
            String nome = scanner.nextLine();

            // CORREÇÃO 1: Usando time.add()
            time.add(new Personagem(nome, casaPadrao, posPadrao));
        }
    }

    private void configurarTimeBot(List<Personagem> time) { // Assinatura corrigida
        System.out.println("\n--- Configuração de Bot (Máquina) (Automático) ---");

        Casa casaBot = Casa.getTARGARYEN();
        Posicao posPadrao = new Posicao(0, 0);

        // CORREÇÃO 2: Usando time.add()
        time.add(new Personagem("Dragão", casaBot, posPadrao));
        time.add(new Personagem("MãeDosDragões", casaBot, posPadrao));
        time.add(new Personagem("Imaculado", casaBot, posPadrao));

        System.out.println("Time do Bot criado com sucesso.");
    }

    // ====================================================================
    // MÉTODOS DE POSICIONAMENTO E LOG (NOVO)
    // ====================================================================

    /**
     * Posiciona os personagens dos dois times em áreas separadas do tabuleiro.
     */
    private void posicionarPersonagens() {
        Random rand = new Random();
        int tamanho = tabuleiro.TAMANHO; // Assumindo que Tabuleiro tem uma constante TAMANHO

        // Posiciona Time 1 (em 0-3)
        for (Personagem p : time1) {
            Posicao pos;
            do {
                pos = new Posicao(rand.nextInt(4), rand.nextInt(tamanho));
            } while (tabuleiro.celulas[pos.getLinha()][pos.getColuna()] != null); // Evita sobreposição
            tabuleiro.adicionarPersonagem(p, pos);
        }

        // Posiciona Time 2 (em 6-9)
        for (Personagem p : time2) {
            Posicao pos;
            do {
                pos = new Posicao(rand.nextInt(4) + 6, rand.nextInt(tamanho));
            } while (tabuleiro.celulas[pos.getLinha()][pos.getColuna()] != null); // Evita sobreposição
            tabuleiro.adicionarPersonagem(p, pos);
        }
        System.out.println("\nPersonagens posicionados no tabuleiro!");
    }

    /**
     * Remove personagens que não estão mais vivos de seus respectivos times.
     */
    private void removerMortos(List<Personagem> time) {
        // Usa a função lambda removeIf, que é eficiente em List
        time.removeIf(p -> !p.estaVivo());
    }

    /**
     * Verifica se houve um vencedor.
     * @return O nome do vencedor ou null se o jogo continuar.
     */
    private String verificaVitoria() {
        if (time1.isEmpty()) return "Jogador 2/Bot";
        if (time2.isEmpty()) return "Jogador 1";
        return null;
    }

    // ====================================================================
    // MÉTODO PRINCIPAL DO JOGO (Loop)
    // ====================================================================

    private void iniciarPartida(ModoJogo modo) {
        System.out.println("\n--- INICIANDO PARTIDA: " + modo + " ---");

        posicionarPersonagens();
        tabuleiro.ImprimeTabuleiro();

        // Loop principal do jogo
        while (verificaVitoria() == null) {
            System.out.println("\n===== TURNO " + turnoAtual + " =====");

            // 1. Turno do Time 1 (Sempre Humano)
            gerenciarTurnoHumano(time1, time2, "Jogador 1");
            removerMortos(time1);
            removerMortos(time2); // Remove mortos caso o ataque tenha ocorrido

            if (verificaVitoria() != null) break;

            // 2. Turno do Time 2
            if (modo == ModoJogo.HUMANO_VS_HUMANO) {
                gerenciarTurnoHumano(time2, time1, "Jogador 2");
            } else {
                System.out.println("\n--- TURNO DO BOT ---");
                // Bot controla time2, ataca time1
                bot.executarTurno(tabuleiro, time2, time1);
            }

            removerMortos(time1);
            removerMortos(time2);

            if (verificaVitoria() != null) break;

            turnoAtual++;
            tabuleiro.ImprimeTabuleiro();
        }

        imprimirHistorico();
        System.out.println("\n*** FIM DA PARTIDA! Vencedor: " + verificaVitoria() + " ***");
    }

    // ====================================================================
    // GERENCIAMENTO DE TURNO HUMANO (Integrando RegistroJogada)
    // ====================================================================

    // CORREÇÃO 3: Assinatura alterada para List e aceitar o time oponente
    private void gerenciarTurnoHumano(List<Personagem> meuTime, List<Personagem> timeOponente, String nomeJogador) {
        System.out.println("\n--- TURNO DE " + nomeJogador + " ---");

        // *** AQUI ENTRA A LÓGICA DE SELEÇÃO E INPUT DO JOGADOR HUMANO ***
        // Por simplificação, vamos pegar o primeiro personagem vivo:
        if (meuTime.isEmpty()) return; // Time já derrotado
        Personagem ativo = meuTime.get(0);

        // Exemplo Simplificado de Ação (Ataque no primeiro inimigo vivo)
        if (!timeOponente.isEmpty()) {
            Personagem alvo = timeOponente.get(0);

            // 1. Tenta o ataque e obtém o dano (Acoes.atacar DEVE retornar int)
            int danoCausado = Acoes.atacar(ativo, alvo, tabuleiro);

            // 2. CRIA E ARMAZENA O REGISTRO DE ATAQUE
            if (danoCausado > 0) { // Se o dano foi real (e não fora de alcance)
                historicoJogadas.add(new RegistroJogada(
                        turnoAtual,
                        ativo,
                        alvo,
                        danoCausado,
                        alvo.getVidaAtual()
                ));
            } else {
                // Registro de um passe ou ataque falho
                historicoJogadas.add(new RegistroJogada(
                        turnoAtual,
                        ativo,
                        null, // Alvo pode ser nulo para registro de ação falha
                        "Tentativa de ataque falhou/fora de alcance"
                ));
            }
        }
    }

    // ====================================================================
    // MÉTODOS UTILITÁRIOS
    // ====================================================================

    private void imprimirHistorico() {
        System.out.println("\n===== HISTÓRICO DE JOGADAS =====");
        for (RegistroJogada registro : historicoJogadas) {
            System.out.println(registro);
        }
        System.out.println("================================");
    }

    // Método Getter solicitado
    public int getTurnoAtual() {
        return turnoAtual;
    }

    // Enum para melhor legibilidade
    private enum ModoJogo {
        HUMANO_VS_HUMANO, HUMANO_VS_BOT
    }

    public static void main(String[] args) {
        new Jogo().iniciar();
    }
}