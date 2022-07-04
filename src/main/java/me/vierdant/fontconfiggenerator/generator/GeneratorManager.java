package me.vierdant.fontconfiggenerator.generator;

import me.vierdant.fontconfiggenerator.generator.type.DefaultGenerator;
import me.vierdant.fontconfiggenerator.generator.type.ItemsAdderGenerator;
import me.vierdant.fontconfiggenerator.generator.type.OraxenGenerator;

import java.io.File;
import java.util.ArrayList;

/**
 * Catches and manages the data before sending it to different generators
 */
public class GeneratorManager {
    // baseUnicode setting
    private Object baseUnicode;
    // ascent setting
    private Object ascent;
    // height setting
    private Object height;
    // checks if the operation is safe or not
    private boolean safe = true;
    // stores the directory of the files
    private String directory;

    public GeneratorManager(String directory, Object baseUnicode, Object ascent, Object height) {
        this.baseUnicode = baseUnicode;
        this.ascent = ascent;
        this.height = height;
        this.directory = directory;
    }

    public GeneratorManager validateData() {
        try {
            // parse given values as integers and assign them to instance variables
            this.baseUnicode = Integer.parseInt(this.baseUnicode.toString());
            this.ascent = Integer.parseInt(this.ascent.toString());
            this.height = Integer.parseInt(this.height.toString());
        } catch (NumberFormatException exception) {
            // if a given value can't be parsed as an integer
            this.safe = false;
        }

        return this;
    }

    public boolean isSafe() {
        return this.safe;
    }

    /**
     * Start the generator process
     */
    public boolean start() {
        // names of file
        ArrayList<String> files = getFiles();

        if (files.isEmpty()) {
            System.out.println("The selected directory was empty");
            return false;
        }

        var defaultGenerator = new DefaultGenerator(files, this.directory, (Integer) this.baseUnicode, (Integer) this.ascent, (Integer) this.height)
                .generate();
        var oraxenGenerator = new OraxenGenerator(files, this.directory, (Integer) this.baseUnicode, (Integer) this.ascent, (Integer) this.height)
                .generate();
        var itemsAdderGenerator = new ItemsAdderGenerator(files, this.directory, (Integer) this.baseUnicode, (Integer) this.ascent, (Integer) this.height)
                .generate();

        return defaultGenerator.isSuccess() && oraxenGenerator.isSuccess();
    }

    /**
     * get the list of files
     * @return a list of file names in a directory
     */
    private ArrayList<String> getFiles() {
        File folder = new File(this.directory);
        File[] files = folder.listFiles();
        ArrayList<String> list = new ArrayList<>();
        // go through the files list
        assert files != null;
        for (File file : files) {
            // if it's a file and not a directory
            if (file.isFile()) {
                // add to return list
                list.add(file.getName());
            }
        }

        return list;
    }
}
