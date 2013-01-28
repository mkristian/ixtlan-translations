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
    @translation_keys = 
      application.translation_keys_all( true, 
                                        params[ :updated_at ] )
    respond_with serializer( @translation_keys ).use( :remote )
  end

  # GET /translation_keys/last_changes
  def uncommitted_last_changes
    @translation_keys =
      application.translation_keys_all( false, 
                                        params[ :updated_at ] )
    respond_with serializer( @translation_keys ).use( :remote )
  end

  # PUT /translation_keys/commit
  def commit
    @transaction_keys = application.commit_keys
    respond_with serializer( @transaction_keys ).use( :remote )
  end

  # PUT /translation_keys/rollback
  def rollback
    @transaction_keys = application.rollback_keys
    respond_with serializer( @transaction_keys ).use( :remote )
  end

  # PUT /translation_keys
  def update
    @transaction_keys = application.update_keys( params[ :translation_keys ] ) )
    respond_with serializer( @transaction_keys ).use( :remote )
  end
end
