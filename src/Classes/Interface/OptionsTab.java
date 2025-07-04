package Classes.Interface;

import Classes.Musica.Instrumentos;
import Classes.Musica.Musica;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class OptionsTab extends Tab {

    //Constantes
    private static int VOLUME_MAX = 100;
    private static int VOLUME_MIN = 0;
    private static int BPM_MAX = 300;
    private static int BPM_MIN = 0;
    private static int OITAVA_MAX = 7;
    private static int OITAVA_MIN = 0;
    private int REMOVE_MINOR_TICK = 0;
    private int OITAVA_TICK = 1;

    public OptionsTab(Musica musica) {

        super("Options");
        setClosable(false);

        //Criacao dos Sliders
        Slider volumeSlider = new Slider(VOLUME_MIN, VOLUME_MAX, Musica.getVolume());
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.valueProperty().bindBidirectional(Musica.volumeProperty());

        Slider bpmSlider = new Slider(BPM_MIN, BPM_MAX, Musica.getBpm());
        bpmSlider.setShowTickLabels(true);
        bpmSlider.setShowTickMarks(true);
        bpmSlider.setMinorTickCount(REMOVE_MINOR_TICK);
        bpmSlider.valueProperty().bindBidirectional(Musica.bpmProperty());

        Slider oitavaSlider = new Slider(OITAVA_MIN, OITAVA_MAX, Musica.getOitava());
        oitavaSlider.setShowTickLabels(true);
        oitavaSlider.setShowTickMarks(true);
        oitavaSlider.setMajorTickUnit(OITAVA_TICK);
        oitavaSlider.setMinorTickCount(REMOVE_MINOR_TICK);
        oitavaSlider.valueProperty().bindBidirectional(Musica.oitavaProperty());

        //Caixas de selecao
        ComboBox<String> sequenciaInstrumentos = new ComboBox<>();
        String[] nomesInstrumentos = Instrumentos.obterNomesInstrumentos();
        int[] midiInstrumentos = Instrumentos.obterInstrumentos();
        sequenciaInstrumentos.getItems().addAll(nomesInstrumentos);
        sequenciaInstrumentos.getSelectionModel().select(Instrumentos.obterInstrumentoInicial());

        //Barra de Instrumentos
        sequenciaInstrumentos.setOnAction(e -> {
            int numberSelected = sequenciaInstrumentos.getSelectionModel().getSelectedIndex();
            int instrumentoSelecionado;
            if (numberSelected < (Instrumentos.NUM_INSTRUMENTOS)) {
                instrumentoSelecionado = midiInstrumentos[numberSelected];
                Musica.trocarInstrumento(instrumentoSelecionado);
            } else {
                instrumentoSelecionado = Instrumentos.instrumentoAleatorio();
                Musica.trocarInstrumento(instrumentoSelecionado);
                sequenciaInstrumentos.getSelectionModel().select(instrumentoSelecionado);
            }
        });
        //Caixas de tetxo
        VBox optionsLayout = new VBox(UIBuilder.ESPACAMENTO_LAYOUT,
                new Label("Volume:"), volumeSlider,
                new Label("BPM:"), bpmSlider,
                new Label("Oitava:"), oitavaSlider,
                new Label("Instrumento:"), sequenciaInstrumentos
        );
        optionsLayout.setAlignment(Pos.CENTER);
        //Estilo do layout
        optionsLayout.setStyle("-fx-padding: 20;");

        setContent(optionsLayout);
    }
}
