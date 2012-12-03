Translations::Application.routes.draw do

  resources :applications do
    member do
      put :translations
    end
  end

  get 'locales/last_changes' => 'locales#last_changes'
  get 'translations/committed/last_changes' => 'translations#committed_last_changes'
  get 'translations/uncommitted/last_changes' => 'translations#uncommitted_last_changes'

  put 'translation_keys' => 'translation_keys#update'
  put 'translation_keys/commit' => 'translation_keys#commit'
  put 'translation_keys/rollback' => 'translation_keys#rollback'

  resource :session do
    member do
      post :reset_password
    end
  end
end
