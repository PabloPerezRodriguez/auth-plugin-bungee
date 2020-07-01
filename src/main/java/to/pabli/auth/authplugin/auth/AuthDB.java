package to.pabli.auth.authplugin.auth;

import java.util.ArrayList;

public class AuthDB {
  private ArrayList<AuthUser> userList;

  public AuthDB() {
    userList = new ArrayList<>();
  }

  public ArrayList<AuthUser> getUserList() {
    return userList;
  }

  public AuthUser getUserWithName(String name) {
    return userList
        .stream()
        .filter(user -> user.getName().equals(name))
        .findFirst()
        .orElse(null);
  }
}
