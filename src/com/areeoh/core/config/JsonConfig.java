package com.areeoh.core.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

import com.areeoh.core.framework.Module;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonConfig extends Module<ConfigManager> {

    private File configFile;
    private File pluginDataFolder;
    private String name;

    public JsonConfig(ConfigManager manager, String moduleName, File pluginDataFolder) {
        super(manager, moduleName);

        StringBuilder fileName = new StringBuilder();
        fileName.append(moduleName).append(".json");
        this.name = fileName.toString();

        configFile = new File(pluginDataFolder, this.name);
        this.pluginDataFolder = pluginDataFolder;

    }

    public void createConfig() {
        if (!configFile.exists()) {

            if (!this.pluginDataFolder.exists()) {

                this.pluginDataFolder.mkdir();
            }

            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getDirectory() {
        return pluginDataFolder;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return configFile;
    }

    public void deleteFile() {
        configFile.delete();
    }

    public void deleteParentDir() {
        this.getDirectory().delete();
    }

    public void reset() {
        this.deleteFile();
        try {
            configFile.createNewFile();
            JSONObject obj = new JSONObject();
            PrintWriter write = new PrintWriter(configFile);
            write.write(obj.toJSONString());
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wipeDirectory() {
        this.getDirectory().delete();
        this.pluginDataFolder.mkdir();
    }

    public void createSubDirectory(String name) throws IOException {
        if (!pluginDataFolder.exists()) {
            throw new IOException("Data folder not found.");
        }

        File subDir = new File(pluginDataFolder, name);

        if (subDir.exists()) {
            throw new IOException("Sub directory already existing.");
        }
        subDir.mkdir();
    }

    @SuppressWarnings("unused")
    public boolean isJSONObject() throws IOException, FileNotFoundException {
        try {
            JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(configFile));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unused")
    public boolean isJSONArray() throws IOException, FileNotFoundException {
        try {
            JSONArray obj = (JSONArray) new JSONParser().parse(new FileReader(configFile));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public JSONObject toJSONObject() throws IOException, FileNotFoundException, ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(configFile));
        return obj;
    }

    public JSONArray toJSONArray() throws IOException, FileNotFoundException, ParseException {
        JSONArray arr = (JSONArray) new JSONParser().parse(new FileReader(configFile));
        return arr;
    }

    @SuppressWarnings("unchecked")
    public void putInJSONObject(Object k, Object v) throws FileNotFoundException, IOException, ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(configFile));
        obj.put(k, v);
        PrintWriter write = new PrintWriter(configFile);
        write.write(obj.toJSONString());
        write.close();
    }

    @SuppressWarnings("unchecked")
    public void putInJSONObject(Map<Object, Object> values) throws FileNotFoundException, IOException, ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(configFile));
        obj.putAll(values);;
        PrintWriter write = new PrintWriter(configFile);
        write.write(obj.toJSONString());
        write.close();
    }

    @SuppressWarnings("unchecked")
    public void putInJSONArray(Object obj) throws FileNotFoundException, IOException, ParseException {
        JSONArray arr = (JSONArray) new JSONParser().parse(new FileReader(configFile));
        arr.add(obj);
        PrintWriter write = new PrintWriter(configFile);
        write.write(arr.toJSONString());
        write.close();
    }

    @SuppressWarnings("unchecked")
    public void putInJSONArray(Collection<Object> c) throws FileNotFoundException, IOException, ParseException {
        JSONArray arr = (JSONArray) new JSONParser().parse(new FileReader(configFile));
        arr.addAll(c);
        PrintWriter write = new PrintWriter(configFile);
        write.write(arr.toJSONString());
        write.close();
    }
}