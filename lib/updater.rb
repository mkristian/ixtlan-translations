require 'ixtlan/remote/updater'
class Updater < Ixtlan::Remote::Updater

  def self.do_it(clazz = nil)
    if clazz
      new.do_it(clazz)
    else
      new.do_it
    end
  end

  def initialize
    super Translations::Application.config.restserver
    register(:user)
    register(:locale)
    register(:application)
  end

end
