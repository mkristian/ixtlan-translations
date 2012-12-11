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
class TranslationKeysController < RemoteController

  # GET /translation_keys/committed/last_changes
  def committed_last_changes
    @translations = application.translation_keys_all( true, 
                                                      params[:updated_at] )
    respond_with serializer( @translations ).use(:remote)
  end

  # GET /translation_keys/uncommitted/last_changes
  def uncommitted_last_changes
    @translations = application.translation_keys_all( false, 
                                                      params[:updated_at] )
    respond_with serializer( @translations ).use(:remote)
  end

  # PUT /translation_keys/commit
  def commit
    @transaction_keys = serializer(application.commit_keys).use(:remote)
    respond_with @transaction_keys
  end

  # PUT /translation_keys/rollback
  def rollback
    @transaction_keys = serializer(application.rollback_keys).use(:remote)
    respond_with @transaction_keys
  end

  # PUT /translation_keys
  def update
    @transaction_keys = serializer(application.update_keys(params[:translation_keys])).use(:remote)
    respond_with @transaction_keys
  end
end
