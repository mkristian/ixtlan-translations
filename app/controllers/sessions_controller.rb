#
# ixtlan_translations - webapp where you can manage translations of applications
# Copyright (C) 2012 Christian Meier
#
# This file is part of ixtlan_translations.
#
# ixtlan_translations is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# ixtlan_translations is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with ixtlan_translations.  If not, see <http://www.gnu.org/licenses/>.
#
class SessionsController < LocalController

  skip_before_filter :authorize

  skip_before_filter :check_session, :only => :destroy

  skip_after_filter :audit, :only => :ping

  prepend_after_filter :reset_session, :only => :destroy

  private

  def authenticator
    @_auth ||= Authenticator.new
  end

  def login_and_password
    auth = params[:authentication] || params
    @session = auth[:login] || auth[:email]
    [ @session, auth[:password] ]
  end

  public

  def create
    user = authenticator.login( *login_and_password )
    if user      
      current_user( user )
      session = Session.new( 'user' => user,
                             'idle_session_timeout' => Translations::Application.config.idle_session_timeout,
                             'permissions' => Permissions.for( user.groups ) )

      @session = session.to_s # for the user_logger
      respond_with serializer( session )
    else
      @session = "access denied #{@session}"
      head :unauthorized
    end
  end

  def ping
    head :ok
  end

  def reset_password
    if @session = authenticator.reset_password( login_and_password[ 0 ] )
      head :ok
    else
      head :not_found
    end
  end

  def destroy
    # for the log
    @session = current_user

    # reset session happens in the after filter which allows for 
    # audit log with username which happens in another after filter
    head :ok
  end
end
