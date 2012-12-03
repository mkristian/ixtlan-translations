# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Daley', :city => cities.first)
unless u = User.get(1)
  u = User.new(:name => "System", :login => "system", :updated_at => DateTime.new(0))
  u.id = 1
  u.save
  p u if u.valid?
end

unless a = Application.get(1)
  a = Application.create(:name => 'users', :url => 'http://localhost.localnet', :updated_at => DateTime.now)
  if a.valid?
    p a
    r = RemotePermission.create(:modified_by => u, :token => 'be happy', :ip => '127.0.0.1', :application => a)
    p r if r.valid?
  end
end

unless a = Application.get(2)
  a = Application.create(:name => 'development', :url => 'http://localhost.localnet', :updated_at => DateTime.now)
  if a.valid?
    p a 
    r = RemotePermission.create(:modified_by => u, :token => 'behappy', :ip => '127.0.0.1', :application => a)
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

unless test = Domain.get(1)
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
