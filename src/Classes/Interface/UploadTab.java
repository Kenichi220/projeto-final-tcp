package Classes.Interface;

import javafx.geometry.Pos;
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
        Button uploadButton = new Button("Selecionar Arquivo");
        Label fileNameLabel = new Label("Nenhum arquivo selecionado");

        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecionar Arquivo .txt");
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

        VBox textInputLayout = new VBox(10, uploadLabel, uploadButton, fileNameLabel);
        textInputLayout.setAlignment(Pos.CENTER);
        textInputLayout.setStyle("-fx-padding: 20;");

        setContent(textInputLayout);
    }
}
