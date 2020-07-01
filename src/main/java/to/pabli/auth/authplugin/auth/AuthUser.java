package to.pabli.auth.authplugin.auth;

import com.google.gson.annotations.SerializedName;

public class AuthUser {
  @SerializedName("name")
  private String name;

  @SerializedName("password")
  private String password;

  @SerializedName("premium")
  private boolean premium;


  private transient boolean connected;

  public AuthUser(String name, String password, boolean connected) {
    this.name = name;
    this.password = password;
    this.connected = connected;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isConnected() {
    return connected;
  }
  public void setConnected(boolean connected) {
    this.connected = connected;
  }

  public boolean isPremium() {
    return premium;
  }
  public void setPremium(boolean premium) {
    this.premium = premium;
  }
}
