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

public class PremiumCommand extends Command {

  public PremiumCommand() {
    super("premium");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (sender != null) {
      ProxiedPlayer player = (ProxiedPlayer) sender;
      AuthUser user = AuthPlugin.INSTANCE.authDBInstance.getUserWithName(player.getDisplayName());
      if (user == null) {
        user = new AuthUser(player.getDisplayName(), "", true);
        user.setPremium(true);
        AuthPlugin.INSTANCE.authDBInstance.getUserList().add(user);
      } else {
        if (user.isPremium()) {
          sender.sendMessage(new TextComponent(ChatColor.GREEN + "El modo premium ya está activado para tu cuenta!"));
        } else {
          user.setPremium(true);
          player.disconnect(new TextComponent(ChatColor.GREEN + "El modo premium está ahora activado para tu cuenta.\n" +
              "¡Simplemente " + ChatColor.YELLOW + ChatColor.BOLD + "vuelve a conectarte al servidor " + ChatColor.RESET + ChatColor.GREEN + "y ya\n" +
              "no tendrás que volver iniciar sesión!"));
        }
      }
    }
  }
}
