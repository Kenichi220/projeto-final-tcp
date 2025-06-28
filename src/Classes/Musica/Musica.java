package Classes.Musica;

import javax.sound.midi.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Musica {

    //Constantes Musica
    private static int VOLUME_INICIAL = 50;
    private static int OITAVA_INICIAL = 0;
    private static int BPM_INICIAL = 120;

    //Variavies
    private static int volume = VOLUME_INICIAL;
    private static int oitava = OITAVA_INICIAL;
    private static int bpm = BPM_INICIAL;
    private static final Instrumentos instrumento_atual = new Instrumentos();
    private static final Notas  nota_atual = new Notas();
    private static final Notas  ultima_nota = new Notas();
    private static final IntegerProperty volumeProperty = new SimpleIntegerProperty(50);
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
    static int TEMPO_MUSICA = 60000/ bpm;
    static final int AUMENTA_BPM = 1;
    static final int BPM_ALEATORIO = 2;
    static final int BPM_ALEATORIO_MAX = 179;
    static final int AUMENTO_BPM = 80;

    //Constantes Nota
    static final int TOCAR_NOTA = 1;
    static final int NOTA_ANTERIOR = 2;
    static final int NOTA_ALEATORIA = 3;

//---------------------------------------------------
//Funcoes

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
                oitava = oitava + 1;
                break;
            case DIMINUI_OITAVA:
                oitava = oitava - 1;
                break;
            default:
                //Arrumar
        }
    }
    public static void alterarBPM(int comando){
        switch (comando){
            case AUMENTA_BPM:
                bpm = bpm + AUMENTO_BPM;
                break;
            case BPM_ALEATORIO:
                bpm = randomizarBPM();
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
                midi.tocar(nota_atual.getNota() + (CONSTANTE_OITAVA * oitava), TEMPO_MUSICA, getVolume());
                break;
            case NOTA_ANTERIOR:
                midi.tocar(ultima_nota.getNota() + (CONSTANTE_OITAVA * oitava), TEMPO_MUSICA, getVolume());
                break;
            case NOTA_ALEATORIA:
                nota_atual.setNota(Notas.notaAleatoria());
                midi.tocar(nota_atual.getNota() + (CONSTANTE_OITAVA * oitava), TEMPO_MUSICA, getVolume());
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
//Construtores
    public static int getVolume() {return volumeProperty.get();}

    public static void setVolume(int volume) {
        volumeProperty.set(volume);
    }

    public static IntegerProperty volumeProperty() {return volumeProperty;}

    //public static int getVolume() {return volume;}

    //public static void setVolume(int volume) {Musica.volume = volume;}

    public static int getOitava() {
        return oitava;
    }

    public static void setOitava(int oitava) {
        Musica.oitava = oitava;
    }

    public static int getBpm() {
        return bpm;
    }

    public static void setBpm(int bpm) {
        Musica.bpm = bpm;
    }

}