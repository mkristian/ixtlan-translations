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
                :translaton_keys => {
                  :only => [:id, :name]
                }
              }
             )

  add_context(:collection,
              :root => 'application',
              :only => [:id, :name],
              :include => {
                :locales => {
                  :only => [:id, :code]
                }
              }
             )

  default_context_key :single
end
