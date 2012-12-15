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
class ApplicationsController < LocalController

  guard_filter :select_groups, :except => [:index]

  private
  
  def select_groups(groups)
    groups.select { |g| g.application == application || g.root? }
  end

  def application
    @application ||= Application.get!(params[:id])
  end

  public

  # GET /applications
  def index
    @applications = current_user.allowed_applications
    respond_with serializer( @applications )
  end

  # GET /applications/1
  def show
    respond_with serializer(application)
  end

  def translations
    params = self.params[:translation] || {}
    key = TranslationKey.get!(params[:translation_key_id])
    locale = Locale.get!(params[:locale_id])
    domain = Domain.get(params[:domain_id])

    translation = application.translation_update!(key,
                                                  locale,
                                                  domain,
                                                  params[:updated_at],
                                                  params[:text],
                                                  current_user)

    respond_with serializer( translation )
  end

  # POST /applications/1/remote_permission
  def create_remote_permission
    remote_permission = application.remote_permission_new(params[:remote_permission])
    remote_permission.modified_by = current_user
    remote_permission.save

    respond_with(serializer(remote_permission))
  end

  # PUT /applications/1/remote_permission/2
  def update_remote_permission   
    remote_permission = application.remote_permission_get!(params[:remote_permission_id],
                                                           updated_at)
    remote_permission.attributes = params[:remote_permission] || {}
    remote_permission.modified_by = current_user
    remote_permission.save

    respond_with(serializer(remote_permission))
  end
end
