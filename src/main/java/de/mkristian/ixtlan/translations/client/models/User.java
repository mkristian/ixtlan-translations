package de.mkristian.ixtlan.translations.client.models;

import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.IsUser;

@Json(style = Style.RAILS)
public class User implements IsUser {

  private String login;

  private String name;

  public final List<Application> applications;

  @JsonCreator
  public User(@JsonProperty("login") String login,
          @JsonProperty("name") String name, 
          @JsonProperty("applications") List<Application> applications){
    this.login = login;
    this.name = name;
    this.applications = applications == null ? null : Collections.unmodifiableList(applications);
  }
  
  public String getLogin(){
    return login;
  }

  public String getName(){
    return name;
  }

  public int hashCode(){
      return login.hashCode();
  }

  public boolean equals(Object other){
    return (other instanceof User) && 
        ((User)other).login == login;
  }

}
