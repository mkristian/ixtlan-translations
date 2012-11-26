package de.mkristian.ixtlan.translations.client.models;


import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS)
public class Domain implements HasToDisplay, Identifyable {

  public final static Domain NONE = new Domain(0, "default");

  public final int id;

  private final String name;

  @JsonCreator
  public Domain(@JsonProperty("id") int id,
          @JsonProperty("name") String name ){
      this.id = id;
      this.name = name;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }
  
  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof Domain) && 
        ((Domain)other).id == id;
  }

  public String toDisplay() {
    return name;
  }
}
