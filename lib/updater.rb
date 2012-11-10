require 'ixtlan/remote/sync'
class Updater < Ixtlan::Remote::Sync

  def initialize
    super Translations::Application.config.restserver
    register( Ixtlan::UserManagement::User )
#    register( Ixtlan::Gettext::Local )
    register( Ixtlan::UserManagement::Application )
  end

end
