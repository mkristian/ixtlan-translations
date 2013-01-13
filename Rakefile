#-*- mode: ruby -*-

require File.expand_path('../config/application', __FILE__)
require 'rake'

Translations::Application.load_tasks

desc 'triggers the update of remote resources'
task :update => [:environment] do
    sync = Updater.new
    sync.do_it

    puts "#{Time.now.strftime('%Y-%m-%d %H:%M:%S')}\n\t#{sync}"
end

task :rideboard => [:environment] do
  yml = YAML.load_file('rideboard.yml')['en']
  en = Locale.first( :code => 'en' )
  d = Domain.last
  u = User.first
  TranslationKey.all.each { |k| p k.name }
  yml.each do |k,v|
    key = TranslationKey.first( :name => k )
    if key
      t = Translation.update_or_virtual(key, en, d, v)
      t.modified_by = u
      if t.save
        p t
      else
        p t.errors
      end
    else
      p k
    end
  end
end

task :headers do
  require 'rubygems'
  require 'copyright_header'

  args = {
    :license => 'AGPL3',
    :copyright_software => 'ixtlan_translations',
    :copyright_software_description => 'webapp where you can manage translations of applications',
    :copyright_holders => ['Christian Meier'],
    :copyright_years => [Time.now.year],
    :add_path => ['lib', 'app', 'src', 'config', 'db/seeds.rb'].join( File::PATH_SEPARATOR ),
    :output_dir => './'
  }

  command_line = CopyrightHeader::CommandLine.new( args )
  command_line.execute
end

# vim: syntax=Ruby
