package de.mkristian.ixtlan.translations.client.models;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import com.google.gwt.core.client.GWT;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS)
public class Application implements HasToDisplay, Identifyable {

  public final int id;

  private final String name;
  
  private final String url;

  private final List<Locale> locales;

  private final List<Domain> domains;
  
  public final List<Translation> translations;

  @Json(name = "keys")
  private final List<TranslationKey> translationKeys;
 
  private final Map<Integer, TranslationKey> id2Key = new HashMap<Integer, TranslationKey>();

  private final Locale defaultLocale;
 
  public Application(){
    this(0, null, null, null, null, null, null);
  }
  
  @SuppressWarnings("unchecked")
  @JsonCreator
  public Application(@JsonProperty("id") int id,
          @JsonProperty("name") String name,
          @JsonProperty("url") String url,
          @JsonProperty("locales") List<Locale> locales, 
          @JsonProperty("domains") List<Domain> domains, 
          @JsonProperty("translationKeys") List<TranslationKey> keys,
          @JsonProperty("translations") List<Translation> translations){
    this.id = id;
    this.name = name;
    this.url = url;
    this.locales = Collections.unmodifiableList(locales == null ? Collections.EMPTY_LIST : locales);
    this.domains = Collections.unmodifiableList(domains == null ? Collections.EMPTY_LIST : domains);
    this.translationKeys = Collections.unmodifiableList(keys == null ? Collections.EMPTY_LIST : keys);
    for(TranslationKey key : this.translationKeys){
        id2Key.put(key.id, key);
        key.setApplication(this);
    }
    if (translations != null){
        for(Translation translation : translations){
            TranslationKey key = id2Key.get(translation.getTranslationKeyId());
            key.addTranslation(translation);
        }
    }
    this.translations = null;
    this.defaultLocale = this.locales.isEmpty() ? null : this.locales.get(0);
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

  public List<Locale> getLocales() {
    return locales;
  }

  public Locale getDefaultLocale() {
    return defaultLocale;
  }

  public List<Domain> getDomains() {
    return domains;
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

  public Translation updateTranslation(Translation trans) {
      if (translations != null){
          int index = translations.indexOf(trans);
          if( index > -1){
              GWT.log(translations.get(index)+"");
              Translation t = translations.get(index);
              t.update(trans);
              // in case it is a new translation
              t.getTranslationKey().addTranslation(t);
              return t;
          }
      }
      return trans;
  }

  public Domain detectDomain(int id) {
      if( id == 0 ){
          return Domain.NONE;
      }
      for(Domain d: domains){
          if (d.getId() == id){
              return d;
          }
      }
      return null;
  }

  public Locale detectLocale(int id) {
      if(id == 0){
          // can be null when list of locales was not set
          return defaultLocale;
      }
      for(Locale l: locales){
          if (l.getId() == id){
              return l;
          }
      }
      return null;
  }
}
