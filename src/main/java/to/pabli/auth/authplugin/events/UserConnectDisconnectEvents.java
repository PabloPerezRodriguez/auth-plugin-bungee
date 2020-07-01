package to.pabli.auth.authplugin.events;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import to.pabli.auth.authplugin.AuthPlugin;
import to.pabli.auth.authplugin.auth.AuthUser;

public class UserConnectDisconnectEvents implements Listener {
  @EventHandler
  public void onPreLogin(PreLoginEvent event) {
    PendingConnection pendingConnection = event.getConnection();
    String playerName = pendingConnection.getName();
    AuthUser authUser = AuthPlugin.INSTANCE.authDBInstance.getUserWithName(playerName);
    if (authUser != null) {
      if (authUser.isPremium()) pendingConnection.setOnlineMode(true);
    }
  }

  @EventHandler
  public void onPostLogin(PostLoginEvent event) {
    ProxiedPlayer player = event.getPlayer();
    if (player.getPendingConnection().isOnlineMode()) {
      AuthUser authUser = AuthPlugin.INSTANCE.authDBInstance.getUserWithName(player.getDisplayName());
      authUser.setConnected(true);
    }
  }

  @EventHandler
  public void onLogout(PlayerDisconnectEvent event) {
    String playerDisplayName = event.getPlayer().getDisplayName();
    AuthUser user = AuthPlugin.INSTANCE.authDBInstance.getUserWithName(playerDisplayName);
    if (user != null) {
      user.setConnected(false);
    }
  }
}
