ixtlan-translations
-------------

* [![Build Status](https://secure.travis-ci.org/mkristian/ixtlan-translations.png)](http://travis-ci.org/mkristian/ixtlan-translations)
* [![Dependency Status](https://gemnasium.com/mkristian/ixtlan-translations.png)](https://gemnasium.com/mkristian/ixtlan-translations)
* [![Code Climate](https://codeclimate.com/badge.png)](https://codeclimate.com/github/mkristian/ixtlan-translations)

getting started - developement
==============================

install your gems

    bundle install

get the development database in place

    rake db:automigrate db:seed
	
start GWT development console (starts also rails server on port 8888 along the way)

	gwt run

just start the rails server (unless you compiled the javascript no GUI code exists)

    rails s

password for development
========================

all development users have an empty password. the username can look like this

* root
* translator[en]
* translator[en,de]
* translator[en,test]
* translator[en,de,test]
	
or in general

    {GROUP}[{LOCALE1},{LOCALE2},{DOMAIN1},{DOMAIN2}]
	
the `{GROUP}` is the only group the user belongs to. `{LOCALE1}` is 2 character locale and `{DOMAIN1}` is the name of domain.

compile the javascript
======================

    gwt compile --env=production

production will different version for different User-Agents but with env=developement that will be an all-browser version (bigger in size and less optmizied). all possible optimizations are turned on for prodcution as well.

now you can again run
  
    rails s

with the GUI popping up on [http://localhost:3000](http://localhost:3000)

updating quasi static external resources
========================================

there external resources from ixtlan-users (or any other rest service with the same API). quasi static means that the do not change often - i.e. a few new entries per month.

* User
* Application
* Domain

to update them there is a rake tasks

    rake update

update these resources wth a cron job helps keeping the local data in sync and reducing the realtime dependency when running the application.

for development the db/seed.rb provides all needed data to start. but you always can update them from [http://github.com/mkristian/ixtlan-users](ixtlan-users) by:

    cd ../ixtlan-users
    rails s
	
in another terminal
	
	cd ../ixtlan-translations
	rake update

using authentication from ixtlan-users
======================================

    cd ../ixtlan-users
    rails s
	
in another terminal start you application with

	cd ../ixtlan-translatons
	SSO=true gwt run

for the password just use **password forgotten** and look out for the email text in the ixtlan-users console. there you will find a new password :)

Configuring remote service for production
=========================================

copy the [config/password.yml.example](ixtlan-translations/tree/master/blob/config/password.example.yml) to **config/password.yml** and feed the real values in it. the password.yml should **not** be part of a public git repo.

this config file is used to setup the rest resource in [config/initializers/remote_servers.rb](ixtlan-translations/tree/master/config/initializers/remote_servers.rb)

API Server
==========

ths application is meant to be an API server for other application using gettext translations. see [API on the wiki](ixtlan-translations/wiki/API).

Contributing
------------

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request

meta-fu
-------

enjoy :) 

