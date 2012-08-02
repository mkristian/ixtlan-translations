require 'ixtlan/gettext/translation_models'
rc = Translations::Application.config.restserver
rc.register(:translations, 
            "http://localhost:3000", 
            :models => {
              'translation_key' => Ixtlan::Gettext::TranslationKey,
              'translation' => Ixtlan::Gettext::Translation,
              'locale' => Ixtlan::Gettext::Locale
            }, 
            :headers => {'X-Service-Token' => 'behappy'})
