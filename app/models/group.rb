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
    updater = Updater.new
    assos = attributes.delete('locales') || []
    ids = assos.collect { |a| a['id'].to_i }
    self.locales = Locale.all.select { |r| ids.include? r.id }
    if self.locales.size != assos.size
      updater.do_it Locale
      self.locales = Locale.all.select { |r| ids.include? r.id }
    end
    assos = attributes.delete('domains') || []
    ids = assos.collect { |a| a['id'].to_i }
    self.domains = Domain.all.select { |r| ids.include? r.id }
    if self.domains.size != assos.size
      updater.do_it Domain
      self.domains = Domain.all.select { |r| ids.include? r.id }
    end
    app = attributes.delete('application')
    if app
      @application = Application.get( app['id'] )
      if @application.nil?
        updater.do_it Application
        @application = Application.get( app['id'] )
      end
    end
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
end