package Classes.Musica;
import Classes.Interface.PlayTab;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class GeradorMidi {

    // Constantes para os comandos MIDI
    static final int NOTE_ON = 0x90;  // Iniciar nota
    static final int NOTE_OFF = 0x80; // Parar nota
    static final int TICK_INICIAL = 0;
    private static final int TICK = 24;
    private static final int CANAL = 0;
    private static final int DURACAO_NOTA = 100;

    //Variaveis
   private static Sequence sequence;

    static {
        try {
            sequence = new Sequence(Sequence.PPQ, TICK);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    private static Track track = sequence.createTrack();
    private static int tick = TICK_INICIAL;

    public static void GeraArquivo() throws IOException {
            //Reinicia o track para nao poluir o arquivo
            tick = TICK_INICIAL;
            sequence.deleteTrack(track);
            track = sequence.createTrack();

            String nomeArquivo = "melodia.mid";

            //Se nao estiver tocando vai gerar o arquivo
            if(!Musica.getTocando()) {
                String texto = PlayTab.getTexto();
                TextoMusicalParser.setGerandoMidi(true);
                TextoMusicalParser.interpret(texto);

                File f = new File(nomeArquivo);
                MidiSystem.write(sequence, 1, f); // O segundo parâmetro é o tipo do arquivo MIDI

                System.out.println("Arquivo " + nomeArquivo + " criado com sucesso!");
            }
    }

    public static void criarEventoMidi(int comando, int nota){
        //Coloca nota
        track.add(criaMensagem(comando,  nota));
        //Passa para o proximo evento
        tick = tick + TICK;
        //Pausa nota
        track.add(criaMensagem(NOTE_OFF, nota));
    }
    public static MidiEvent criaMensagem(int comando, int nota) {

        ShortMessage mensagem = new ShortMessage();
        try {
            mensagem.setMessage(comando, CANAL, nota + (Musica.CONSTANTE_OITAVA * Musica.getOitava()), DURACAO_NOTA);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return new MidiEvent(mensagem, tick);
    }
//----------------------------------------------



}

