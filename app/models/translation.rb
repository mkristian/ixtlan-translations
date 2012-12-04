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
class Translation

  include DataMapper::Resource

  belongs_to :translation_key, :key => true
  belongs_to :locale, :key => true
  belongs_to :domain, :key => true, :required => false

  property :text, Text, :length => 4096

  timestamps :at

  belongs_to :modified_by, 'User'

  def app_id
    translation_key.application.id
  end

  def self.update_or_virtual(key, locale, domain, text, updated_at = nil)
    # get the translation unless stale
    t = if updated_at
          optimistic_get!(updated_at, 
                          key.id, 
                          locale.id, 
                          domain ? domain.id : nil)
        else
          get(key.id, locale.id, domain ? domain.id : nil)
        end

    #  or a create new instance
    unless t
      t = new(:translation_key => key, 
              :locale => locale, 
              :domain => domain)
    end

    # check if the 'text' has a default value, if so delete the existing entry
    if domain
      default = get(key.id, locale.id, nil)
      # delete it if text matches from default domain
      if default
        if default.text == text
          t.destroy!
          t = nil
        end
      else
        # delete it if text is the default text
        if t.translation_key.name == text
          t.destroy!
          t = nil
        end
      end
    else
      # delete it if text is the default text
      if t.translation_key.name == text
        t.destroy!
        t = nil
      end
    end
    
    # if we had a default we need a "virtual" instance which can not be saved
    if t.nil?
      t = new( :translation_key => key,
               :locale => locale,
               :domain => domain )
      # should not be save
      def t.save
        true
      end
    end

    # set the text
    t.text = text
    t
  end
end