/*
 * ixtlan_gettext - helper to use fast_gettext with datamapper/ixtlan
 * Copyright (C) 2012 Christian Meier
 *
 * This file is part of ixtlan_gettext.
 *
 * ixtlan_gettext is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * ixtlan_gettext is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with ixtlan_gettext.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.mkristian.ixtlan.translations.client.models;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifiable;
import de.mkristian.gwt.rails.views.ExternalApplication;

@Json(style = Style.RAILS)
public class Application implements HasToDisplay, Identifiable, ExternalApplication {

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
              Translation t = translations.get(index);
              t.update(trans);
              return t;
          }
      }
      return trans;
  }

  public Domain detectDomain(int id) {
      for(Domain d: domains){
          if (d.getId() == id){
              return d;
          }
      }
      return Domain.DEFAULT;
  }

  public Locale detectLocale(int id) {
      if(id == 0){
          // can be null when list of locales was not set yet
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
