package Classes.Musica;

public class TextoMusicalParser {

    // Variáveis para controlar o estado da reprodução
    private static boolean tocando;
    private static boolean pausado;

    // controlado pelos botoes
    public static void interpret(String input) {
        boolean notaAnterior = false;
        String[] musica = input.split("");

        for (int i = 0; i < musica.length; i++) {
            tocando = Musica.getTocando();
            pausado = Musica.getPausado();
            // Como estou lidando com thread, precisei adiconar isso para quebrar o loop
            if (!tocando) {
                break;
            }

            // Verifica se deve pausar
            while (pausado && tocando) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    // Interrompe se a musica for parada
                    tocando = false;
                }
            }

            String letra_atual = musica[i];

            switch (letra_atual) {
                case "A": case "a":
                    Musica.tocarSom(Notas.LA, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "B": case "b":
                    Musica.tocarSom(Notas.SI, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "C": case "c":
                    Musica.tocarSom(Notas.DO, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "D": case "d":
                    Musica.tocarSom(Notas.RE, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "E": case "e":
                    Musica.tocarSom(Notas.MI, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "F": case "f":
                    Musica.tocarSom(Notas.FA, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "G": case "g":
                    Musica.tocarSom(Notas.SOL, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case " ":
                    Musica.pausa();
                    notaAnterior = false;
                    break;
                case "+":
                    Musica.alterarVolume(Musica.AUMENTA_VOLUME);
                    notaAnterior = false;
                    break;
                case "-":
                    Musica.alterarVolume(Musica.DIMINUI_VOLUME);
                    notaAnterior = false;
                    break;
                case "O": case "o": case "I": case "i": case "U": case "u":
                    if(notaAnterior == true){
                        Musica.tocarSom(0, Musica.NOTA_ANTERIOR);
                    }
                    else{
                        Musica.tocarSom(Notas.TELEFONE, Musica.TOCAR_NOTA);
                        notaAnterior = true;
                    }
                    break;

                case "R":
                    if (i + 1 < musica.length) {
                        if (musica[i + 1].equals("+"))
                            Musica.alterarOitava(Musica.AUMENTA_OITAVA);
                        else if (musica[i + 1].equals("-"))
                            Musica.alterarOitava(Musica.DIMINUI_OITAVA);
                        i++;
                    }
                    notaAnterior = false;
                    break;

                case "?":
                    Musica.tocarSom(Musica.NOTA_ALEATORIA, Musica.NOTA_ALEATORIA);
                    notaAnterior = true;
                    break;

                case ";":
                    Musica.alterarBPM(Musica.BPM_ALEATORIO);
                    notaAnterior = false;
                    break;

                case "\n":
                    //Instrumento aleatorio
                    Musica.trocarInstrumento(Instrumentos.instrumentoAleatorio());
                    notaAnterior = false;
                    break;

                //Trata do default e do BPM+
                default:
                    notaAnterior = false;
                    if (i + 3 < musica.length && musica[i].equals("B") && musica[i + 1].equals("P") && musica[i + 2].equals("M") && musica[i + 3].equals("+")) {
                        Musica.alterarBPM(Musica.AUMENTA_BPM);
                        i += 3;
                    }
                    else {
                        //NOP
                    }
                    break;

            }
        }
    }
}