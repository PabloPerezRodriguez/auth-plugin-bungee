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

public class RegisterCommand extends Command {

  public RegisterCommand() {
    super("registrar");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (sender != null) {
      ProxiedPlayer player = (ProxiedPlayer) sender;
      AuthUser user = AuthPlugin.INSTANCE.authDBInstance.getUserWithName(player.getDisplayName());
      if (user == null) {
        if (args.length == 2) {
          String pass1 = args[0];
          String pass2 = args[1];
          if (pass1.equals(pass2)) {
            // Create database entry
            AuthPlugin.INSTANCE.authDBInstance.getUserList().add(new AuthUser(sender.getName(), pass1, true));

            // Move to main server
            ServerInfo target = ProxyServer.getInstance().getServerInfo("main");
            player.connect(target);
          } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Las contraseñas no coinciden"));
          }
        } else {
          sender.sendMessage(new TextComponent(ChatColor.RED + "Cómo se usa el comando: /registrar <contraseña> <contraseña>"));
        }
      } else {
        if (user.isConnected()) {
          sender.sendMessage(new TextComponent(ChatColor.GREEN + "Ya estás conectado!"));
        } else {
          sender.sendMessage(new TextComponent(ChatColor.RED + "Este usuario ya está registrado"));
        }
      }
    }
  }
}
