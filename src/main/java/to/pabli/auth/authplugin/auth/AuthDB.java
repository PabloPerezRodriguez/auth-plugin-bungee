package to.pabli.auth.authplugin.auth;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AuthDB {
  @SerializedName("userList")
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
