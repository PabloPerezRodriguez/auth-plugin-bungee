package to.pabli.auth.authplugin.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import to.pabli.auth.authplugin.AuthPlugin;
import to.pabli.auth.authplugin.auth.AuthUser;

public class LoginCommand extends Command {

  public LoginCommand() {
    super("iniciarsesion");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (sender != null) {
      ProxiedPlayer player = (ProxiedPlayer) sender;
      AuthUser user = AuthPlugin.INSTANCE.authDBInstance.getUserWithName(player.getDisplayName());
      if (user != null) {
        if (user.isConnected()) {
          sender.sendMessage(new TextComponent(ChatColor.GREEN + "Ya estás conectado!"));
          return;
        }
        if (args.length == 1) {
          String password = args[0];
          if (user.getPassword().equals(password)) {
            user.setConnected(true);

            // Move to main server
            ServerInfo target = ProxyServer.getInstance().getServerInfo("main");
            player.connect(target);
          } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Las contraseña no es correcta"));
          }
        } else {
          sender.sendMessage(new TextComponent(ChatColor.RED + "Cómo se usa el comando: /inciarsesion <contraseña>"));
        }
      } else {
        sender.sendMessage(new TextComponent(ChatColor.RED + "Este usario todavía no está registrado"));
      }
    }
  }
}
