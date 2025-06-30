package Classes.Interface;

import Classes.Musica.GeradorMidi;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UploadTab extends Tab {

    public UploadTab(TextArea targetTextArea) {
        super("Upload Text");
        setClosable(false);

        Label uploadLabel = new Label("Carregue uma partitura de um arquivo de texto:");
        Button uploadButton = new Button("Selecionar arquivo .txt");
        Label fileNameLabel = new Label("Nenhum arquivo selecionado");
        Button exportMidi = new Button("Exportar em .midi");

        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecionar arquivo .txt");
            // Bloqueia arquivos nao txt
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos de Texto (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());

            if (file != null) {
                try {
                    String text = new String(Files.readAllBytes(file.toPath()));
                    targetTextArea.setText(text);
                    fileNameLabel.setText("Arquivo: " + file.getName());
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro de Leitura");
                    alert.setHeaderText("Não foi possível ler o conteúdo do arquivo.");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        });

        exportMidi.setOnAction(e -> {
            try {
                GeradorMidi.GeraArquivo();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Gerado com sucesso");
                successAlert.setContentText("O arquivo `melodia.midi` foi criado com sucesso!");
                successAlert.showAndWait();

            } catch (IOException ex) {

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erro de Arquivo");
                errorAlert.setHeaderText("Não foi possível gerar o arquivo .midi");
                errorAlert.setContentText("Ocorreu um erro de I/O: " + ex.getMessage());
                errorAlert.showAndWait();
            }
        });

        VBox textInputLayout = new VBox(UIBuilder.ESPACAMENTO_LAYOUT, uploadLabel, uploadButton, fileNameLabel, exportMidi);
        textInputLayout.setAlignment(Pos.CENTER);
        textInputLayout.setStyle("-fx-padding: 20;");

        VBox.setMargin(exportMidi, new Insets(25, 0, 0, 0));

        setContent(textInputLayout);
    }
}
