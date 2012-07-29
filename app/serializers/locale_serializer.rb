require 'ixtlan/babel/serializer'

class LocaleSerializer < Ixtlan::Babel::Serializer

  model Locale

  add_context(:single,
              :root => 'locale'
             )

  add_context(:collection,
              :root => 'locale',
              :except => [:created_at]
             )

  default_context_key :single
end
