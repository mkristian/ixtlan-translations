require 'ixtlan/remote/sync'
class Updater < Ixtlan::Remote::Sync

  def initialize
    super Translations::Application.config.rest
    register( User )
    register( Application )
    register( Domain )
  end

end
