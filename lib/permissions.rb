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
class Permissions

  def self.for(groups)
    Rails.configuration.guard.permissions( groups ) do |r,a|
      if r == 'applications'
        l = a.collect { |g| g.locales }
        l.flatten!
        l.uniq!;
        l = [Locale.new(:code => '')] if l.empty?
        d = a.collect { |g| g.domains }
        d.flatten!
        d.uniq!;
        d = [Domain.new(:name => '')] if d.empty?
        # calculate crossproduct of locales X domains
        r = d.collect do |dd| 
          l.collect { |ll| "#{ll.code}|#{dd.name}" }
        end
        r.flatten!
        r
      end
    end
  end
end