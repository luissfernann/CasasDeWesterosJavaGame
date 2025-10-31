
package domain;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {

    private Tabuleiro tabuleiro;
    private final Scanner scanner;
    private final Bot bot;

    private final List<Personagem> time1;
    private final List<Personagem> time2;

    private final int MAX_PERSONAGENS = 3;

    private final List<RegistroJogada> historicoJogadas;
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

   // Auxílio para que o jogador possa a qualquer momento sair da partida
    private String lerEntrada(String prompt) {
        System.out.print(prompt);
        String entrada = scanner.nextLine();

        if (entrada.equalsIgnoreCase("SAIR") || entrada.equalsIgnoreCase("EXIT")) {
            System.out.println("\nEncerrando o jogo a pedido do usuário...");
            System.exit(0); // Encerra a aplicação imediatamente
        }
        return entrada;
    }

    // Inicio da Partida
    public void iniciar() {

        // Loop principal para a opção "Jogar Novamente" (Atividade 6)
        boolean jogarNovamente = true;

        while (jogarNovamente) {
            resetarJogo();

            System.out.println("\n");
            System.out.println("=========================================");
            System.out.println("  Início do Jogo - A Batalha de Westeros");
            System.out.println("=========================================");

            int escolha = 0;
            while (escolha != 1 && escolha != 2) {
                System.out.println("\nEscolha o Modo de Jogo (ou digite 'SAIR/EXIT' para encerrar o jogo):");
                System.out.println("1. Jogador Humano vs. Jogador Humano");
                System.out.println("2. Jogador Humano vs. Bot (Máquina)");

                String entrada = lerEntrada("Sua escolha: "); // Modificado

                if (entrada.equals("1")) escolha = 1;
                else if (entrada.equals("2")) escolha = 2;
                else System.out.println("Opção inválida. Por favor, digite 1 ou 2.");
            }
            // Fim da seleção de modo

            // 1. Configuração dos Personagens para o Time 1
            configurarTime(time1, "Jogador 1");
            if (escolha == 1) {
                // 2. Configuração dos Personagens para o Time 2
                configurarTime(time2, "Jogador 2");
                iniciarPartida(ModoJogo.HUMANO_VS_HUMANO);
            } else {
                // 2. Configuração dos Personagens para o Time 2
                configurarTimeBot(time2);
                iniciarPartida(ModoJogo.HUMANO_VS_BOT);
            }

            String resposta = lerEntrada("\nDeseja Jogar Novamente? (S/N): ").toUpperCase(); // Modificado
            if (!resposta.equals("S")) {
                jogarNovamente = false;
            }
        }
        System.out.println("Obrigado por jogar!");
    }

    // Configurando um time para um jogador humano
    private void configurarTime(List<Personagem> time, String nomeJogador) {
        System.out.println("\n--- Configuração de " + nomeJogador + " ---");
        Posicao posPadrao = new Posicao(0, 0);

        for (int i = 0; i < MAX_PERSONAGENS; i++) {
            Casa casaEscolhida = null;
            while (casaEscolhida == null) {
                System.out.println("\nEscolha a Casa para o Personagem " + (i + 1) + " (ou digite 'SAIR/EXIT' para encerrar o jogo):"); // Modificado
                System.out.println("1. STARK");
                System.out.println("2. LANNISTER");
                System.out.println("3. TARGARYEN");

                String escCasa = lerEntrada("Opção: "); // Modificado
                switch (escCasa) {
                    case "1": casaEscolhida = Casa.getSTARK(); break;
                    case "2": casaEscolhida = Casa.getLANNISTER(); break;
                    case "3": casaEscolhida = Casa.getTARGARYEN(); break;
                    default: System.out.println("Opção inválida.");
                }
            }

            String nome = lerEntrada("Digite o nome para o Personagem " + (i + 1) + " (Casa " + casaEscolhida.getNome() + "): "); // Modificado
            time.add(new Personagem(nome, casaEscolhida, posPadrao));
        }

    }

    // Configurando um time para o jogador Bot
    private void configurarTimeBot(List<Personagem> time) {
        System.out.println("\nConfiguração automática de Bot (Máquina)");
        Casa[] casas = {Casa.getSTARK(), Casa.getLANNISTER(), Casa.getTARGARYEN()};
        Random r = new Random();
        Posicao posPadrao = new Posicao(0, 0);

        time.add(new Personagem("Dragão", casas[r.nextInt(casas.length)], posPadrao));
        time.add(new Personagem("MãeDosDragões", casas[r.nextInt(casas.length)], posPadrao));
        time.add(new Personagem("Imaculado", casas[r.nextInt(casas.length)], posPadrao));

        System.out.println("Time do Bot criado com sucesso!");
    }

    // Chamando o tabuleiro para posicionar os personagens em lados opostos.
    private void posicionarPersonagens() {
        tabuleiro.posicaoPInicial(time1, time2);
        System.out.println("\n--Personagens posicionados no tabuleiro!-- ");
    }

    private void removerMortos(List<Personagem> time) {
        time.removeIf(p -> !p.estaVivo());
    }

    private String verificaVitoria() {
        if (time1.isEmpty()) return "Jogador 2/Bot";
        if (time2.isEmpty()) return "Jogador 1";
        return null;
    }

    private void resetarJogo() {
        this.tabuleiro = new Tabuleiro();
        this.time1.clear();
        this.time2.clear();
        this.historicoJogadas.clear();
        this.turnoAtual = 1;
    }

    // LOOP do Jogo
    private void iniciarPartida(ModoJogo modo) {
        posicionarPersonagens();

        System.out.println("\n-- INICIANDO PARTIDA: " + modo + " --");
        tabuleiro.ImprimeTabuleiro(); // Mostra o estado inicial

        while (verificaVitoria() == null) {
            System.out.println("\n              Turno " + turnoAtual + " ");

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
            removerMortos(time1);
            if (verificaVitoria() != null) break;

            turnoAtual++;
            tabuleiro.ImprimeTabuleiro();
        }

        System.out.println("\n** FIM DA PARTIDA! Vencedor: " + verificaVitoria() + " **");
        String resposta = lerEntrada("\nDeseja ver o Replay? (S/N): ").toUpperCase(); // Modificado
        if (resposta.equals("S")) {
            imprimirHistorico();
        }
    }

    private void gerenciarTurnoHumano(List<Personagem> meuTime, List<Personagem> timeOponente, String nomeJogador) {
        System.out.println("\n     --- Turno do " + nomeJogador + " ---");

        Personagem ativo = selecionarPersonagemAtivo(meuTime);
        if (ativo == null) {
            System.out.println(nomeJogador + " não tem personagens vivos para jogar.");
            return;
        }

        realizarMovimentoHumano(ativo);
        realizarAtaqueHumano(ativo, timeOponente);
    }

    // Para que o jogador possa escolher qual personagem ativo usar
    private Personagem selecionarPersonagemAtivo(List<Personagem> meuTime) {
        while (true) {
            System.out.println("\nEscolha seu personagem para este turno (ou digite 'SAIR/EXIT' para encerrar a partida):"); // Modificado
            for (int i = 0; i < meuTime.size(); i++) {
                Personagem p = meuTime.get(i);
                System.out.printf("%d. %s (Casa: %s, Vida: %d) - Pos: %s, Alcance: %d\n",
                        i + 1, p.getNomePersonagem(), p.getCasaPersonagem().getNome(), p.getVidaAtual(), p.getPosicaoPersonagem(), p.getAlcanceMaximo());
            }

            String entrada = lerEntrada("Opção: "); // Modificado
            int escolha = -1;
            try {
                escolha = Integer.parseInt(entrada) - 1;
            } catch (NumberFormatException e) {
            }

            if (escolha >= 0 && escolha < meuTime.size()) {
                return meuTime.get(escolha); // Retorna a escolha válida
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void realizarMovimentoHumano(Personagem ativo) {
        boolean movimentoValido = false;
        Posicao posInicial = new Posicao(ativo.getLinha(), ativo.getColuna()); // Guarda pos inicial

        while (!movimentoValido) {
            String direcao = lerEntrada("\nMover " + ativo.getNomePersonagem() + " (W,A,S,D), P (Pular) ou digite 'SAIR/EXIT' para encerrar a partida: ").toUpperCase(); // Modificado

            if (direcao.equals("P")) return;

            int novaLinha = ativo.getLinha();
            int novaColuna = ativo.getColuna();

            if (direcao.equals("W")) novaLinha--;
            else if (direcao.equals("S")) novaLinha++;
            else if (direcao.equals("A")) novaColuna--;
            else if (direcao.equals("D")) novaColuna++;
            else {
                System.out.println("Direção inválida. Use W, A, S, D ou P.");
                continue;
            }

            // O Tabuleiro valida se o movimento é legal (limites, ocupação)
            movimentoValido = tabuleiro.moverPersonagem(ativo, novaLinha, novaColuna);

            if (movimentoValido) {
                historicoJogadas.add(new RegistroJogada(
                        turnoAtual,
                        ativo,
                        posInicial,
                        ativo.getPosicaoPersonagem()
                ));
            }
            // Se 'movimentoValido' for false, o loop repete e pede nova direção
        }
    }

    // Para que o jogador possa escolher qual personagem ativo (inimigo) e atacar
    private void realizarAtaqueHumano(Personagem ativo, List<Personagem> timeOponente) {
        // Encontra alvos VIVOS no alcance
        List<Personagem> alvosNoAlcance = new ArrayList<>();
        for (Personagem inimigo : timeOponente) {
            if (inimigo.estaVivo()) {
                int distancia = tabuleiro.calcularDistancia(ativo, inimigo);
                if (distancia <= ativo.getAlcanceMaximo()) {
                    alvosNoAlcance.add(inimigo);
                }
            }
        }

        if (alvosNoAlcance.isEmpty()) {
            System.out.println(ativo.getNomePersonagem() + " não tem alvos no alcance.");
            return;
        }

        Personagem alvo = null;
        // Se só há um alvo, ataca direto
        if (alvosNoAlcance.size() == 1) {
            alvo = alvosNoAlcance.get(0);
            System.out.println("Alvo único no alcance: " + alvo.getNomePersonagem() + ". Atacando automaticamente.");
        } else {
            // Permite ao jogador escolher o alvo
            while (alvo == null) {
                System.out.println("\n" + ativo.getNomePersonagem() + " - Escolha seu alvo (ou 'SAIR'):"); // Modificado
                for (int i = 0; i < alvosNoAlcance.size(); i++) {
                    Personagem p = alvosNoAlcance.get(i);
                    System.out.printf("%d. %s (Vida: %d) - Dist: %d\n",
                            i + 1, p.getNomePersonagem(), p.getVidaAtual(), tabuleiro.calcularDistancia(ativo, p));
                }

                String entrada = lerEntrada("Opção: "); // Modificado
                int escolha = -1;
                try {
                    escolha = Integer.parseInt(entrada) - 1;
                } catch (NumberFormatException _) {
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

        historicoJogadas.add(new RegistroJogada(
                turnoAtual,
                ativo,
                alvo,
                danoCausado,
                alvo.getVidaAtual()
        ));
    }

    // Replay geral
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
