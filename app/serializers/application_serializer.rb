require 'ixtlan/babel/serializer'

class ApplicationSerializer < Ixtlan::Babel::Serializer

  model Application

  add_context(:single,
              :root => 'application',
              :only => [:id, :name],
              :include => {
                :locales => {
                  :only => [:id, :code]
                },
                :domains => {
                  :only => [:id, :name]
                },
                :keys => {
                  :only => [:id, :name]
                },
                :translations => {
                  :only => [:translation_key_id, :locale_id, :domain_id, :text, :updated_at],
                  :include => {
                    :modified_by => {
                      :only => [:id, :login, :name]
                    }
                  },
                  :methods => [:app_id]
                }
              }
             )

  add_context(:collection,
              :root => 'application',
              :only => [:id, :name]
             )
end
