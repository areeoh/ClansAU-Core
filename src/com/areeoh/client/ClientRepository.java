package com.areeoh.client;

import com.areeoh.database.DatabaseManager;
import com.areeoh.database.DatabaseTypes;
import com.areeoh.framework.Module;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class ClientRepository extends Module<DatabaseManager> {

    public ClientRepository(DatabaseManager manager) {
        super(manager, "ClientRepository");
    }

    @Override
    public void initialize(JavaPlugin javaPlugin) {
        new BukkitRunnable() {
            public void run() {
                loadClients();
            }
        }.runTaskAsynchronously(getPlugin());
    }

    public synchronized void loadClients() {
        final File parent = new File(getPlugin().getDataFolder() + "/Clients/");

        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (parent != null) {
            for (File file : parent.listFiles()) {
                JSONParser parser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));
                    final UUID uuid = UUID.fromString((String) jsonObject.get("uuid"));
                    final String name = (String) jsonObject.get("name");
                    final Rank rank = Rank.valueOf((String) jsonObject.get("rank"));
                    final String previousName = (String) jsonObject.get("previous_name");
                    final String ipAddress = (String) jsonObject.get("ip_address");

                    final Client client = new Client(uuid);

                    client.setName(name);
                    client.setRank(rank);
                    client.setPreviousName(previousName);
                    client.setIPAddress(ipAddress);

                    getManager(ClientManager.class).addClient(client);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateRank(Client client) {
        DatabaseTypes.CLIENTS.SetObject(getManager(), client.getUUID() + ".json", "rank", client.getRank().name());
    }

    public void saveClient(Client client) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", client.getUUID().toString());
        jsonObject.put("name", client.getName());
        jsonObject.put("rank", client.getRank().name());
        jsonObject.put("previous_name", client.getPreviousName());
        jsonObject.put("ip_address", client.getIPAddress());

        try (FileWriter fileWriter = new FileWriter(getPlugin().getDataFolder() + "/Clients/" + client.getUUID() + ".json")) {
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
    }
}