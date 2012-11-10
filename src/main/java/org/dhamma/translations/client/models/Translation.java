package org.dhamma.translations.client.models;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS)
public class Translation implements HasToDisplay, Identifyable {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  @Json(name = "modified_by")
  private final User modifiedBy;

  private String text;

  public Translation(){
    this(0, null, null, null);
  }
  
  @JsonCreator
  public Translation(@JsonProperty("id") int id, 
          @JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("modifiedBy") User modifiedBy){
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.modifiedBy = modifiedBy;
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

  public User getModifiedBy(){
    return modifiedBy;
  }

  public String getText(){
    return text;
  }

  public void setText(String value){
    text = value;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof Translation) && 
        ((Translation)other).id == id;
  }

  public String toDisplay() {
    return text;
  }
}