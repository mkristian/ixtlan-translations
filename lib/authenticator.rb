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
require 'ixtlan/user_management/authenticator'
require 'ixtlan/user_management/dummy_authentication'
class Authenticator < Ixtlan::UserManagement::Authenticator

  def initialize
    super Translations::Application.config.rest
  end

  def user_new( params = {} )
    groups = params.delete( 'groups' ) || []
    apps = params.delete( 'applications' ) || []
    u = User.new( params[ 'user' ] || params )
    u.groups = groups.collect { |g| Group.new( g ) }
    u.applications = apps.collect { |a| Application.get_or_create( a ) }
    u
  end
end

# only dev mod without SSO needs a dummy authentication
if Ixtlan::UserManagement::DummyAuthentication.need_dummy?( Translations::Application.config.rest, :users )

  warn '[Authentication] Using dummy Authentication'

  class Authenticator 

    include Ixtlan::UserManagement::DummyAuthentication

    def user_model
      User
    end

    def setup_group( login )
      g = group_for( Group, login )
      lids = []
      dids = []
      split( login ).each do |id| 
        if id.length == 2
          lids << id
        else
          dids << id
        end
      end
      g.locales = Locale.all(:code => lids)
      g.domains = Domain.all(:name => dids)
      g.application = Application.last
      g
    end
  end
else

  warn "[Authentication] Using Authentication at #{Translations::Application.config.rest.to_server('ixtlan/user_management/authentication').url}"
  
end
