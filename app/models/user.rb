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
require 'ixtlan/user_management/user_resource'
class Ixtlan::UserManagement::User

  # use the same table as User
  def self.storage_name(repo = :default)
    'users'
  end
end
class User < Ixtlan::UserManagement::User

  def self.get_or_create( params = {} )
    a = first( :id => params['id'], :fields => [:id, :login, :name] )
    unless a
      a = create( params.merge( {:updated_at => DateTime.new( 0 ) }) )
    end
    a
  end

  def allowed_applications
    @_apps ||= if root?
                 Application.all
               else
                 groups.collect { |g| g.application }.uniq
               end
  end

  def root?
    @is_root ||= !groups.detect { |g| g.root? }.nil?
  end
end
