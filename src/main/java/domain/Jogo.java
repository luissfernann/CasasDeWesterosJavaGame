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
    private int turnoAtual;


    public Jogo() {
        this.tabuleiro = new Tabuleiro();
        this.scanner = new Scanner(System.in);
        this.bot = new Bot();

        this.time1 = new ArrayList<>();
        this.time2 = new ArrayList<>();
        this.historicoJogadas = new ArrayList<>();
        this.turnoAtual = 1;
    }

    /**
     * Ponto de entrada do jogo.
     */
    public void iniciar() {
        // Loop principal para a opção "Jogar Novamente" (Atividade 6)
        boolean jogarNovamente = true;
        while (jogarNovamente) {
            // Reseta o estado do jogo para uma nova partida
            resetarJogo();

            System.out.println("=========================================");
            System.out.println("  Início do Jogo - A Batalha de Westeros");
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
                scanner.nextLine(); // Limpa o buffer
                if (escolha != 1 && escolha != 2) {
                    System.out.println("Opção inválida. Por favor, digite 1 ou 2.");
                }
            }
            // Fim da seleção de modo

            // 1. Configuração dos Personagens para o Time 1 (Sempre Humano)
            configurarTime(time1, "Jogador 1");

            if (escolha == 1) {
                // 2. Configuração dos Personagens para o Time 2 (Humano)
                configurarTime(time2, "Jogador 2");
                iniciarPartida(ModoJogo.HUMANO_VS_HUMANO);
            } else {
                // 2. Configuração dos Personagens para o Time 2 (Bot)
                configurarTimeBot(time2); // Corrigido
                iniciarPartida(ModoJogo.HUMANO_VS_BOT);
            }

            // Pergunta se quer jogar novamente (Atividade 6)
            System.out.println("\nDeseja Jogar Novamente? (S/N)");
            String resposta = scanner.nextLine().toUpperCase();
            if (!resposta.equals("S")) {
                jogarNovamente = false;
            }
        }
        System.out.println("Obrigado por jogar!");
    }

    // ====================================================================
    // MÉTODOS DE CONFIGURAÇÃO (Implementação da Atividade 3)
    // ====================================================================

    /**
     * Configura um time para um jogador humano.
     * Permite escolher NOME e CASA (Requisito do PDF).
     */
    private void configurarTime(List<Personagem> time, String nomeJogador) {
        System.out.println("\n--- Configuração de " + nomeJogador + " ---");
        Posicao posPadrao = new Posicao(0, 0); // Posição temporária

        for (int i = 0; i < MAX_PERSONAGENS; i++) {
            Casa casaEscolhida = null;
            while (casaEscolhida == null) {
                System.out.println("Escolha a Casa para o Personagem " + (i + 1) + ":");
                System.out.println("1. STARK");
                System.out.println("2. LANNISTER");
                System.out.println("3. TARGARYEN");
                System.out.print("Opção: ");
                String escCasa = scanner.nextLine();
                switch (escCasa) {
                    case "1": casaEscolhida = Casa.getSTARK(); break;
                    case "2": casaEscolhida = Casa.getLANNISTER(); break;
                    case "3": casaEscolhida = Casa.getTARGARYEN(); break;
                    default: System.out.println("Opção inválida.");
                }
            }

            System.out.print("Digite o nome para o Personagem " + (i + 1) + " (Casa " + casaEscolhida.getNome() + "): ");
            String nome = scanner.nextLine();
            time.add(new Personagem(nome, casaEscolhida, posPadrao));
        }
    }

    /**
     * Configura um time para o Bot.
     * Escolhe Nomes e CASAS ALEATÓRIAS (Requisito do PDF).
     */
    private void configurarTimeBot(List<Personagem> time) {
        System.out.println("\n--- Configuração de Bot (Máquina) (Automático) ---");
        Casa[] casas = {Casa.getSTARK(), Casa.getLANNISTER(), Casa.getTARGARYEN()};
        Random r = new Random();
        Posicao posPadrao = new Posicao(0, 0);

        time.add(new Personagem("Dragão", casas[r.nextInt(casas.length)], posPadrao));
        time.add(new Personagem("MãeDosDragões", casas[r.nextInt(casas.length)], posPadrao));
        time.add(new Personagem("Imaculado", casas[r.nextInt(casas.length)], posPadrao));

        System.out.println("Time do Bot criado com sucesso.");
    }

    // ====================================================================
    // MÉTODOS DE POSICIONAMENTO E ESTADO DE JOGO
    // ====================================================================

    /**
     * Chama o tabuleiro para posicionar os personagens em lados opostos.
     */
    private void posicionarPersonagens() {
        tabuleiro.posicaoPInicial(time1, time2); // Delegado ao Tabuleiro
        System.out.println("\nPersonagens posicionados no tabuleiro!");
    }

    /**
     * Remove personagens com vida <= 0 da lista do time.
     */
    private void removerMortos(List<Personagem> time) {
        time.removeIf(p -> !p.estaVivo());
    }

    /**
     * Verifica se algum time venceu (Atividade 6).
     * @return O nome do vencedor ou null se o jogo continua.
     */
    private String verificaVitoria() {
        if (time1.isEmpty()) return "Jogador 2/Bot";
        if (time2.isEmpty()) return "Jogador 1";
        return null;
    }

    /**
     * Reseta o estado do jogo para a opção "Jogar Novamente".
     */
    private void resetarJogo() {
        this.tabuleiro = new Tabuleiro();
        this.time1.clear();
        this.time2.clear();
        this.historicoJogadas.clear();
        this.turnoAtual = 1;
    }

    // ====================================================================
    // MÉTODO PRINCIPAL DO JOGO (Loop)
    // ====================================================================

    private void iniciarPartida(ModoJogo modo) {
        // Posiciona os personagens ANTES de iniciar o loop
        posicionarPersonagens();

        System.out.println("\n-- INICIANDO PARTIDA: " + modo + " --");
        tabuleiro.ImprimeTabuleiro(); // Mostra o estado inicial

        while (verificaVitoria() == null) {
            System.out.println("\n              TURNO " + turnoAtual + " ");

            // 1. Turno do Time 1 (Sempre Humano)
            gerenciarTurnoHumano(time1, time2, "Jogador 1");
            removerMortos(time2); // Verifica mortes no time 2
            if (verificaVitoria() != null) break;

            tabuleiro.ImprimeTabuleiro(); // Mostra o tabuleiro após a jogada 1

            // 2. Turno do Time 2
            if (modo == ModoJogo.HUMANO_VS_HUMANO) {
                gerenciarTurnoHumano(time2, time1, "Jogador 2");
            } else {
                bot.executarTurno(tabuleiro, time2, time1, historicoJogadas, turnoAtual);
            }
            removerMortos(time1); // Verifica mortes no time 1
            if (verificaVitoria() != null) break;

            turnoAtual++;
            tabuleiro.ImprimeTabuleiro(); // Mostra o tabuleiro no fim da rodada
        }

        System.out.println("\n** FIM DA PARTIDA! Vencedor: " + verificaVitoria() + " **");

        // Oferecer Replay (Atividade 6)
        System.out.println("\nDeseja ver o Replay? (S/N)");
        String resposta = scanner.nextLine().toUpperCase();
        if (resposta.equals("S")) {
            imprimirHistorico();
        }
    }

    // ====================================================================
    // GERENCIAMENTO DE TURNO HUMANO (Implementação da Atividade 4)
    // ====================================================================

    private void gerenciarTurnoHumano(List<Personagem> meuTime, List<Personagem> timeOponente, String nomeJogador) {
        System.out.println("\n    --- TURNO DE " + nomeJogador + " ---");

        // 1. ESCOLHER PERSONAGEM
        Personagem ativo = selecionarPersonagemAtivo(meuTime);
        if (ativo == null) {
            System.out.println(nomeJogador + " não tem personagens vivos para jogar.");
            return;
        }

        // 2. REALIZAR MOVIMENTO (WASD)
        realizarMovimentoHumano(ativo);

        // 3. REALIZAR ATAQUE
        realizarAtaqueHumano(ativo, timeOponente);
    }

    /**
     * Sub-método para o Jogador escolher qual personagem usar (Atividade 4).
     */
    private Personagem selecionarPersonagemAtivo(List<Personagem> meuTime) {
        while (true) {
            System.out.println("Escolha seu personagem para este turno:");
            for (int i = 0; i < meuTime.size(); i++) {
                Personagem p = meuTime.get(i);
                System.out.printf("%d. %s (Casa: %s, Vida: %d) - Pos: %s, Alcance: %d\n",
                        i + 1, p.getNomePersonagem(), p.getCasaPersonagem().getNome(), p.getVidaAtual(), p.getPosicaoPersonagem(), p.getAlcanceMaximo());
            }
            System.out.print("Opção: ");
            int escolha = -1;
            try {
                // Lê a linha inteira para evitar problemas de buffer
                escolha = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                // Captura se o usuário digitar texto
            }

            if (escolha >= 0 && escolha < meuTime.size()) {
                return meuTime.get(escolha); // Retorna a escolha válida
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Sub-método para o Jogador mover com WASD (Atividade 4 e 5).
     */
    private void realizarMovimentoHumano(Personagem ativo) {
        boolean movimentoValido = false;
        Posicao posInicial = new Posicao(ativo.getLinha(), ativo.getColuna()); // Guarda pos inicial

        while (!movimentoValido) {
            System.out.print("Mover " + ativo.getNomePersonagem() + " (W,A,S,D) ou P (Pular): ");
            String direcao = scanner.nextLine().toUpperCase();

            if (direcao.equals("P")) return; // Pula o movimento

            int novaLinha = ativo.getLinha();
            int novaColuna = ativo.getColuna();

            if (direcao.equals("W")) novaLinha--;
            else if (direcao.equals("S")) novaLinha++;
            else if (direcao.equals("A")) novaColuna--;
            else if (direcao.equals("D")) novaColuna++;
            else {
                System.out.println("Direção inválida. Use W, A, S, D ou P.");
                continue; // Pede a direção novamente
            }

            // O Tabuleiro valida se o movimento é legal (limites, ocupação)
            movimentoValido = tabuleiro.moverPersonagem(ativo, novaLinha, novaColuna);

            if (movimentoValido) {
                // Registra o movimento (Atividade 5)
                historicoJogadas.add(new RegistroJogada(
                        turnoAtual,
                        ativo,
                        posInicial, // Posição antes de mover
                        ativo.getPosicaoPersonagem() // Posição nova
                ));
            }
            // Se 'movimentoValido' for false, o loop repete e pede nova direção
        }
    }

    /**
     * Sub-método para o Jogador escolher um alvo e atacar (Atividade 4).
     */
    private void realizarAtaqueHumano(Personagem ativo, List<Personagem> timeOponente) {
        // Encontra alvos VIVOS no alcance
        List<Personagem> alvosNoAlcance = new ArrayList<>();
        for (Personagem inimigo : timeOponente) {
            if (inimigo.estaVivo()) { // Só considera alvos vivos
                int distancia = tabuleiro.calcularDistancia(ativo, inimigo);
                if (distancia <= ativo.getAlcanceMaximo()) {
                    alvosNoAlcance.add(inimigo);
                }
            }
        }

        // Se não há alvos, o turno de ataque termina (Atividade 4)
        if (alvosNoAlcance.isEmpty()) {
            System.out.println(ativo.getNomePersonagem() + " não tem alvos no alcance.");
            return;
        }

        Personagem alvo = null;
        // Se só há um alvo, ataca direto (Atividade 4)
        if (alvosNoAlcance.size() == 1) {
            alvo = alvosNoAlcance.get(0);
            System.out.println("Alvo único no alcance: " + alvo.getNomePersonagem() + ". Atacando automaticamente.");
        } else {
            // Permite ao jogador escolher o alvo
            while (alvo == null) {
                System.out.println(ativo.getNomePersonagem() + " - Escolha seu alvo:");
                for (int i = 0; i < alvosNoAlcance.size(); i++) {
                    Personagem p = alvosNoAlcance.get(i);
                    System.out.printf("%d. %s (Vida: %d) - Dist: %d\n",
                            i + 1, p.getNomePersonagem(), p.getVidaAtual(), tabuleiro.calcularDistancia(ativo, p));
                }
                System.out.print("Opção: ");
                int escolha = -1;
                try {
                    escolha = Integer.parseInt(scanner.nextLine()) - 1;
                } catch (NumberFormatException e) {
                    // Ignora
                }

                if (escolha >= 0 && escolha < alvosNoAlcance.size()) {
                    alvo = alvosNoAlcance.get(escolha);
                } else {
                    System.out.println("Opção inválida.");
                }
            }
        }

        // Realiza o ataque (chamando a classe Acoes)
        int danoCausado = Acoes.atacar(ativo, alvo, tabuleiro);

        // Registra o ataque (Atividade 5)
        historicoJogadas.add(new RegistroJogada(
                turnoAtual,
                ativo,
                alvo,
                danoCausado,
                alvo.getVidaAtual()
        ));
    }


    // ====================================================================
    // MÉTODOS UTILITÁRIOS (Replay - Atividade 6)
    // ====================================================================

    private void imprimirHistorico() {
        System.out.println("\n===== HISTÓRICO DE JOGADAS =====");
        if(historicoJogadas.isEmpty()){
            System.out.println("Nenhuma jogada registrada.");
        }
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