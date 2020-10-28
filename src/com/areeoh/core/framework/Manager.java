package com.areeoh.core.framework;

import com.areeoh.core.framework.interfaces.IManager;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.core.utility.UtilTime;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Manager<E extends Module> extends Frame implements IManager {

    private List<E> modules;

    public Manager(Plugin plugin, String name) {
        super(plugin, name);

        modules = new ArrayList<>();
    }

    @Override
    public void initialize(Plugin javaPlugin) {
        super.initialize(javaPlugin);

        registerModules();

        for (Module module : modules) {
            if (module.isEnabled()) {
                long epoc = System.currentTimeMillis();
                module.initialize(javaPlugin);
                UtilMessage.log("Module", module.getName() + " initialised in " + UtilTime.convert(System.currentTimeMillis() - epoc, UtilTime.TimeUnit.SECONDS, 2) + ".");
            }
            if (hasPrimitives(module)) {
                File managerFile = getManagerFile();
                if(!getPlugin().getDataFolder().exists()) {
                    getPlugin().getDataFolder().mkdir();
                }
                if(!managerFile.exists()) {
                    managerFile.mkdir();
                }
                File finalFile = new File(managerFile.getPath(), module.getName() + ".json");
                if (!finalFile.exists()) {
                    createJSONFile(module, finalFile);
                }
                loadJSONFile(module, finalFile);
            }
        }
    }

    @Override
    public void shutdown() {
    }

    public List<E> getModules() {
        return modules;
    }

    public <T extends Module> T getModule(Class<T> classType) {
        return modules.stream().filter(module -> module.getClass().equals(classType)).findFirst().map(classType::cast).orElse(null);
    }

    public E getModule(String input) {
        for (E module : modules) {
            if (module.getName().equalsIgnoreCase(input)) {
                return module;
            }
        }
        return null;
    }

    public List<String> getModulesAsList() {
        List<String> modules = new ArrayList<>();
        for (Module module : this.modules) {
            modules.add(module.getName());
        }
        return modules;
    }

    public <T> List<T> getModules(Class<T> clazz) {
        List<T> modules = new ArrayList<>();
        for (Module module : this.modules) {
            if (clazz.isAssignableFrom(module.getClass())) {
                modules.add(clazz.cast(module));
            }
        }
        return modules;
    }

    public void addModule(E module) {
        this.modules.add(module);
    }

    public abstract void registerModules();

    private File getManagerFile() {
        File file = new File(getPlugin().getDataFolder().getPath() + "/managers/");
        if (!file.exists()) {
            file.mkdir();
        }
        File managerFile = new File(file.getPath() + "/" + getName() + "/");
        if (!managerFile.exists()) {
            managerFile.mkdir();
        }
        return managerFile;
    }

    private void createJSONFile(Module module, File finalFile) {
        JSONObject jsonObject = new JSONObject();

        for (Object entry : module.getPrimitives().entrySet()) {
            Map.Entry<String, Primitive> primitiveEntry = (Map.Entry<String, Primitive>) entry;
            JSONObject moduleJsonObject = new JSONObject();
            moduleJsonObject.put("primitive_name", primitiveEntry.getKey());
            moduleJsonObject.put("primitive_value", primitiveEntry.getValue().getObject());

            jsonObject.put(primitiveEntry.getKey(), moduleJsonObject);
        }
        try (FileWriter fileWriter = new FileWriter(finalFile)) {
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePrimitive(Module module, Object object, Object to) {
        final File f = new File(getPlugin().getDataFolder() + "/managers/" + getName() + "/" + module.getName() + ".json");

        if(!f.exists()) {
            f.mkdir();
        }

        try {
            FileReader reader = new FileReader(f);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            jsonObject.put(object, to);

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

    private void loadJSONFile(Module module, File finalFile) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(finalFile));
            for (Object value : jsonObject.values()) {
                JSONObject valueJSONObject = (JSONObject) value;

                String primitiveName = (String) valueJSONObject.get("primitive_name");
                Object primitiveValue = valueJSONObject.get("primitive_value");

                Map.Entry<String, Primitive> primitive = module.getPrimitive(primitiveName);
                primitive.getValue().setObject(primitiveValue);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean hasPrimitives(Module module) {
        return !module.getPrimitives().isEmpty();
    }
}
