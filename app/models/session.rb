require 'ixtlan/user_management/session_model'

class Session < Ixtlan::UserManagement::Session

  # TODO really needed ??????

  # needed for respond_with
  extend ActiveModel::Naming

  # needed for respond_with
  def errors
    []
  end

end
