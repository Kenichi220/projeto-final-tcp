package Classes.Musica;

import javax.sound.midi.*;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;


public class Musica {
    //Numeros 1 e 2, são definidos no enunciado do trabalho
    //Constantes Musica
    private static int VOLUME_INICIAL = 50;
    private static int OITAVA_INICIAL = 0;
    private static int BPM_INICIAL = 120;
    private static int TIME_SLEEP = 100;

    //Variavies
    private static int volume = VOLUME_INICIAL;
    private static int oitava = OITAVA_INICIAL;
    private static int bpm = BPM_INICIAL;
    private static final Instrumentos instrumento_atual = new Instrumentos();
    private static final Notas  nota_atual = new Notas();
    private static final Notas  ultima_nota = new Notas();
    private static final IntegerProperty volumeProperty = new SimpleIntegerProperty(VOLUME_INICIAL);
    private static final IntegerProperty bpmProperty = new SimpleIntegerProperty(BPM_INICIAL);
    private static final IntegerProperty oitavaProperty = new SimpleIntegerProperty(OITAVA_INICIAL);
    private static final Midi midi;

    static {
        try {
            midi = new Midi();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

//---------------------------------------------------
//Constantes das Funcoes
    //Constantes Volume
    static final int AUMENTA_VOLUME = 1;
    static final int DIMINUI_VOLUME = 2;

    //Constantes Oitava
    static final int AUMENTA_OITAVA = 1;
    static final int DIMINUI_OITAVA = 2;
    static final int CONSTANTE_OITAVA = 12;

    //Constantes Bpm
    static final int TEMPO_MUSICA = 60000;
    static final int AUMENTA_BPM = 1;
    static final int BPM_ALEATORIO = 2;
    static final int BPM_ALEATORIO_MAX = 179;
    static final int AUMENTO_BPM = 80;

    //Constantes Nota
    static final int TOCAR_NOTA = 1;
    static final int NOTA_ANTERIOR = 2;
    static final int NOTA_ALEATORIA = 3;

    // volatile -> todas as threads conseguem ver
    private static volatile boolean tocando;
    private static volatile boolean pausado;
    private Thread threadReproducao;

//---------------------------------------------------
//Funcoes
    public Musica(){
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

        //Thread que interage com o PlayTab
        threadReproducao = new Thread(() -> {
            Platform.runLater(() -> playButton.setText("Parar"));
            // Metodo roda em segundo plano
            TextoMusicalParser.interpret(input);

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
                threadReproducao.join(TIME_SLEEP);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    // Tive que colocar isso pra interligar a interface
    public boolean isTocando() {
        return tocando;
    }


    private static int randomizarBPM() {
        //Vai de 1 até BPM_ALEATORIO_MAX + 1
        return  (int) (Math.random() * BPM_ALEATORIO_MAX + 1);
    }

    public static void alterarVolume(int comando){
        switch (comando){
            case AUMENTA_VOLUME:
                setVolume(VOLUME_INICIAL * 2);
                break;
            case DIMINUI_VOLUME:
                setVolume(VOLUME_INICIAL);
                break;
            default:
                setVolume(VOLUME_INICIAL);
        }
    }

    public static void alterarOitava(int comando){
        switch (comando){
            case AUMENTA_OITAVA:
                setOitava(getOitava() + 1);
                break;
            case DIMINUI_OITAVA:
                setOitava(getOitava() - 1);
                break;
            default:
                //Arrumar
        }
    }
    public static void alterarBPM(int comando){
        switch (comando){
            case AUMENTA_BPM:
               setBpm(getBpm() + AUMENTO_BPM);
               bpm = getBpm();
                break;
            case BPM_ALEATORIO:
                int bpmRandom = randomizarBPM();
                setBpm(bpmRandom);
                bpm = getBpm();
                break;
            default:
                //Arrumar
        }
    }

    public static void tocarSom(int nota, int comando){
        //Nota atual atualizada se houver nota
        if(nota != 0)
            nota_atual.setNota(nota);
        switch (comando){
            case TOCAR_NOTA:
                midi.tocar(nota_atual.getNota() + (CONSTANTE_OITAVA * getOitava()), TEMPO_MUSICA/getBpm(), getVolume());
                break;
            case NOTA_ANTERIOR:
                midi.tocar(ultima_nota.getNota() + (CONSTANTE_OITAVA * getOitava()), TEMPO_MUSICA/getBpm(), getVolume());
                break;
            case NOTA_ALEATORIA:
                nota_atual.setNota(Notas.notaAleatoria());
                midi.tocar(nota_atual.getNota() + (CONSTANTE_OITAVA * getOitava()), TEMPO_MUSICA/getBpm(), getVolume());
                break;
            default:
                //Arrumar
        }
        //Ultima nota vira a atual
        ultima_nota.setNota(nota_atual.getNota());
    }

    public static void trocarInstrumento(int instrumentoSelecionado){
        instrumento_atual.setNumero_MIDI(instrumentoSelecionado);
        midi.trocarInstrumento(instrumento_atual.getNumero_MIDI());
    }
    public static void pausa() {
        midi.sleep(TEMPO_MUSICA);

    }
//----------------------------------------------
//Getter Setter

    public static int getVolume() {return volumeProperty.get();}

    public static void setVolume(int volume) {volumeProperty.set(volume);}

    public static IntegerProperty volumeProperty() {return volumeProperty;}

    public static int getBpm() {return bpmProperty.get();}

    public static void setBpm(int bpm) {bpmProperty.set(bpm);}

    public static IntegerProperty bpmProperty() {return bpmProperty;}

    public static int getOitava() {return oitavaProperty().get();}

    public static void setOitava(int oitava) {oitavaProperty().set(oitava);}

    public static IntegerProperty oitavaProperty() {return oitavaProperty;}

    public static boolean getTocando(){ return tocando;}

    public static boolean getPausado(){return pausado;}

    public static void setTocando(boolean tocando) {
        Musica.tocando = tocando;
    }
}