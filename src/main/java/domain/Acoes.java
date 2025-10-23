package domain;

import java.lang.Math;

public class Acoes {

    // ====================================================================
    // 1. MÉTODO PRINCIPAL DE COMBATE: ATACAR
    // ====================================================================

    /**
     * Realiza a ação de ataque entre dois personagens.
     * Assume que 'atacante' e 'alvo' são objetos válidos e estão ativos no jogo.
     */
    public static void atacar(Personagem atacante, Personagem alvo, Tabuleiro tabuleiro) {

        // 1. Verificação de Alcance (A ÚNICA REGRA DE VALIDAÇÃO DE AÇÃO)
        if (!verificarCondicoesAtaque(atacante, alvo, tabuleiro)) {
            return;
        }

        // 2. Calcular Dano
        double dano = calcularDano(atacante, alvo);

        // 3. Aplicar Dano
        receberDano(alvo, dano, tabuleiro);

        // 4. Feedback
        System.out.println(atacante.getNomePersonagem() + " atacou " + alvo.getNomePersonagem() +
                " e causou " + String.format("%.1f", dano) + " de dano!");
    }

    // ====================================================================
    // 2. MÉTODOS DE APOIO E LÓGICA DE REGRAS
    // ====================================================================

    /**
     * Verifica se o alvo está dentro do alcance máximo do atacante.
     * (Requer o método Tabuleiro.calcularDistancia e Personagem.getCasaPersonagem().getAlcanceMaximo)
     */
    private static boolean verificarCondicoesAtaque(Personagem atacante, Personagem alvo, Tabuleiro tabuleiro) {

        // A camada 'Jogo' deve garantir que atacante e alvo não são nulos.

        int distancia = tabuleiro.calcularDistancia(atacante, alvo);
        int alcance = atacante.getCasaPersonagem().getAlcanceMaximo();

        if (distancia > alcance) {
            System.out.println(atacante.getNomePersonagem() + " está fora do alcance para atacar " +
                    alvo.getNomePersonagem() + " (Distância: " + distancia + ", Alcance: " + alcance + ")");
            return false;
        }

        return true;
    }

    /**
     * Calcula o dano base, aplicando modificadores e regras de Casa (Targaryen, Stark).
     */
    private static double calcularDano(Personagem atacante, Personagem alvo) {
        Casa casaAtacante = atacante.getCasaPersonagem();
        Casa casaAlvo = alvo.getCasaPersonagem();

        // 1. Cálculo de Ataque e Defesa Base
        // Ataque: Base + Bônus Ofensivo (ex: LANNISTER tem 0% -> 1.0; STARK tem 20% -> 1.2)
        double ataque = casaAtacante.getAtaqueBase() * (1 + casaAtacante.getModificadorOfensivo());
        double defesa = casaAlvo.getDefesaBase();

        // 2. Aplica regra da Casa Targaryen (IGNORA DEFESA)
        if (casaAtacante.getNome().equalsIgnoreCase("TARGARYEN")) {
            defesa = 0;
            System.out.println("TARGARYEN ignora a defesa do alvo!");
        }

        double dano = ataque - defesa;

        // 3. Aplica regra da Casa Stark (REDUZ DANO RECEBIDO)
        if (casaAlvo.getNome().equalsIgnoreCase("STARK")) {
            // Stark tem 0.20 (20%) de modificador defensivo
            dano *= (1 - casaAlvo.getModificadorDefensivo());
            System.out.println("STARK reduziu o dano recebido em " + (casaAlvo.getModificadorDefensivo() * 100) + "%.");
        }

        // Garante que o dano mínimo seja 0
        return Math.max(dano, 0);
    }

    /**
     * Aplica o dano, atualiza a vida e, se a vida chegar a zero, remove o personagem do tabuleiro.
     * (Requer os métodos Personagem.setVidaAtual e Tabuleiro.removerPersonagem)
     */
    public static void receberDano(Personagem alvo, double dano, Tabuleiro tabuleiro) {
        // Arredonda o dano calculado para um inteiro
        int danoArredondado = (int) Math.round(dano);

        int vidaAnterior = alvo.getVidaAtual();
        // Garante que a vida nunca fique abaixo de zero
        int novaVida = Math.max(vidaAnterior - danoArredondado, 0);

        alvo.setVidaAtual(novaVida);

        if (novaVida <= 0) {
            System.out.println("\n*** " + alvo.getNomePersonagem() + " FOI DERROTADO! ***");
            tabuleiro.removerPersonagem(alvo);
        } else {
            System.out.println(alvo.getNomePersonagem() + " [Vida: " + vidaAnterior + " -> " + novaVida + "]");
        }
    }
}