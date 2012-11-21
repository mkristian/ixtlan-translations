package de.mkristian.ixtlan.translations.client.models;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS, name = "remote_permission")
public class RemotePermission implements HasToDisplay, Identifyable {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  private String ip;

  private String token;

  public RemotePermission(){
    this(0, null, null);
  }
  
  @JsonCreator
  public RemotePermission(@JsonProperty("id") int id, 
          @JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt){
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public int getId(){
    return id;
  }

  public Date getCreatedAt(){
    return createdAt;
  }

  public Date getUpdatedAt(){
    return updatedAt;
  }

  public String getIp(){
    return ip;
  }

  public void setIp(String value){
    ip = value;
  }

  public String getToken(){
    return token;
  }

  public void setToken(String value){
    token = value;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof RemotePermission) && 
        ((RemotePermission)other).id == id;
  }

  public String toDisplay() {
    return ip;
  }
}
