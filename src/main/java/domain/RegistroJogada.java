package domain;

public class RegistroJogada {
    public enum TipoAcao {
        MOVIMENTO,
        ATAQUE
    }

    private final TipoAcao tipo;
    private final Personagem autor;
    private final int numeroDoTurno;
    private final Posicao posInicial;
    private final Posicao posFinal;

    private final Personagem alvo;
    private final int danoCausado;
    private final int vidaRestanteAlvo;
    private final String descricao;

    // Construtor 1: PARA O MOVIMENTO
    public RegistroJogada(int numeroDoTurno, Personagem autor, Posicao posInicial, Posicao posFinal) {
        this.tipo = TipoAcao.MOVIMENTO;
        this.numeroDoTurno = numeroDoTurno;
        this.autor = autor;
        this.posInicial = posInicial;
        this.posFinal = posFinal;

        this.alvo = null;
        this.danoCausado = 0;
        this.vidaRestanteAlvo = 0;
        this.descricao = null;
    }

    // Construtor 2: CONSIDERANDO QUE TEMOS UM ATAQUE BEM-SUCEDIDO
    public RegistroJogada(int numeroDoTurno, Personagem autor, Personagem alvo, int danoCausado, int vidaRestanteAlvo) {
        this.tipo = TipoAcao.ATAQUE;
        this.numeroDoTurno = numeroDoTurno;
        this.autor = autor;
        this.alvo = alvo;
        this.danoCausado = danoCausado;
        this.vidaRestanteAlvo = vidaRestanteAlvo;

        this.posInicial = null;
        this.posFinal = null;
        this.descricao = null;
    }

    // Construtor 3: AÇÃO FALHA/CUSTOMIZADA
    public RegistroJogada(int numeroDoTurno, Personagem autor, String descricao) {
        this.tipo = TipoAcao.ATAQUE;
        this.numeroDoTurno = numeroDoTurno;
        this.autor = autor;

        this.alvo = null;
        this.danoCausado = 0;
        this.vidaRestanteAlvo = 0;
        this.posInicial = null;
        this.posFinal = null;

        this.descricao = descricao;
    }

    @Override
    public String toString() {
        if (tipo == TipoAcao.MOVIMENTO) {
            return String.format("Turno %d: [MOVIMENTO] %s (%s) moveu de %s para %s.",
                    numeroDoTurno,
                    autor.getNomePersonagem(),
                    autor.getCasaPersonagem().getNome(),
                    posInicial,
                    posFinal);
        } else if (tipo == TipoAcao.ATAQUE && alvo != null) {
            return String.format("Turno %d: [ATAQUE] %s (%s) atacou %s (%s) causando %d de dano. (Vida restante: %d)",
                    numeroDoTurno,
                    autor.getNomePersonagem(),
                    autor.getCasaPersonagem().getNome(),
                    alvo.getNomePersonagem(),
                    alvo.getCasaPersonagem().getNome(),
                    danoCausado,
                    vidaRestanteAlvo);
        } else if (tipo == TipoAcao.ATAQUE && alvo == null && this.descricao != null) {
            // Lógica para AÇÕES FALHAS/CUSTOMIZADAS
            return String.format("Turno %d: [AÇÃO FALHA] %s (%s) %s",
                    numeroDoTurno,
                    autor.getNomePersonagem(),
                    autor.getCasaPersonagem().getNome(),
                    this.descricao);
        }
        return "    Turno " + numeroDoTurno + ": Ação desconhecida.";
    }
}