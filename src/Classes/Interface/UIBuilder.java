package Classes.Interface;

import Classes.Musica.*;
import Classes.Musica.TextoMusicalParser;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;

public class UIBuilder {
    //Constantes
    private final int LARGURA_LAYOUT = 600;
    private final int ALTURA_LAYOUT = 400;
    static final int ESPACAMENTO_LAYOUT = 10;

    //Variaveis
    private final TextoMusicalParser parser;
    private final Musica musica;


    public UIBuilder(TextoMusicalParser parser, Musica musica) {
        this.parser = parser;
        this.musica = musica;
    }

    public Scene buildUI() {
        PlayTab playTab = new PlayTab(parser, musica);

        OptionsTab optionsTab = new OptionsTab(musica);

        ArquivosTab uploadTab = new ArquivosTab(playTab.getInputArea());

        TabPane tabPane = new TabPane(playTab, optionsTab, uploadTab);

        return new Scene(tabPane, LARGURA_LAYOUT, ALTURA_LAYOUT);
    }
}
