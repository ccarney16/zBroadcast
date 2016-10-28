package org.zyphinia.zbroadcast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public final class zbroadcast  extends JavaPlugin {

    private Broadcast _broadcast = null;

    private static zbroadcast pluginInstance = null;

    @Override
    public void onEnable() {
        pluginInstance = this;

        //load config/write new config
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        _broadcast = new Broadcast();

        try {
            _broadcast.Start();
        } catch (Error ex) {
            throw ex;
        }

        getLogger().log(Level.INFO, "Simple Broadcast Enabled");
    }

    @Override
    public void onDisable() {
        //stop all broadcasting
        _broadcast.Stop();

        //remove all variables
        _broadcast = null;
    }

    //Update the list
    public static void UpdateList(List<String> list) {
        GetInstance().getConfig().set("messages", list);
    }

    public static zbroadcast GetInstance() {
        return pluginInstance;
    }
}
