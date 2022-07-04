module me.vierdant.fontconfiggenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.yaml.snakeyaml;

    exports me.vierdant.fontconfiggenerator;
    exports me.vierdant.fontconfiggenerator.controller;
    exports me.vierdant.fontconfiggenerator.generator;
    opens me.vierdant.fontconfiggenerator.controller to javafx.fxml;
}