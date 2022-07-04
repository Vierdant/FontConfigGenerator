package me.vierdant.fontconfiggenerator.generator.type;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class ItemsAdderGenerator {
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

    public ItemsAdderGenerator(ArrayList<String> files, String directory, int baseUnicode, int ascent, int height) {
        this.baseUnicode = baseUnicode;
        this.ascent = ascent;
        this.height = height;
        this.directory = directory;
        this.files = files;
    }

    public ItemsAdderGenerator generate() {
        // stores the root of every entry
        LinkedHashMap<String, Object> fileBody = new LinkedHashMap<>();
        HashMap<String, Object> innerFileBody = new HashMap<>();

        // set up the info body entry
        HashMap<String, Object> bodyInfo = new HashMap<>();
        bodyInfo.put("namespace", "generated");
        fileBody.put("info", bodyInfo);

        for (String name : files) {
            // stores the entry body
            LinkedHashMap<String, Object> partObject = new LinkedHashMap<>();
            // generate the entry content
            partObject.put("permission", "generated."+name.split("_")[1].split("\\.")[0]);
            partObject.put("show_in_gui", true);
            partObject.put("suggest_in_command", false);
            partObject.put("path", "generated/"+name);
            partObject.put("symbol", (char)this.baseUnicode+"");
            partObject.put("y_position", ascent);
            partObject.put("scale_ratio", height);

            // add the object to outer body
            innerFileBody.put(name.split("\\.")[0], partObject);
            // clear object and increase unicode value for next loop
            this.baseUnicode++;
        }

        fileBody.put("font_images", innerFileBody);
        // create dump options
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        // create yml file
        try {
            FileWriter file = new FileWriter(directory + "/generatedpack.yml", StandardCharsets.UTF_8);
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

