#
# ixtlan_translations - webapp where you can manage translations of applications
# Copyright (C) 2012 Christian Meier
#
# This file is part of ixtlan_translations.
#
# ixtlan_translations is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# ixtlan_translations is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with ixtlan_translations.  If not, see <http://www.gnu.org/licenses/>.
#
require 'ixtlan/user_management/application_resource'
class Ixtlan::UserManagement::Application

  # use the same table as Application
  def self.storage_name(repo = :default)
    'applications'
  end
end

class Application < Ixtlan::UserManagement::Application

  has n, :translation_keys
  
  attr_accessor :locales, :domains

  def self.get_or_create( params = {} )
    a = first( :id => params['id'], :fields => [:name, :url] )
    unless a
      a = create( params.merge( {:updated_at => DateTime.new( 0 ) }) )
    end
    a
  end

  def transit_state( from, to )
    #TODO bulk updates
    translation_keys.all(:state => from).each do |k|
      k.update(:state => to)
    end
  end
  private :transit_state

  def rollback_keys
    translation_keys.all(:state => :new).destroy!
    translation_keys.reload # to reflect the deleted entries
    transit_state( :hidden, :ok )
    transit_state( :restored, :deleted )
    translation_keys.select { |tk| tk.state != :deleted }
  end

  def commit_keys
    transit_state( [:new, :restored], :ok )
    transit_state( :hidden, :deleted )
    translation_keys.select { |tk| tk.state != :deleted }
  end

  def create_keys_and_return_overview( keys )
    old = { :new => [],
      :ok => [],
      :hidden => [],
      :deleted => [],
      :restored => [] }
    all = []

    translation_keys.each do |k| 
      old[k.state] << k.name
      all << k.name
    end
    all.uniq!

    #create the ones which are missing
    (keys - all).each do |t|
      t = translation_keys.create(:name => t, :state => :new)
      warn t.errors.inspect unless t.valid?
    end
    
    old
  end
  private :create_keys_and_return_overview

  def update_state( names, new )
    translation_keys.all( :name => names ).update( :state => new )
  end
  private :update_state

  # update_keys(set1) + update_keys(set2) = update_keys(set2)
  def update_keys( keys )
    old = create_keys_and_return_overview( keys )

    # delete the new entries which are gone now
    translation_keys.all( :name => ( old[:new] - keys ) ).destroy!
    translation_keys.reload # to reflect the deleted entries

    # hide the entries which shall be deleted on commit
    update_state( old[:ok] - keys, :hidden )

    # "delete" the restored entries which are not anymore
    update_state( old[:restored] - keys, :deleted )

    # unhide the entries which are back
    update_state( old[:hidden] & keys, :ok )

    # all the deleted which are back are restored
    update_state( old[:deleted] & keys, :restored )

    translation_keys.select { |tk| tk.state != :deleted }
  end

  def translation_keys_all( committed = true, from = nil )
    cond = { :fields => [ :id, :name, :updated_at ] }
    cond[:updated_at.gt] = from if from
    if committed
      cond[ :state ] = :ok
    else
      cond[ :state.not ] = :deleted
    end
    translation_keys.all( cond )
  end

  def translations_all( committed = true, from = nil )
    cond = { :fields => [ :text, 
                          :translation_key_id, 
                          :locale_id, 
                          :domain_id, 
                          :updated_at ] }
    cond[:updated_at.gt] = from if from
    if committed
      cond[ Translation.translation_key.state ] = :ok
    else
      cond[ Translation.translation_key.state.not ] = :deleted
    end
    translations.all( cond )
  end

  def translations
    Translation.all( :fields => [ :translation_key_id, 
                                  :locale_id, 
                                  :domain_id, 
                                  :text, 
                                  :updated_at ],
                     Translation.translation_key.application.id => self.id,
                     Translation.translation_key.state.not => :deleted )
  end

  def keys
    translation_keys.all( :state.not => :deleted, :fields => [ :id, :name ] )
  end

  def translation_update!(key, locale, domain, updated_at, text, current_user)
    if key.application != self
      # app is not matching
      raise DataMapper::ObjectNotFoundError.new 
    end

    t = Translation.update_or_virtual(key, locale, domain, text, updated_at)
    t.modified_by = current_user
    t.save
    t
  end

  def remote_permission_new(params)
    t = RemotePermission.new(params)
    t.application = self
    t
  end

  def remote_permission_get!(id, updated_at)
    t = RemotePermission.optimistic_get!(updated_at, id)
    if t.application != self
      raise DataMapper::ObjectNotFoundError.new 
    end
    t
  end
end
