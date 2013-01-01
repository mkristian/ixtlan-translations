source 'https://rubygems.org'

RAILS_VERSION = '~> 3.2.9'
DM_VERSION    = '~> 1.2.0'

gem 'activesupport',  RAILS_VERSION, :require => 'active_support'
gem 'actionpack',     RAILS_VERSION, :require => 'action_pack'
#gem 'actionmailer',   RAILS_VERSION, :require => 'action_mailer'
#gem 'activeresource', RAILS_VERSION, :require => 'active_resource'
gem 'railties',       RAILS_VERSION, :require => 'rails'
gem 'tzinfo',         '~> 0.3.32'

gem 'dm-rails',               '~> 1.2.1'
gem 'dm-sqlite-adapter', DM_VERSION

# You can use any of the other available database adapters.
# This is only a small excerpt of the list of all available adapters
# Have a look at
#
#  http://wiki.github.com/datamapper/dm-core/adapters
#  http://wiki.github.com/datamapper/dm-core/community-plugins
#
# for a rather complete list of available datamapper adapters and plugins

# gem 'dm-sqlite-adapter',    DM_VERSION
# gem 'dm-mysql-adapter',     DM_VERSION
# gem 'dm-postgres-adapter',  DM_VERSION
# gem 'dm-oracle-adapter',    DM_VERSION
# gem 'dm-sqlserver-adapter', DM_VERSION

gem 'dm-migrations',   DM_VERSION
gem 'dm-types',        DM_VERSION
gem 'dm-validations',  DM_VERSION
gem 'dm-constraints',  DM_VERSION
gem 'dm-transactions', DM_VERSION
gem 'dm-aggregates',   DM_VERSION
gem 'dm-timestamps',   DM_VERSION
gem 'dm-observer',     DM_VERSION


group :test do
  # Pretty printed test output
  gem 'turn', '~> 0.9.4', :require => false
end

#gem "gwt-rails", :group => :developement
gem 'ruby-maven', :group => :developement
gem 'maven-tools', :group => :developement
gem "ixtlan-optimistic"#, :path => '../../ixtlan/ixtlan-optimistic'
gem "ixtlan-babel"#, :path => '../../ixtlan/ixtlan-babel'

#gem "ixtlan-core", :path => '../../ixtlan/ixtlan-core'
gem "ixtlan-session-timeout"
gem "ixtlan-guard"#, :path => '../../ixtlan/ixtlan-guard'
gem "jruby-openssl", "0.8.1", :platforms => :jruby

gem 'virtus', '~> 0.5.0'
#gem 'backports', :platforms => :ruby_18

group :test, :development do
  gem 'minitest'
  gem 'minitest-rails'
  gem 'factory_girl', '< 3.0.0'# to allow ruby 1.8
end

gem 'ixtlan-remote'#, :path => '../../ixtlan/ixtlan-remote'
gem 'ixtlan-gettext'#, :path => '../../ixtlan/ixtlan-gettext'
#gem 'rest-client', '1.6.7'

group :production do
  gem 'dm-postgres-adapter', DM_VERSION
end
#gem 'fast_gettext'

gem 'copyright-header', '~> 1.0.7', :group => :development
