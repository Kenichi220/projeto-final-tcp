package Classes.Musica;

import javafx.application.Platform;
import javafx.scene.control.Button;

public class TextoMusicalParser {

    // Variáveis para controlar o estado da reprodução
    // volatile -> todas as threads veem
    private volatile boolean tocando;
    private volatile boolean pausado;
    private Thread threadReproducao;

    public TextoMusicalParser() {
        this.tocando = false;
        this.pausado = false;
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
        tocando = false; // Sinaliza para a thread parar
        pausado = false; // Garante que saia do estado de pausa

        // Espera um pouco para a thread terminar, se necessário
        if (threadReproducao != null) {
            try {
                threadReproducao.join(100); // Espera no máximo 100ms
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
                    Musica.tocar_som(Notas.LA, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "B": case "b":
                    Musica.tocar_som(Notas.SI, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "C": case "c":
                    Musica.tocar_som(Notas.DO, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "D": case "d":
                    Musica.tocar_som(Notas.RE, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "E": case "e":
                    Musica.tocar_som(Notas.MI, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "F": case "f":
                    Musica.tocar_som(Notas.FA, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case "G": case "g":
                    Musica.tocar_som(Notas.SOL, Musica.TOCAR_NOTA);
                    notaAnterior = true;
                    break;
                case " ":
                    Musica.pausa();
                    notaAnterior = false;
                    break;
                case "+":
                    Musica.alterar_volume(Musica.AUMENTA_VOLUME);
                    notaAnterior = false;
                    break;
                case "-":
                    Musica.alterar_volume(Musica.DIMINUI_VOLUME);
                    notaAnterior = false;
                    break;
                case "O": case "o": case "I": case "i": case "U": case "u":
                    if(notaAnterior == true){
                        Musica.tocar_som(0, Musica.NOTA_ANTERIOR);
                    }
                    else{
                        Musica.tocar_som(Notas.TELEFONE, Musica.TOCAR_NOTA);
                        notaAnterior = true;
                    }
                    break;

                case "R":
                    if (i + 1 < musica.length) {
                        if (musica[i + 1].equals("+"))
                            Musica.alterar_oitava(Musica.AUMENTA_OITAVA);
                        else if (musica[i + 1].equals("-"))
                            Musica.alterar_oitava(Musica.DIMINUI_OITAVA);
                        i++;
                    }
                    notaAnterior = false;
                    break;

                case "?":
                    Musica.tocar_som(Musica.NOTA_ALEATORIA, Musica.NOTA_ALEATORIA);
                    notaAnterior = true;
                    break;

                case ";":
                    Musica.alterar_bpm(Musica.BPM_ALEATORIO);
                    notaAnterior = false;
                    break;

                case "\n":

                    Musica.trocar_instrumento_random();
                    notaAnterior = false;
                    break;

                //Trata do default e do BPM+
                default:
                    notaAnterior = false;
                    if (i + 3 < musica.length && musica[i].equals("B") && musica[i + 1].equals("P") && musica[i + 2].equals("M") && musica[i + 3].equals("+")) {
                        Musica.alterar_bpm(Musica.AUMENTA_BPM);
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