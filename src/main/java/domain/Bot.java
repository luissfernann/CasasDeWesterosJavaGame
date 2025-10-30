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
                              List<RegistroJogada> historicoJogadas, // Adicionado para Log
                              int turnoAtual) {                       // Adicionado para Log
        Personagem atacanteBot = escolherPersonagemAleatorio(timeBot);


        if (atacanteBot == null) {
            System.out.println("Sem mais personagens vivos para jogar.");
            // considerando que todos já estejam mortos
            return;
        }

        System.out.println("--- Turno do Bot: " + atacanteBot.getNomePersonagem() + " (Casa: " + atacanteBot.getCasaPersonagem().getNome() + ") ---");

        // LINHAS NOVAS PARA LOG DE MOVIMENTO: Captura a posição inicial e o resultado
        Posicao posInicial = new Posicao(atacanteBot.getLinha(), atacanteBot.getColuna());

        Posicao posFinal = realizarMovimentoAleatorio(tabuleiro, atacanteBot);

        // Registro de Movimento
        if (posFinal != null) {
            historicoJogadas.add(new RegistroJogada(turnoAtual, atacanteBot, posInicial, posFinal));
        }

        // qualquer movimento aleatório → depois calcular o que podemos fazer com esse movimento
        System.out.println("Bot (" + atacanteBot.getNomePersonagem() + ") procurando alvo...");






        // 3. AÇÃO MEU FI
        // teríamos uma variável do tipo: classe Personagem: recebendo um personagem
        Personagem alvoIdeal = buscarAlvoMaisProximo(atacanteBot, timeJogador, tabuleiro);

        if (alvoIdeal != null) { // quer dizer que não está morto

            // tendo um bot escolhido aleatóriamente, e um personagem mais próximo, posso calcular a distância para validar ou não as ações
            int distancia = tabuleiro.calcularDistancia(atacanteBot, alvoIdeal);


            // se a distância estiver dentro do meu alcance máximo, tome
            if (distancia <= atacanteBot.getCasaPersonagem().getAlcanceMaximo()) {
                System.out.println(atacanteBot.getNomePersonagem() + " ATACA " + alvoIdeal.getNomePersonagem() +
                        " (Distância: " + distancia + ", Alcance: " + atacanteBot.getCasaPersonagem().getAlcanceMaximo() + ")");

                // Chama a lógica de combate e captura o dano para o log
                int danoCausado = Acoes.atacar(atacanteBot, alvoIdeal, tabuleiro);

                // Registro de Ataque
                historicoJogadas.add(new RegistroJogada(
                        turnoAtual,
                        atacanteBot,
                        alvoIdeal,
                        danoCausado,
                        alvoIdeal.getVidaAtual()
                ));

                // se não tiver, não consigo fazer nada, e temos ue adicionar um passa a vez
            } else {
                System.out.println(alvoIdeal.getNomePersonagem() + " é o mais próximo, mas está fora de alcance (Distância: " + distancia + ").");
            }


        } else {
            System.out.println("Nenhum alvo inimigo vivo encontrado no timeJogador.");
        }
    }










    private Personagem buscarAlvoMaisProximo(Personagem atacante, List<Personagem> timeJogador, Tabuleiro tabuleiro) {
        Personagem alvoIdeal = null;

        // para armazenar a menor distância até então
        // isso comecará com o maior valor do atributo MAX pertencente à classe empacotadora integer : vamos fazer isso para ir diminuindo a distancia gradualmente
        int minDistancia = Integer.MAX_VALUE;


        for (Personagem inimigo : timeJogador) {
            // vamos fazer um loop chamando de inimigo: esse looping irá ler todos os personagens da lista timeJogador
            // e irá verificar para cada um deles a distância mais próxima


            // isso deverá servir apenas para personagens vivos, já que iremos atualizar o tabuleiro, removendo personagens mortos
            if (inimigo != null && inimigo.estaVivo()) {
                //  se está vivo, chamamos a classe tabuleiro para calcular a distância daquele jogador daquele índice

                // atacante é o personagem da vez daquele turno
                int distancia = tabuleiro.calcularDistancia(atacante, inimigo);

                if (distancia < minDistancia) {   // atualiza a distância para um número menor DAQUELE PERSONAGEM do índice x
                    minDistancia = distancia;    // atualiza o valor da distância mínima ⇒ volta ela para o for
                    alvoIdeal = inimigo;        // passa o nome do inimigo(timeJogador) com a distância atualizada
                }
            }


        }

        return alvoIdeal;
    }










    private Personagem escolherPersonagemAleatorio(List<Personagem> timeBot) {
        if (timeBot.isEmpty()) {
            return null;
        }

        return timeBot.get(random.nextInt(timeBot.size()));
    }







    //  VER MELHOR COMO FUNCIONA ISSO AQUI
    // O método retorna a Posicao (ou null) para o log de jogadas
    private Posicao realizarMovimentoAleatorio(Tabuleiro tabuleiro, Personagem personagem) {
        int linhaAtual = personagem.getLinha();
        int colunaAtual = personagem.getColuna();

        // Lista contendo os 8 possíveis deslocamentos [dLinha, dColuna]
        List<int[]> direcoes = new ArrayList<>();

        // Preenche todas as 8 direções (inclusive diagonais)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) { // Garante que não é [0, 0] (ficar parado)
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
                System.out.println("Bot moveu " + personagem.getNomePersonagem() + " para [" + novaLinha + ", " + novaColuna + "].");

                // Retorna a posição final para o RegistroJogada
                return new Posicao(novaLinha, novaColuna);
            }
        }

        // Se o loop terminou sem um movimento válido
        System.out.println(personagem.getNomePersonagem() + " não conseguiu se mover (cercado ou sem opções válidas).");
        return null; // Retorna null para indicar que não houve movimento
    }
}