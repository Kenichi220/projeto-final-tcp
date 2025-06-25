package Classes.Musica;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.sound.midi.*;


public class Musica {

    //Constantes Musica
    static int VOLUME_INICIAL = 50;
    static int OITAVA_INICIAL = 0;
    static int BPM_INICIAL = 120;

    //Variavies
    private static int volume = VOLUME_INICIAL;
    private static int oitava = OITAVA_INICIAL;
    private static int bpm = BPM_INICIAL;
    private static final Instrumentos instrumento_atual = new Instrumentos();
    private static final Notas  nota_atual = new Notas();
    private static final Notas  ultima_nota = new Notas();

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

    public final Label volumeLabel = new Label("Volume: 50");
    public final Label oitavaLabel = new Label("Oitava: 5");
//---------------------------------------------------
//Funcoes

    private static int randomizar_nota() {
        int random = (int) (Math.random() * Notas.NUM_NOTAS);
        int[] vetor = {Notas.DO,Notas.RE,Notas.MI,Notas.FA,Notas.SOL,Notas.LA,Notas.SI};
        return vetor[random];
    }
    private static int randomizar_instrumento() {
        int random = (int) (Math.random() * Instrumentos.NUM_INSTRUMENTOS);
        int[] vetor = {Instrumentos.PIANO, Instrumentos.VIOLAO,Instrumentos.SAXOFONE,Instrumentos.GAITA_FOLE,Instrumentos.STEEL_DRAM};
        return vetor[random];
    }

    private static int randomizar_bpm() {
        //Vai de 1 até BPM_ALEATORIO_MAX + 1
        return  (int) (Math.random() * BPM_ALEATORIO_MAX + 1);
    }

    public static void alterar_volume(int comando){
        switch (comando){
            case AUMENTA_VOLUME:
                volume = volume * 2;
                break;
            case DIMINUI_VOLUME:
                volume = VOLUME_INICIAL;
                break;
            default:
                volume = VOLUME_INICIAL;
        }
    }

    public static void alterar_oitava(int comando){
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
    public static void alterar_bpm(int comando){
        switch (comando){
            case AUMENTA_BPM:
                bpm = bpm + AUMENTO_BPM;
                break;
            case BPM_ALEATORIO:
                bpm = randomizar_bpm();
                break;
            default:
                //Arrumar
        }
    }

    public static void tocar_som(int nota, int comando){
        //Nota atual atualizada se houver nota
        if(nota != 0)
            nota_atual.setNota(nota);
        switch (comando){
            case TOCAR_NOTA:
                midi.tocar(nota_atual.getNota() + (12 * oitava), TEMPO_MUSICA, volume);
                break;
            case NOTA_ANTERIOR:
                midi.tocar(ultima_nota.getNota() + (12 * oitava), TEMPO_MUSICA, volume);
                break;
            case NOTA_ALEATORIA:
                nota_atual.setNota(randomizar_nota());
                midi.tocar(nota_atual.getNota() + (12 * oitava), TEMPO_MUSICA, volume);
                break;
            default:
                //Arrumar
        }
        //Ultima nota vira a atual
        ultima_nota.setNota(nota_atual.getNota());
    }

    public static void trocar_instrumento_random(){
        instrumento_atual.setNumero_MIDI(randomizar_instrumento());
        midi.trocarInstrumento(instrumento_atual.getNumero_MIDI());
    }
    public static void trocar_instrumento(int instrumento_selecionado){
        instrumento_atual.setNumero_MIDI(instrumento_selecionado);
        midi.trocarInstrumento(instrumento_atual.getNumero_MIDI());
    }
    public static void pausa() {
        midi.sleep(TEMPO_MUSICA);

    }
//----------------------------------------------
//Construtores

    public static int getVolume() {
        return volume;
    }

    public static void setVolume(int volume) {
        Musica.volume = volume;
    }

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