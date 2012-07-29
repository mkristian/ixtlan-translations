package org.dhamma.translations.client.models;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS)
public class Locale implements HasToDisplay, Identifyable {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  private String code;

  public Locale(){
    this(0, null, null);
  }
  
  @JsonCreator
  public Locale(@JsonProperty("id") int id, 
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

  public String getCode(){
    return code;
  }

  public void setCode(String value){
    code = value;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof Locale) && 
        ((Locale)other).id == id;
  }

  public String toDisplay() {
    return code;
  }
}
