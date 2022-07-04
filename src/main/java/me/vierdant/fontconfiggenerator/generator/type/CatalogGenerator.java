package me.vierdant.fontconfiggenerator.generator.type;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class CatalogGenerator {
    // baseUnicode setting
    private int baseUnicode;
    // checks if the operation is done
    private boolean success = false;
    // stores the directory of the files
    private String directory;
    // names of file
    private ArrayList<String> files;

    public CatalogGenerator(ArrayList<String> files, String directory, int baseUnicode) {
        this.baseUnicode = baseUnicode;
        this.directory = directory;
        this.files = files;
    }

    public CatalogGenerator generate() {
        // stores the root of every entry
        LinkedHashMap<String, Object> fileBody = new LinkedHashMap<>();

        for (String name : files) {
            // generate the entry content
            fileBody.put(name, (char)this.baseUnicode+"");

            // clear object and increase unicode value for next loop
            this.baseUnicode++;
        }

        // create dump options
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        // create yml file
        try {
            FileWriter file = new FileWriter(directory + "/catalog.yml", StandardCharsets.UTF_8);
            Yaml yaml = new Yaml(options);
            yaml.dump(fileBody, file);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public boolean isSuccess() {
        return success;
    }
}

