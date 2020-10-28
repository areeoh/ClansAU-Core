package com.areeoh.core.client.commands;

import com.areeoh.core.ClansAUCore;
import com.areeoh.core.client.Rank;
import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Primitive;
import com.areeoh.core.framework.commands.Command;
import com.areeoh.core.framework.commands.CommandManager;
import com.areeoh.core.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrimitiveCmdManager extends CommandManager {

    public PrimitiveCmdManager(ClansAUCore plugin) {
        super(plugin, "PrimitiveCommand");
    }

    @Override
    public void registerModules() {
        addModule(new Command<CommandSender>(this, "PrimitiveBase", CommandSender.class) {
            @Override
            public boolean execute(CommandSender sender, String[] args) {
                UtilMessage.message(sender, "Primitive", "/primitive set <manager> <module> <primitive-type> <primitive-name> <new-value>");
                UtilMessage.message(sender, "Primitive", "/primitive get <manager> <module> <primitive-type> <primitive-name>");
                return false;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String[] args) {
                return new ArrayList<>();
            }
        }.setCommand("primitive").setRequiredRank(Rank.OWNER));

        addModule(new Command<CommandSender>(this, "PrimitiveSet", CommandSender.class) {
            @Override
            public boolean execute(CommandSender sender, String[] args) {
                String targetManager = args[1];
                String targetModule = args[2];
                Manager manager = getManager(targetManager);
                if (manager == null) {
                    return false;
                }
                Module module = manager.getModule(targetModule);
                if (module == null) {
                    return false;
                }
                Map.Entry<String, Primitive> primitive = module.getPrimitive(args[3]);
                if (primitive == null) {
                    return false;
                }
                try {
                    primitive.getValue().setObject(primitive.getValue().getObject().getClass().getConstructor(String.class).newInstance(args[4]));
                    UtilMessage.message(sender, "Primitive", "You have updated " + ChatColor.YELLOW + primitive.getKey() + " " + primitive.getValue().getObject().getClass().getSimpleName() + ChatColor.GRAY + " to " + ChatColor.GREEN + primitive.getValue().getObject() + ChatColor.GRAY + ".");

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("primitive_name", primitive.getKey());
                    jsonObject.put("primitive_value", primitive.getValue().getObject());

                    manager.savePrimitive(module, primitive.getKey(), jsonObject);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    UtilMessage.message(sender, "Primitive", "New value type does not match the current value type.");
                }
                return false;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String[] args) {
                List<String> list = new ArrayList<>();
                if (args.length == 2) {
                    for (Manager manager : getPlugin().getManagers()) {
                        if (manager.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                            list.add(manager.getName());
                        }
                    }
                } else if (args.length == 3) {
                    for (Object obj : getPlugin().getManager(args[1]).getModules()) {
                        Module module = (Module) obj;
                        if (module.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                            list.add(module.getName());
                        }
                    }
                } else if (args.length == 4) {
                    for (Object primitive : getManager(args[1]).getModule(args[2]).getPrimitives().entrySet()) {
                        Map.Entry<String, Primitive> prim = (Map.Entry<String, Primitive>) primitive;
                        if (prim.getKey().toLowerCase().startsWith(args[3].toLowerCase())) {
                            list.add(prim.getKey());
                        }
                    }
                }
                return list;
            }
        }.setCommand("set").setIndex(1).setRequiredArgs(5).setRequiredRank(Rank.OWNER));

        addModule(new Command<CommandSender>(this, "PrimitiveGet", CommandSender.class) {
            @Override
            public boolean execute(CommandSender sender, String[] args) {
                String targetManager = args[1];
                String targetModule = args[2];
                Manager manager = getManager(targetManager);
                if (manager == null) {
                    return false;
                }
                Module module = manager.getModule(targetModule);
                if (module == null) {
                    return false;
                }
                Map.Entry<String, Primitive> primitive = module.getPrimitive(args[3]);
                if (primitive == null) {
                    return false;
                }
                UtilMessage.message(sender, "Primitive", "The " + ChatColor.GREEN + primitive.getValue().getObject().getClass().getSimpleName() + ChatColor.GRAY + " value of " + ChatColor.YELLOW + primitive.getKey() + ChatColor.GRAY + " is " + ChatColor.GREEN + primitive.getValue().getObject() + ChatColor.GRAY + ".");
                return false;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String[] args) {
                List<String> list = new ArrayList<>();
                if (args.length == 2) {
                    for (Manager manager : getPlugin().getManagers()) {
                        if (manager.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                            list.add(manager.getName());
                        }
                    }
                } else if (args.length == 3) {
                    for (Object obj : getPlugin().getManager(args[1]).getModules()) {
                        Module module = (Module) obj;
                        if (module.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                            list.add(module.getName());
                        }
                    }
                } else if (args.length == 4) {
                    for (Object value : getManager(args[1]).getModule(args[2]).getPrimitives().keySet()) {
                        list.add((String) value);
                    }
                }
                return list;
            }

            @Override
            public void invalidArgsRequired(CommandSender sender) {
                UtilMessage.message(sender, "Invalid args", "");
            }
        }.setCommand("get").setIndex(1).setRequiredArgs(4).setRequiredRank(Rank.OWNER));

        addModule(new Command<CommandSender>(this, "PrimitiveResetDefault", CommandSender.class) {
            @Override
            public boolean execute(CommandSender sender, String[] args) {
                String targetManager = args[1];
                String targetModule = args[2];
                Manager manager = getManager(targetManager);
                if (manager == null) {
                    return false;
                }
                Module module = manager.getModule(targetModule);
                if (module == null) {
                    return false;
                }
                if (args[3].equals("*")) {
                    for (Object p : module.getPrimitives().entrySet()) {
                        Map.Entry<String, Primitive> primitive = (Map.Entry<String, Primitive>) p;
                        primitive.getValue().resetToDefault();
                        UtilMessage.message(sender, "Primitive", "You reset " + ChatColor.YELLOW + primitive.getKey() + ChatColor.GRAY + " to it's default value (" + ChatColor.GREEN + primitive.getValue().getDefaultValue() + ChatColor.GRAY + ").");
                    }
                } else {
                    Map.Entry<String, Primitive> primitive = module.getPrimitive(args[3]);
                    if (primitive == null) {
                        return false;
                    }
                    primitive.getValue().resetToDefault();
                    UtilMessage.message(sender, "Primitive", "You reset " + ChatColor.YELLOW + primitive.getKey() + ChatColor.GRAY + " to it's default value (" + ChatColor.GREEN + primitive.getValue().getDefaultValue() + ChatColor.GRAY + ").");
                }
                return false;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String[] args) {
                List<String> list = new ArrayList<>();
                if (args.length == 2) {
                    for (Manager manager : getPlugin().getManagers()) {
                        if (manager.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                            list.add(manager.getName());
                        }
                    }
                } else if (args.length == 3) {
                    for (Object obj : getPlugin().getManager(args[1]).getModules()) {
                        Module module = (Module) obj;
                        if (module.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                            list.add(module.getName());
                        }
                    }
                } else if (args.length == 4) {
                    for (Object primitive : getManager(args[1]).getModule(args[2]).getPrimitives().keySet()) {
                        list.add((String) primitive);
                    }
                }
                return list;
            }
        }.setCommand("reset").setAliases("resetdefault", "resetdefaults").setIndex(1).setRequiredArgs(4).setRequiredRank(Rank.OWNER));
    }
}
