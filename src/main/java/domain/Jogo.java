package domain;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Jogo {

    private Tabuleiro tabuleiro;
    private Scanner scanner;

    // Usamos List para gerenciar personagens vivos
    private List<Personagem> time1;
    private List<Personagem> time2;
    private final int MAX_PERSONAGENS = 3;

    private enum ModoJogo {
        HUMANO_VS_HUMANO, HUMANO_VS_BOT
    }

    public Jogo() {
        this.tabuleiro = new Tabuleiro();
        this.scanner = new Scanner(System.in);
        this.time1 = new ArrayList<>();
        this.time2 = new ArrayList<>();
    }

    // Método Público chamado pela Main.java
    public void iniciar() {
        System.out.println("=========================================");
        System.out.println("     Início do Jogo - A Batalha de Westeros");
        System.out.println("=========================================");

        // CORREÇÃO: A variável 'modo' é declarada e inicializada aqui.
        ModoJogo modo = selecionarModo();

        // 1. Configuração dos Times
        configurarTimeHumano(time1, "JOGADOR 1 (STARK)");
        if (modo == ModoJogo.HUMANO_VS_HUMANO) {
            configurarTimeHumano(time2, "JOGADOR 2 (LANNISTER)");
        } else {
            configurarTimeBot(time2, "BOT (TARGARYEN)");
        }

        // 2. Posicionar no Tabuleiro
        posicionarPersonagensIniciais();

        // 3. Iniciar Partida
        iniciarPartida(modo);
    }

    private ModoJogo selecionarModo() {
        int escolha = 0;
        while (escolha != 1 && escolha != 2) {
            System.out.println("\nEscolha o Modo de Jogo:");
            System.out.println("1. Jogador Humano vs. Jogador Humano");
            System.out.println("2. Jogador Humano vs. Bot (Máquina)");
            System.out.print("Sua escolha: ");
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
            }
            scanner.nextLine();
            if (escolha != 1 && escolha != 2) {
                System.out.println("Opção inválida.");
            }
        }
        return escolha == 1 ? ModoJogo.HUMANO_VS_HUMANO : ModoJogo.HUMANO_VS_BOT;
    }

    private void configurarTimeHumano(List<Personagem> time, String nomeJogador) {
        System.out.println("\n--- Configuração de " + nomeJogador + " ---");

        Casa casaPadrao = nomeJogador.contains("STARK") ? Casa.getSTARK() : Casa.getLANNISTER();

        for (int i = 0; i < MAX_PERSONAGENS; i++) {
            System.out.print("Digite o nome para o Personagem " + (i + 1) + " (Casa " + casaPadrao.getNome() + "): ");
            String nome = scanner.nextLine();

            time.add(new Personagem(nome, casaPadrao, new Posicao(0, 0)));
        }
    }

    private void configurarTimeBot(List<Personagem> time, String nomeJogador) {
        System.out.println("\n--- Configuração de " + nomeJogador + " (Automático) ---");
        Casa casaBot = Casa.getTARGARYEN();

        time.add(new Personagem("Dragao", casaBot, new Posicao(0, 0)));
        time.add(new Personagem("Drogon", casaBot, new Posicao(0, 0)));
        time.add(new Personagem("Viserion", casaBot, new Posicao(0, 0)));

        System.out.println("Time do Bot (" + casaBot.getNome() + ") criado com sucesso.");
    }

    private void posicionarPersonagensIniciais() {
        // Time 1: Linha 0 (Cima)
        for (int i = 0; i < MAX_PERSONAGENS; i++) {
            tabuleiro.adicionarPersonagem(time1.get(i), 0, i * (tabuleiro.TAMANHO / MAX_PERSONAGENS) + 1);
        }
        // Time 2: Linha 9 (Baixo)
        for (int i = 0; i < MAX_PERSONAGENS; i++) {
            tabuleiro.adicionarPersonagem(time2.get(i), tabuleiro.TAMANHO - 1, i * (tabuleiro.TAMANHO / MAX_PERSONAGENS) + 1);
        }
    }

    private void iniciarPartida(ModoJogo modo) {
        System.out.println("\n--- INICIANDO COMBATE ---");

        List<Personagem> timeAtivo = time1;
        List<Personagem> timePassivo = time2;
        String nomeJogadorAtivo = "JOGADOR 1";

        while (time1.size() > 0 && time2.size() > 0) {

            RegistroJogada.imprimirSituacaoAtual(tabuleiro, time1, time2, nomeJogadorAtivo);

            // Gerencia o Turno
            if (nomeJogadorAtivo.equals("JOGADOR 1") || (nomeJogadorAtivo.equals("JOGADOR 2") && modo == ModoJogo.HUMANO_VS_HUMANO)) {
                gerenciarTurnoHumano(timeAtivo, timePassivo, nomeJogadorAtivo);
            } else { // BOT
                // Converte List para Array para a classe Bot
                new Bot().realizarJogada(tabuleiro, timeAtivo.toArray(new Personagem[0]), timePassivo.toArray(new Personagem[0]));
            }

            // Remove os personagens que morreram durante o turno
            removerDerrotados();

            // Troca os times para o próximo turno
            List<Personagem> temp = timeAtivo;
            timeAtivo = timePassivo;
            timePassivo = temp;

            nomeJogadorAtivo = proximoJogador(modo, nomeJogadorAtivo);

            RegistroJogada.solicitarProximaJogada(scanner);
        }

        // FIM DE JOGO
        if (time2.isEmpty()) {
            System.out.println("\n*** FIM DE JOGO! VENCEDOR: JOGADOR 1! ***");
        } else {
            System.out.println("\n*** FIM DE JOGO! VENCEDOR: " + (modo == ModoJogo.HUMANO_VS_HUMANO ? "JOGADOR 2" : "BOT") + "! ***");
        }
    }

    private String proximoJogador(ModoJogo modo, String atual) {
        if (atual.equals("JOGADOR 1")) {
            return (modo == ModoJogo.HUMANO_VS_HUMANO) ? "JOGADOR 2" : "BOT";
        } else {
            return "JOGADOR 1";
        }
    }

    private void removerDerrotados() {
        time1.removeIf(p -> !p.estaVivo());
        time2.removeIf(p -> !p.estaVivo());
    }

    private void gerenciarTurnoHumano(List<Personagem> meuTime, List<Personagem> timeOponente, String nomeJogador) {
        System.out.println("\n--- AÇÃO DE " + nomeJogador + " ---");

        Personagem ativo = selecionarPersonagem(meuTime);
        if (ativo == null) return;

        // 1. Movimento
        realizarMovimento(ativo);

        // 2. Ataque
        System.out.println("\nATAQUE - " + ativo.getNomePersonagem());
        System.out.print("Deseja atacar? (S/N): ");
        String querAtacar = scanner.nextLine().toUpperCase();

        if (querAtacar.equals("S")) {
            Personagem alvo = selecionarAlvo(ativo, timeOponente);
            if (alvo != null) {
                Acoes.atacar(ativo, alvo, tabuleiro);
            } else {
                System.out.println("Nenhum alvo válido selecionado ou no alcance.");
            }
        }
    }

    private Personagem selecionarPersonagem(List<Personagem> time) {
        Personagem ativo = null;
        while (ativo == null) {
            System.out.println("Selecione o Personagem para o turno:");
            for (int i = 0; i < time.size(); i++) {
                System.out.println((i + 1) + ". " + time.get(i).getNomePersonagem() + " (Vida: " + time.get(i).getVidaAtual() + ")");
            }
            System.out.print("Opção: ");
            if (scanner.hasNextInt()) {
                int escolha = scanner.nextInt();
                scanner.nextLine();
                if (escolha > 0 && escolha <= time.size()) {
                    ativo = time.get(escolha - 1);
                }
            } else {
                scanner.nextLine();
            }
            if (ativo == null) System.out.println("Seleção inválida.");
        }
        return ativo;
    }

    private void realizarMovimento(Personagem ativo) {
        boolean movimentoRealizado = false;
        while (!movimentoRealizado) {
            System.out.print("Escolha a direção (W=Cima, A=Esq, S=Baixo, D=Dir ou M=Pular): ");
            String direcao = scanner.nextLine().toUpperCase();

            if (direcao.equals("M")) {
                movimentoRealizado = true;
                continue;
            }

            int novaLinha = ativo.getLinha();
            int novaColuna = ativo.getColuna();

            switch (direcao) {
                case "W": novaLinha--; break;
                case "S": novaLinha++; break;
                case "A": novaColuna--; break;
                case "D": novaColuna++; break;
                default: System.out.println("Opção de movimento inválida!"); continue;
            }

            movimentoRealizado = tabuleiro.moverPersonagem(ativo, novaLinha, novaColuna);
        }
    }

    private Personagem selecionarAlvo(Personagem atacante, List<Personagem> timeOponente) {
        List<Personagem> alvosNoAlcance = new ArrayList<>();

        for (Personagem p : timeOponente) {
            if (tabuleiro.calcularDistancia(atacante, p) <= atacante.getCasaPersonagem().getAlcanceMaximo()) {
                alvosNoAlcance.add(p);
            }
        }

        if (alvosNoAlcance.isEmpty()) {
            System.out.println("Nenhum inimigo no alcance!");
            return null;
        }

        System.out.println("Selecione o Alvo:");
        for (int i = 0; i < alvosNoAlcance.size(); i++) {
            Personagem alvo = alvosNoAlcance.get(i);
            int dist = tabuleiro.calcularDistancia(atacante, alvo);
            System.out.println((i + 1) + ". " + alvo.getNomePersonagem() +
                    " (Casa: " + alvo.getCasaPersonagem().getNome() +
                    ", Vida: " + alvo.getVidaAtual() + ", Distância: " + dist + ")");
        }
        System.out.print("Opção: ");

        if (scanner.hasNextInt()) {
            int escolha = scanner.nextInt();
            scanner.nextLine();
            if (escolha > 0 && escolha <= alvosNoAlcance.size()) {
                return alvosNoAlcance.get(escolha - 1);
            }
        }
        scanner.nextLine();
        return null;
    }

}