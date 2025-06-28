package Classes.Musica;

import javafx.application.Platform;
import javafx.scene.control.Button;

public class TextoMusicalParser {

    // Variáveis para controlar o estado da reprodução
    // volatile -> todas as threads conseguem ver
    private volatile boolean tocando;
    private volatile boolean pausado;
    private Thread threadReproducao;

    public TextoMusicalParser() {
        tocando = false;
        pausado = false;
    }

    public void play(String input, Button playButton, Button pauseButton) {
        if (tocando) {
            stop();
        }

        // Start inicial flags de controle
        tocando = true;
        pausado = false;

        // Nova thread
        threadReproducao = new Thread(() -> {
            Platform.runLater(() -> playButton.setText("Parar"));
            // Metodo roda em segundo plano
            interpret(input);

            Platform.runLater(() -> playButton.setText("Play"));
            pauseButton.setDisable(true);

            tocando = false;
        });

        threadReproducao.start();
    }

    public void togglePause(Button pauseButton) {
        pausado = !pausado;

        // Atualiza o texto do botão para refletir o estado atual
        if (pausado) {
            Platform.runLater(() -> pauseButton.setText("Retomar"));
        } else {
            Platform.runLater(() -> pauseButton.setText("Pause"));
        }
    }

    public void stop() {
        tocando = false;
        pausado = false;

        // Espera um pouco para a thread terminar, se necessário
        if (threadReproducao != null) {
            try {
                threadReproducao.join(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    // Tive que colocar isso pra interligar a interface
    public boolean isTocando() {
        return tocando;
    }

    // controlado pelos botoes
    private void interpret(String input) {
        boolean notaAnterior = false;
        String[] musica = input.split("");

        for (int i = 0; i < musica.length; i++) {

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