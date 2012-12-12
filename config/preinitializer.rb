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
require 'yaml'
require 'erb'
module Ixtlan
  class Configurator

    def self.symbolize_keys(h)
      result = {}

      h.each do |k, v|
        v = ' ' if v.nil?
        if v.is_a?(Hash)
          result[k.to_sym] = symbolize_keys(v) unless v.size == 0
        else
          result[k.to_sym] = v unless k.to_sym == v.to_sym
        end
      end

      result
    end

    def self.load(file)
      if File.exists?(file)
        symbolize_keys(YAML::load(ERB.new(IO.read(file)).result))
      else
        f = file.sub /#{File.dirname(File.dirname(__FILE__)) }\/?/, ''
        warn "no file #{f} to load"
      end
    end
  end
end

CONFIG = Ixtlan::Configurator.load(File.join(File.dirname(__FILE__), 'passwords.yml')) || {}