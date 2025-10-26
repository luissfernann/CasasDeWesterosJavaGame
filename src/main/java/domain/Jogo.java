package domain;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {

    private Tabuleiro tabuleiro;
    private Scanner scanner;
    private Bot bot;

    private List<Personagem> time1;
    private List<Personagem> time2;


    private final int MAX_PERSONAGENS = 3;

    private List<RegistroJogada> historicoJogadas;
    private int turnoAtual = 1;


    public Jogo() {
        this.tabuleiro = new Tabuleiro();
        this.scanner = new Scanner(System.in);
        this.bot = new Bot();

        this.time1 = new ArrayList<>();
        this.time2 = new ArrayList<>();
        this.historicoJogadas = new ArrayList<>();
    }

    /**
     * Ponto de entrada do jogo.
     */
    public void iniciar() {
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

            // ALTERAÇÃO: Chama posicionamento aqui, após a configuração dos dois times
            posicionarPersonagens();
            iniciarPartida(ModoJogo.HUMANO_VS_HUMANO);
        } else {
            // 2. Configuração dos Personagens para o Time 2 (Bot)
            configurarTimeBot(time2);

            // ALTERAÇÃO: Chama posicionamento aqui, após a configuração dos dois times
            posicionarPersonagens();
            iniciarPartida(ModoJogo.HUMANO_VS_BOT);
        }
    }

    // ====================================================================
    // MÉTODOS DE CONFIGURAÇÃO (Omitidos - Estão corretos)
    // ====================================================================

    private void configurarTime(List<Personagem> time, String nomeJogador) {
        System.out.println("\n--- Configuração de " + nomeJogador + " ---");
        Casa casaPadrao = Casa.getSTARK();
        Posicao posPadrao = new Posicao(0, 0);
        for (int i = 0; i < MAX_PERSONAGENS; i++) {
            System.out.print("Digite o nome para o Personagem " + (i + 1) + " (Casa " + casaPadrao.getNome() + "): ");
            String nome = scanner.nextLine();
            time.add(new Personagem(nome, casaPadrao, posPadrao));
        }
    }

    private void configurarTimeBot(List<Personagem> time) {
        System.out.println("\n--- Configuração de Bot (Máquina) (Automático) ---");
        Casa casaBot = Casa.getTARGARYEN();
        Posicao posPadrao = new Posicao(0, 0);
        time.add(new Personagem("Dragão", casaBot, posPadrao));
        time.add(new Personagem("MãeDosDragões", casaBot, posPadrao));
        time.add(new Personagem("Imaculado", casaBot, posPadrao));
        System.out.println("Time do Bot criado com sucesso.");
    }

    // ====================================================================
    // MÉTODOS DE POSICIONAMENTO E LOG
    // ====================================================================

    /**
     * Posiciona os personagens dos dois times em áreas separadas do tabuleiro.
     */
    private void posicionarPersonagens() {
        Random rand = new Random();
        int tamanho = 10; // Use a constante correta do seu Tabuleiro

        // Posiciona Time 1 (em 0-3)
        for (Personagem p : time1) {
            Posicao pos;
            do {
                pos = new Posicao(rand.nextInt(4), rand.nextInt(tamanho));
            } while (tabuleiro.celulas[pos.getLinha()][pos.getColuna()] != null);
            tabuleiro.adicionarPersonagem(p, pos);
        }

        // Posiciona Time 2 (em 6-9)
        for (Personagem p : time2) {
            Posicao pos;
            do {
                pos = new Posicao(rand.nextInt(4) + 6, rand.nextInt(tamanho));
            } while (tabuleiro.celulas[pos.getLinha()][pos.getColuna()] != null);
            tabuleiro.adicionarPersonagem(p, pos);
        }
        System.out.println("\nPersonagens posicionados no tabuleiro!");
    }

    private void removerMortos(List<Personagem> time) {
        time.removeIf(p -> !p.estaVivo());
    }

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

        // NOTE: 'posicionarPersonagens()' FOI REMOVIDO DAQUI para ser chamado no iniciar()
        tabuleiro.ImprimeTabuleiro(); // Imprime o tabuleiro já posicionado

        while (verificaVitoria() == null) {
            System.out.println("\n===== TURNO " + turnoAtual + " =====");

            // 1. Turno do Time 1 (Sempre Humano)
            gerenciarTurnoHumano(time1, time2, "Jogador 1");
            removerMortos(time1);
            removerMortos(time2);

            if (verificaVitoria() != null) break;

            // 2. Turno do Time 2
            if (modo == ModoJogo.HUMANO_VS_HUMANO) {
                gerenciarTurnoHumano(time2, time1, "Jogador 2");
            } else {
                System.out.println("\n--- TURNO DO BOT ---");
                // Correção: Passando o histórico e o turno
                bot.executarTurno(tabuleiro, time2, time1, historicoJogadas, turnoAtual);
            }

            removerMortos(time1);
            removerMortos(time2);

            if (verificaVitoria() != null) break;

            turnoAtual++;
            tabuleiro.ImprimeTabuleiro();
        }

        imprimirHistorico();
        System.out.println("\n** FIM DA PARTIDA! Vencedor: " + verificaVitoria() + " **");
    }

    // ====================================================================
    // GERENCIAMENTO DE TURNO HUMANO (Integrando RegistroJogada)
    // ====================================================================

    private void gerenciarTurnoHumano(List<Personagem> meuTime, List<Personagem> timeOponente, String nomeJogador) {
        // ... (Lógica Omitida - Está correta, mas com placeholder para input) ...
        System.out.println("\n--- TURNO DE " + nomeJogador + " ---");

        if (meuTime.isEmpty()) return;
        Personagem ativo = meuTime.get(0);

        if (!timeOponente.isEmpty()) {
            Personagem alvo = timeOponente.get(0);

            int danoCausado = Acoes.atacar(ativo, alvo, tabuleiro);

            // Na classe Jogo.java, dentro do método gerenciarTurnoHumano:

// ... (Lógica do ataque)

            if (danoCausado > 0) {
                // 1. REGISTRO DE ATAQUE BEM-SUCEDIDO (5 argumentos)
                historicoJogadas.add(new RegistroJogada(
                        turnoAtual,
                        ativo,
                        alvo,
                        danoCausado,
                        alvo.getVidaAtual()
                ));
            } else {
                // 2. REGISTRO DE AÇÃO FALHA (3 argumentos)
                historicoJogadas.add(new RegistroJogada(
                        turnoAtual,
                        ativo,
                        // REMOVA O 'null' QUE ESTAVA AQUI!
                        "Tentativa de ataque falhou/fora de alcance" // A String agora é o 3º argumento
                ));
            }
        }
    }

    // ====================================================================
    // MÉTODOS UTILITÁRIOS (Omitidos - Estão corretos)
    // ====================================================================

    private void imprimirHistorico() {
        System.out.println("\n===== HISTÓRICO DE JOGADAS =====");
        for (RegistroJogada registro : historicoJogadas) {
            System.out.println(registro);
        }
        System.out.println("================================");
    }

    public int getTurnoAtual() {
        return turnoAtual;
    }

    private enum ModoJogo {
        HUMANO_VS_HUMANO, HUMANO_VS_BOT
    }

    public static void main(String[] args) {
        new Jogo().iniciar();
    }
}