Translations::Application.routes.draw do

  resources :applications

  get 'locales/last_changes' => 'locales#last_changes'
  get 'translations/last_changes' => 'translations#last_changes'

  put 'translation_keys' => 'translation_keys#update'
  put 'translation_keys/commit' => 'translation_keys#commit'
  put 'translation_keys/rollback' => 'translation_keys#rollback'

  resource :session do
    member do
      post :reset_password
    end
  end
end
