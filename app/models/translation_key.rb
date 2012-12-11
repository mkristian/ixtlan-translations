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
class TranslationKey

  include DataMapper::Resource

  property :id, Serial

  property :name, Text, :required => true, :length => 4066, :unique_index => :app_name

  property :state, Enum[:ok, :new, :hidden, :deleted, :restored], :required => true

  timestamps :at

  property :application_id, Integer, :unique_index => :app_name
  belongs_to :application
end
