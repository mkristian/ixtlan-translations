package de.mkristian.ixtlan.translations.client.models;


import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS)
public class Application implements HasToDisplay, Identifyable {

  public final int id;

  private final String name;

  private final String url;

  @Json(name = "updated_at")
  private final Date updatedAt;

  private final List<Locale> locales;

  @Json(name = "translation_keys")
  private final List<TranslationKey> translationKeys;

  public Application(){
    this(0, null, null, null, null, null);
  }
  
  @JsonCreator
  public Application(@JsonProperty("id") int id,
          @JsonProperty("name") String name,
          @JsonProperty("url") String url,
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("locales") List<Locale> locales, 
          @JsonProperty("translationKeys") List<TranslationKey> keys){
    this.id = id;
    this.name = name;
    this.url = url;
    this.updatedAt = updatedAt;
    this.locales = Collections.unmodifiableList(locales);
    this.translationKeys = Collections.unmodifiableList(keys);
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public String getUrl(){
    return url;
  }

  public Date getUpdatedAt(){
    return updatedAt;
  }

  public List<Locale> getLocales() {
    return locales;
  }

  public List<TranslationKey> getTranslationKeys() {
    return translationKeys;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof Application) && 
        ((Application)other).id == id;
  }

  public String toDisplay() {
    return name;
  }
}
