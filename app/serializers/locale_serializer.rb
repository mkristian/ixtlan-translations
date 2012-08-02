require 'ixtlan/babel/serializer'

class LocaleSerializer < Ixtlan::Babel::Serializer

  model Locale

  add_context(:collection,
              :root => 'locale'
             )

  default_context_key :collection
end
