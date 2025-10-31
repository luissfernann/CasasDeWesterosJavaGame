 package domain;

import java.util.Random;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class Bot {

    private final Random random = new Random();

    public void executarTurno(Tabuleiro tabuleiro,
                              List<Personagem> timeBot,
                              List<Personagem> timeJogador,
                              List<RegistroJogada> historicoJogadas,
                              int turnoAtual) {
        Personagem atacanteBot = escolherPersonagemAleatorio(timeBot);

        if (atacanteBot == null) {
            System.out.println("Sem mais personagens vivos para jogar.");
            return;
        }


        System.out.println("\n--- Turno do Bot: " + atacanteBot.getNomePersonagem() + " (Casa: " + atacanteBot.getCasaPersonagem().getNome() + ") ---\n");

        Posicao posInicial = new Posicao(atacanteBot.getLinha(), atacanteBot.getColuna());
        Posicao posFinal = realizarMovimentoAleatorio(tabuleiro, atacanteBot);

        if (posFinal != null) {
            historicoJogadas.add(new RegistroJogada(turnoAtual, atacanteBot, posInicial, posFinal));
        }

        System.out.println("Bot (" + atacanteBot.getNomePersonagem() + ") procurando alvo...");

        // Começando a ação
        Personagem alvoIdeal = buscarAlvoMaisProximo(atacanteBot, timeJogador, tabuleiro);

        if (alvoIdeal != null) {

            // Tendo um bot escolhido aleatóriamente, e um personagem mais próximo, posso calcular a distância para validar ou não as ações
            int distancia = tabuleiro.calcularDistancia(atacanteBot, alvoIdeal);

            if (distancia <= atacanteBot.getCasaPersonagem().getAlcanceMaximo()) {
                System.out.println(atacanteBot.getNomePersonagem() + " ATACA " + alvoIdeal.getNomePersonagem() +
                        " (Distância: " + distancia + ", Alcance: " + atacanteBot.getCasaPersonagem().getAlcanceMaximo() + ")");

                int danoCausado = Acoes.atacar(atacanteBot, alvoIdeal, tabuleiro);

                // Registro de Ataque lá para a classe Registro
                historicoJogadas.add(new RegistroJogada(
                        turnoAtual,
                        atacanteBot,
                        alvoIdeal,
                        danoCausado,
                        alvoIdeal.getVidaAtual()
                ));

            } else {
                System.out.println(alvoIdeal.getNomePersonagem() + " é o mais próximo, mas está fora de alcance (Distância: " + distancia + ").");
            }

        } else {
            System.out.println("Nenhum alvo inimigo vivo encontrado no timeJogador.");
        }
        System.out.println("\n     ---Status do Time Bot ---\n");


        for (Personagem p : timeBot) {
            System.out.printf("* %s (Casa: %s, Vida: %d) - Pos: %s, Alcance: %d\n",
                    p.getNomePersonagem(),
                    p.getCasaPersonagem().getNome(),
                    p.getVidaAtual(),
                    p.getPosicaoPersonagem(),
                    p.getAlcanceMaximo());
        }

    }

    private Personagem buscarAlvoMaisProximo(Personagem atacante, List<Personagem> timeJogador, Tabuleiro tabuleiro) {
        Personagem alvoIdeal = null;

        // Irá começar com o maior valor do atributo MAX pertencente à classe empacotadora integer: vamos fazer isso para ir diminuindo a distância gradualmente
        int minDistancia = Integer.MAX_VALUE;

        //calculando o inimigo mais proximo
        for (Personagem inimigo : timeJogador) {
            if (inimigo != null && inimigo.estaVivo()) {
                int distancia = tabuleiro.calcularDistancia(atacante, inimigo);
                if (distancia < minDistancia) {   // atualiza a distância para um número menor DAQUELE PERSONAGEM do índice x
                    minDistancia = distancia;    // atualiza o valor da distância mínima ⇒ volta ela para o for
                    alvoIdeal = inimigo;        // passa o nome do inimigo(timeJogador) com a distância atualizada
                }
            }


        }

        return alvoIdeal;
    }

    // Auxílios para os métodos acima
    private Personagem escolherPersonagemAleatorio(List<Personagem> timeBot) {
        if (timeBot.isEmpty()) {
            return null;
        }
        return timeBot.get(random.nextInt(timeBot.size()));
    }

    private Posicao realizarMovimentoAleatorio(Tabuleiro tabuleiro, Personagem personagem) {
        int linhaAtual = personagem.getLinha();
        int colunaAtual = personagem.getColuna();

        List<int[]> direcoes = new ArrayList<>();

        // Preenche todas as 8 direções (inclusive diagonais)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    direcoes.add(new int[]{i, j});
                }
            }
        }

        // Embaralha as direções para tentar um movimento aleatório
        Collections.shuffle(direcoes);

        // Tenta mover para a primeira direção válida
        for (int[] dir : direcoes) {
            int novaLinha = linhaAtual + dir[0];
            int novaColuna = colunaAtual + dir[1];

            // O Tabuleiro fará a validação de limites e ocupação
            if (tabuleiro.moverPersonagem(personagem, novaLinha, novaColuna)) {
                return new Posicao(novaLinha, novaColuna);
            }
        }

        System.out.println(personagem.getNomePersonagem() + " não conseguiu se mover (cercado ou sem opções válidas).");
        return null;
    }
}