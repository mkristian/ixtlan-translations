require 'ixtlan/user_management/authenticator'
class Authenticator < Ixtlan::UserManagement::Authenticator

  def initialize
    super Translations::Application.config.rest
  end

  def user_new(params)
    User.new(params)
  end
end
