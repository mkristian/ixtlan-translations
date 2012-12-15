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
require 'ixtlan/user_management/group_model'
class Group < Ixtlan::UserManagement::Group

  attribute :application, Application
  attribute :locales, Array[Locale]
  attribute :domains, Array[Domain]

  def initialize(attributes = {})
    self.locales = setup( Locale, attributes.delete( 'locales' ) )
    self.domains = setup( Domain, attributes.delete( 'domains' ) )
    setup_application( attributes.delete( 'application' ) )
    super
  end

  def application
    if name == 'translator'
      a = @application
      a.locales = self.locales
      a.domains = self.domains
      a
    end
  end

  def root?
    @is_root ||= name == 'root'
  end

  private

  def updater
    @updater ||= Updater.new
  end

  def setup_application( app )
    if app
      @application = Application.get( app['id'] )
      if @application.nil?
        updater.do_it Application
        @application = Application.get( app['id'] )
      end
    end
  end

  def setup( model, assos )
    assos ||= []
    ids = assos.collect { |a| a['id'].to_i }
    result = model.all.select { |r| ids.include? r.id }
    if result.size != assos.size
      updater.do_it model
      result = model.all.select { |r| ids.include? r.id }
    end
    result
  end

end
