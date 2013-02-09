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
Translations::Application.routes.draw do

  resources :applications do
    member do
      put :translations
    end
  end

  get 'locales/last_changes' => 'locales#last_changes'
  get 'translations/committed/last_changes' => 'translations#committed_last_changes'
  get 'translations/uncommitted/last_changes' => 'translations#uncommitted_last_changes'
  get 'translations/last_changes' => 'translations#uncommitted_last_changes'

  get 'translation_keys/committed/last_changes' => 'translation_keys#committed_last_changes'
  get 'translation_keys/last_changes' => 'translation_keys#uncommitted_last_changes'

  put 'translation_keys' => 'translation_keys#update'
  put 'translation_keys/commit' => 'translation_keys#commit'
  put 'translation_keys/rollback' => 'translation_keys#rollback'

  resource :session do
    member do
      post :reset_password
      get :ping
    end
  end
end
