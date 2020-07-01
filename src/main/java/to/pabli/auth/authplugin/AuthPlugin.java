package to.pabli.auth.authplugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import to.pabli.auth.authplugin.auth.AuthDB;
import to.pabli.auth.authplugin.commands.LoginCommand;
import to.pabli.auth.authplugin.commands.PremiumCommand;
import to.pabli.auth.authplugin.commands.RegisterCommand;
import to.pabli.auth.authplugin.events.UserConnectDisconnectEvents;
import to.pabli.auth.authplugin.events.UserJoinServerEvent;

public final class AuthPlugin extends Plugin {
  public static AuthPlugin INSTANCE;

  public AuthDB authDBInstance;
  private static final String FILENAME = "users.json";
  private File usersFile;

  @Override
  public void onEnable() {
    INSTANCE = this;

    // Plugin startup logic
    this.usersFile = new File(this.getDataFolder().getPath() + File.separator + FILENAME);

    if (!this.usersFile.exists()) {
      //File does not exist, save to file
      getLogger().info("Couldn't load 'users.json' database, creating an empty one.");
      this.authDBInstance = new AuthDB();
      try {
        ConfigLoader.saveConfig(this.authDBInstance, this.usersFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        this.authDBInstance = ConfigLoader.loadConfig(AuthDB.class, usersFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (this.authDBInstance == null) {
        this.authDBInstance = new AuthDB();
      }
    }

    getProxy().getPluginManager().registerCommand(this, new RegisterCommand());
    getProxy().getPluginManager().registerCommand(this, new LoginCommand());
    getProxy().getPluginManager().registerCommand(this, new PremiumCommand());
    getProxy().getPluginManager().registerListener(this, new UserConnectDisconnectEvents());
    getProxy().getPluginManager().registerListener(this, new UserJoinServerEvent());

    getLogger().info("AuthPlugin loaded");
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic

    try {
      ConfigLoader.saveConfig(this.authDBInstance, usersFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

    getLogger().info("AuthPlugin disabled");
  }

}
