package me.vierdant.fontconfiggenerator.generator.type;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class DefaultGenerator {
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
    // build json structure
    private JsonObject jsonObject = new JsonObject();

    public DefaultGenerator(ArrayList<String> files, String directory, int baseUnicode, int ascent, int height) {
        this.baseUnicode = baseUnicode;
        this.ascent = ascent;
        this.height = height;
        this.directory = directory;
        this.files = files;
    }

    public DefaultGenerator generate() {
        // create the top level json array
        JsonArray outerArray = new JsonArray();

        for (String name : files) {
            JsonObject partObject = new JsonObject();
            // generate the part object for a name entry
            ArrayList<String> chars = new ArrayList<>();
            chars.add((char)this.baseUnicode+"");
            partObject.put("chars", chars);
            partObject.put("file","generated/"+name);
            partObject.put("ascent", ascent+"");
            partObject.put("height", height+"");
            partObject.put("type", "bitmap");
            // add the object to outer array
            outerArray.add(partObject);
            // clear object and increase unicode value for next loop
            this.baseUnicode++;
        }

        // add the final array to the json object
        jsonObject.put("providers", outerArray);

        try {
            FileWriter file = new FileWriter(directory+"/default.json", StandardCharsets.UTF_8);
            file.write(jsonObject.toJson());
            file.close();
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
