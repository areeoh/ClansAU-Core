package com.areeoh.core.database;

import com.areeoh.core.framework.Manager;
import com.areeoh.core.utility.UtilMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public enum DatabaseTypes {

    CLIENTS("/Clients/", "Clients"),
    GAMERS("/Gamers/", "Gamers"),
    CLANS("/Clans/", "Clans");

    private String path, name;

    DatabaseTypes(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public void SetObject(Manager manager, String file, Object object, Object to) {
        final File f = new File(manager.getPlugin().getDataFolder() + path + file);

        if(!f.exists()) {
            f.mkdir();
        }

        try {
            FileReader reader = new FileReader(manager.getPlugin().getDataFolder() + path + file);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            jsonObject.put(object, to);

            System.out.println(jsonObject.toString());

            try (FileWriter fileWriter = new FileWriter(f)) {
                fileWriter.write(jsonObject.toString());
                fileWriter.flush();
                System.out.println(jsonObject.toString());
            } catch (IOException e) {
                UtilMessage.log("Error", "Error writing " + f.getPath() + " to file.");
            }
        } catch (IOException | ParseException e) {
            UtilMessage.log("Error", "Error writing " + f.getPath() + " to file.");
        }
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}