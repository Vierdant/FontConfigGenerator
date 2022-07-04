package me.vierdant.fontconfiggenerator.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.DirectoryChooser;
import me.vierdant.fontconfiggenerator.Application;
import me.vierdant.fontconfiggenerator.generator.GeneratorManager;

import java.io.File;
import java.util.ArrayList;

public class ViewController {
    @FXML
    private CheckBox themeCheckbox;
    @FXML
    private Spinner baseUnicode;
    @FXML
    private Spinner height;
    @FXML
    private Spinner ascent;
    @FXML
    private Label infoBox;
    @FXML
    private Label directoryText;
    @FXML
    private Button directoryButton;
    // saves the directory string
    private String directory = "default";

    /**
     * Triggered when user clicks on dark theme checkbox
     */
    @FXML
    protected void onThemeChange() {
        if (themeCheckbox.isSelected()) {
            Application.getStage().getScene().getStylesheets().add("dark-theme.css");
        } else {
            Application.getStage().getScene().getStylesheets().remove("dark-theme.css");
        }
    }

    /**
     * Triggered when user chooses a directory
     */
    @FXML
    protected void onDirectoryChoose() {
        // open directory dialog window
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(Application.getStage());
        // saves the new directory path
        if (selectedDirectory != null) {
            directory = selectedDirectory.getAbsolutePath();
            setDirectoryText(directory);
        }
    }

    /**
     * Triggered when user click on start button
     */
    @FXML
    protected void onGenerateButton() {
        if (directory.equals("default")) {
            setInfo("Please choose the directory where your files are located before starting the generation process.\n" +
                    "You can do that by clicking on the <Files Directory> button in the left panel.");
            return;
        }
        Object baseUnicodeValue = baseUnicode.getValueFactory().getValue();
        Object ascentValue = ascent.getValueFactory().getValue();
        Object heightValue = height.getValueFactory().getValue();

        System.out.println(baseUnicodeValue + " " + ascentValue + " " + heightValue);

        GeneratorManager generatorManager = new GeneratorManager(directory, baseUnicodeValue, ascentValue, heightValue).validateData();
        if (!generatorManager.isSafe()) {
            setInfo("Something went wrong!\nCheck your entered arguments and try again.");
            return;
        }

        boolean success = generatorManager.start();

        if (!success) {
            setInfo("Failed to generate one or more files...\nPlease check your settings and try again");
            return;
        }
        setInfo("Success! Config files generated.\nMake sure to join our discord server and thank us\nfor saving you a lot of pain :)");
    }

    /**
     * set the text in the info box to new text
     * @param string new text that would be displayed in the info box
     */
    public void setInfo(String string) {
        infoBox.setText(string);
    }

    /**
     * set the text in the directory text box to new text
     * @param string new text that would be displayed in the directory box
     */
    public void setDirectoryText(String string) {
        directoryText.setText(string);
    }
}