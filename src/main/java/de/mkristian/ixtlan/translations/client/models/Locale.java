package de.mkristian.ixtlan.translations.client.models;


import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS)
public class Locale implements HasToDisplay, Identifyable {

  public final int id;

  private String code;

  public Locale(){
    this(0);
  }
  
  @JsonCreator
  public Locale(@JsonProperty("id") int id){
      this.id = id;
  }

  public int getId(){
    return id;
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
