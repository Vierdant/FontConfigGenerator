package me.vierdant.fontconfiggenerator.generator.type;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class OraxenGenerator {
    // baseUnicode setting
    private int baseUnicode;
    // ascent setting
    private int ascent;
    // height setting
    private int height;
    // checks if the operation is done
    private boolean success = false;
    // stores the directory of the files
    private String directory;
    // names of file
    private ArrayList<String> files;

    public OraxenGenerator(ArrayList<String> files, String directory, int baseUnicode, int ascent, int height) {
        this.baseUnicode = baseUnicode;
        this.ascent = ascent;
        this.height = height;
        this.directory = directory;
        this.files = files;
    }

    public OraxenGenerator generate() {
        // stores the root of every entry
        HashMap<String, Object> fileBody = new HashMap<>();

        for (String name : files) {
            // stores the entry body
            LinkedHashMap<String, Object> partObject = new LinkedHashMap<>();
            // generate the entry content
            partObject.put("texture", "generated/"+name);
            partObject.put("ascent", ascent);
            partObject.put("height", height);
            partObject.put("code", baseUnicode);

            // add the object to outer body
            fileBody.put(name.split("\\.")[0], partObject);
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
            FileWriter file = new FileWriter(directory + "/chat_tags.yml", StandardCharsets.UTF_8);
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

