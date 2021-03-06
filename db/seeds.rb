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
# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
unless u = User.get(1)
  u = User.new(:name => "System", :login => "system", :updated_at => DateTime.new(1))
  u.id = 1
  u.save
  p u if u.valid?
end

unless a = Application.get(1)
  a = Application.create(:name => 'users', :url => 'http://localhost.localnet', :updated_at => DateTime.now)
  if a.valid?
    p a
    r = RemotePermission.create(:modified_by => u, :authentication_token => 'behappy', :allowed_ip => '127.0.0.1', :application => a)
    p r if r.valid?
  end
end

unless a = Application.get(2)
  a = Application.create(:name => 'development', :url => 'http://localhost.localnet', :updated_at => DateTime.now)
  if a.valid?
    p a 
    r = RemotePermission.create(:modified_by => u, :authentication_token => 'be happy', :allowed_ip => '127.0.0.1', :application => a)
    if r.valid?
      p r
    else
      puts "-" * 80
      p r.errors
      puts
    end
  end
end

unless en = Locale.get(1)
  en = Locale.create(:code => 'en', :updated_at => DateTime.now)
  p en if en.valid?
  de = Locale.create(:code => 'de', :updated_at => DateTime.now)
  p de if de.valid?
end

# load predefined
Domain.ALL
Domain.DEFAULT
unless test = Domain.get(3)
  test = Domain.create(:name => 'test', :updated_at => DateTime.now)
  p test if test.valid?
end

if defined? ::Configuration
  c = ::Configuration.instance
  if defined? Ixtlan::Errors
    c.errors_keep_dumps = 30
  end
  if defined? Ixtlan::Audit
    c.audits_keep_logs = 90
  end
  if defined? Ixtlan::Sessions
    c.idle_session_timeout = 15
  end
  c.modified_by = u
  c.save
  p c if c.valid?
end

puts 'seeding done'
