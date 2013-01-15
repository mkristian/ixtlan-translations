source 'https://rubygems.org'

DM_VERSION    = '~> 1.2.0'

gem 'railties',             '~> 3.2.11', :require => 'rails'
gem 'tzinfo',               '~> 0.3.32'

gem 'dm-rails',             DM_VERSION
gem 'dm-sqlite-adapter',    DM_VERSION, :group => :development
gem 'dm-postgres-adapter',  DM_VERSION, :group => :production
gem 'dm-migrations',        DM_VERSION
gem 'dm-types',             DM_VERSION
gem 'dm-validations',       DM_VERSION
gem 'dm-constraints',       DM_VERSION
gem 'dm-transactions',      DM_VERSION
gem 'dm-timestamps',        DM_VERSION
gem 'dm-aggregates',        DM_VERSION

gem 'ixtlan-audit',         '~> 0.3'
gem 'ixtlan-babel',         '~> 0.2'#, :path => '../../ixtlan/ixtlan-babel'
gem 'ixtlan-error-handler', '~> 0.3'
gem 'ixtlan-guard',         '~> 0.9'#, :path => '../../ixtlan/ixtlan-guard'
gem 'ixtlan-gettext',       '~> 0.1'#, :path => '../../ixtlan/ixtlan-gettext'
gem 'ixtlan-optimistic',    '~> 0.2.1'#, :path => '../../ixtlan/ixtlan-optimistic'
gem 'ixtlan-remote',        '~> 0.1'#, :path => '../../ixtlan/ixtlan-remote'
gem 'ixtlan-session-timeout'

gem 'slf4r',                '~> 0.4.2'
gem 'pony',                 '~> 1.4'
# it allows jetty-run to obey ssl during development:
gem 'enforce-ssl',          '~> 0.2' 
gem 'cuba-api',             '~> 0.1'

group :development do
  #gem 'gwt-rails'
  gem 'ruby-maven'
  gem 'copyright-header',   '~> 1.0.7'
end

group :test do
  # Pretty printed test output
  gem 'turn',               '~> 0.9.4', :require => false
  gem 'minitest',           '~> 4.4'
  gem 'minitest-rails'
  gem 'factory_girl',       '< 3.0.0'# to allow ruby 1.8
end
