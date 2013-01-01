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


import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifiable;

@Json(style = Style.RAILS)
public class Locale implements HasToDisplay, Identifiable {

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
