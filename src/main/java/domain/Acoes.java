package domain;

// Importa a classe Math para operações como Math.abs, Math.max, Math.round
import java.lang.Math;


// ver melhor de onde vem o personagem atacante e o personagem alvo

public class Acoes {

    //VERIFICANDO A POSSIBILIDADE DO ATAQUE ANTES DE ATACAR / DEFENDER
    public static void atacar(Personagem atacante, Personagem alvo, Tabuleiro tabuleiro) {

                // O alvo é exatamente àquele que estiver no alcançe, calculado pela classe JOGO, tabuleiro ou posiçao
        // 1. VERIFICANDO ALCANCE

        // Cálculo da Distância
            // garantindo que o resultado seja sempre positivo
        int dLinha = Math.abs(atacante.getLinha() - alvo.getLinha());
        int dColuna = Math.abs(atacante.getColuna() - alvo.getColuna());

        // distância final → aplicação das regras específicas do documento para o cálculo da distância
        int distancia = Math.max(dLinha, dColuna);


        // Comparando a distância com o alcance máximo do personagem daquela casa
        int alcance = atacante.getCasaPersonagem().getAlcanceMaximo();

        if (distancia > alcance) {
            System.out.println(atacante.getNomePersonagem() + " está fora do alcance para atacar " +
                    alvo.getNomePersonagem() + " (Distância: " + distancia + ", Alcance: " + alcance + ")");
            return;
        }

            /* CASO ESTEJA NO ALCANÇE, EU POSSO ATACAR, E OUTRO DEFENDER, LOGO:
                 Chamamos:
                  o personagem + casa do personagem =
                        características de defesa + características de ataque
                    */





        // 2. CÁLCULO DO DANO E DEFESA

            // personagem + casa = defesa e ataque


            // ATACANDO


        // chamando
        // Para o atacante, as características da sua casa são:
        Casa casaAtacante = atacante.getCasaPersonagem();
        Casa casaAlvo = alvo.getCasaPersonagem(); // para o defensor

                // Dano base (Ataque Base + Modificador Ofensivo): só para esses 2 casos
        // Por exemplo: se é um ataque da casa STARK, temos: 20 * (1 + 0)
        // Por exemplo: se é um ataque da casa LANNISTER, temos: 20 * (1 + 0.15)
                // Caso especial em TARGARYEN

        double ataque = casaAtacante.getAtaqueBase() * (1 + casaAtacante.getModificadorOfensivo());
        // Caso especial dessa casa (havia esquecido): TARGARYEN ignora defesa
        if (casaAtacante.getNome().equalsIgnoreCase("TARGARYEN")) {
            defesa = 0;
            System.out.println("  (TARGARYEN ignora a defesa!)");
        }

        /* esse equals compara a String na qual ele é chamado com a outraString
        fornecida como argumento, ignorando a diferença entre letras maiúsculas e minúsculas. */








         // DEFESA

        // Defesa inicial
        double defesa = casaAlvo.getDefesaBase();
        // A defesa é a mesma para quase todos, mas há um caso especial


        // Dano Bruto, aqui, sem considerar o modificador defensivo
        double danoBruto = ataque - defesa;


        // ÚNICO CASO ESPECIAL PARA DEFESA: Modificador Defensivo (LANNISTER)
            // com o modificador defensivo: aplicado somente à STARK
        // Código mais "eficiente", mas menos POO
        if (casaAlvo.getNome().equalsIgnoreCase("STARK")) {
            danoBruto *= 0.80; // Aplica a redução de 20% (1 - 0,20 = 0.80)
            System.out.println("  (Defensor da casa STARK reduziu o dano em 20%.)");
        }

        /*
            se o caso for melhor eficiência na aplicação, eu posso usar:
        if (casaAlvo.getModificadorDefensivo() > 0) {
            danoBruto = danoBruto * (1 - casaAlvo.getModificadorDefensivo()); // * 0,80 = 80% do dano bruto
            System.out.println("  (Defensor da casa " + casaAlvo.getNome() + " reduziu o dano.)");
        }*/

        // Garante que o dano não seja negativo
        double danoFinal = Math.max(danoBruto, 0);
        int danoArredondado = (int) Math.round(danoFinal);

        // 3. APLICAÇÃO DO DANO E ATUALIZAÇÃO DE VIDA

        int vidaAnterior = alvo.getVidaAtual(); // precisava subtrair a vida antiga, pelo dano total do momento (arredondado)
        int novaVida = vidaAnterior - danoArredondado;

        // Garante que a vida não seja negativa
        if (novaVida < 0) {
            novaVida = 0;
        }

        // atualizando os acontecimentos para quem recebeu o ataque
        alvo.setVidaAtual(novaVida);



        System.out.println(atacante.getNomePersonagem() + " atacou " + alvo.getNomePersonagem() +
                " e causou " + danoArredondado + " de dano!");

        System.out.println("  " + alvo.getNomePersonagem() + " [Vida: " + vidaAnterior + " -> " + novaVida + "]");







                // Usar isso mais tarde
        // 4. VERIFICAÇÃO DE MORTE E REMOÇÃO
        if (novaVida <= 0) {
            System.out.println("\n*** " + alvo.getNomePersonagem() + " FOI DERROTADO! ***");
            // Remove o personagem do Tabuleiro
            tabuleiro.removerPersonagem(alvo);



        }
    }
}