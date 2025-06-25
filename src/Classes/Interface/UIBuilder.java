package Classes.Interface;

import Classes.Musica.*;
import Classes.Musica.TextoMusicalParser;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;

public class UIBuilder {
    private final TextoMusicalParser parser;
    private final Musica musica;

    public UIBuilder(TextoMusicalParser parser, Musica musica) {
        this.parser = parser;
        this.musica = musica;
    }

    public Scene buildUI() {
        PlayTab playTab = new PlayTab(parser);

        OptionsTab optionsTab = new OptionsTab(musica);

        UploadTab uploadTab = new UploadTab(playTab.getInputArea());

        TabPane tabPane = new TabPane(playTab, optionsTab, uploadTab);

        return new Scene(tabPane, 600, 400);
    }
}
