package to.pabli.auth.authplugin.events;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import to.pabli.auth.authplugin.AuthPlugin;
import to.pabli.auth.authplugin.auth.AuthUser;

public class UserJoinServerEvent implements Listener {
  @EventHandler
  public void onServerConnect(ServerConnectEvent event) {
    ProxiedPlayer player = event.getPlayer();
    ServerInfo mainServerInfo = ProxyServer.getInstance().getServerInfo("main");
    ServerInfo lobbyServerInfo = ProxyServer.getInstance().getServerInfo("lobby");

    if (player.getPendingConnection().isOnlineMode()) {
      event.setTarget(mainServerInfo);
    } else {
      AuthUser authUser = AuthPlugin.INSTANCE.authDBInstance.getUserWithName(player.getDisplayName());
      if (authUser == null || !authUser.isConnected()) {
        event.setTarget(lobbyServerInfo);
      } else {
        event.setTarget(mainServerInfo);
      }
    }
  }

}
