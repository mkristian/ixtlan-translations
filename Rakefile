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
  yml.each do |k,v|
    key = TranslationKey.first( :name => k )
    t = Translation.update_or_virtual(key, en, d, v)
    t.modified_by = u
    if t.save
      p t
    else
      p t.errors
    end
  end
end

task :headers do
  require 'rubygems'
  require 'copyright_header'

  args = { '--license' => 'AGPL3', 
    '--copyright-software' => 'ixtlan_translations',
    '--copyright-software-description' => 'webapp where you can manage translations of applications',
    '--copyright-holder' => 'Christian Meier',
    '--copyright-year' => '2012',
    '--add-path' => 'lib',
    '--output-dir' => '.' 
  }

  ARGV.replace( args.to_a.flatten )
  command_line = CopyrightHeader::CommandLine.new
  command_line.execute

  args['--add-path'] = 'src'
  ARGV.replace( args.to_a.flatten )
  command_line = CopyrightHeader::CommandLine.new
  command_line.execute

  args['--add-path'] = 'app'
  ARGV.replace( args.to_a.flatten )
  command_line = CopyrightHeader::CommandLine.new
  command_line.execute

  args['--add-path'] = 'config'
  ARGV.replace( args.to_a.flatten )
  command_line = CopyrightHeader::CommandLine.new
  command_line.execute

  args['--add-path'] = 'db/seeds.rb'
  ARGV.replace( args.to_a.flatten )
  command_line = CopyrightHeader::CommandLine.new
  command_line.execute
end

# vim: syntax=Ruby
