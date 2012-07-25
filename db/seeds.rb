# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Daley', :city => cities.first)

c = Configuration.instance
if defined? Ixtlan::Errors
  c.errors_keep_dumps = 30
end
if defined? Ixtlan::Audit
  c.audits_keep_logs = 90
end
if defined? Ixtlan::Sessions
  c.idle_session_timeout = 15
end
# TODO maybe a modified_by= assignment is missing
c.save
