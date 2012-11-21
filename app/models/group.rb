require 'ixtlan/user_management/group_model'
class Group < Ixtlan::UserManagement::Group

  attribute :application, Application
  attribute :locales, Array[Locale]
  attribute :domains, Array[Domain]

  def initialize(attributes = {})
    updater = Updater.new
    assos = attributes['locales'] || []
    ids = assos.collect { |a| a['id'].to_i }
    self.locales = Locale.all.select { |r| ids.include? r.id }
    if self.locales.size != assos.size
      updater.do_it Locale
      self.locales = Locale.all.select { |r| ids.include? r.id }
    end
    assos = attributes['domains'] || []
    ids = assos.collect { |a| a['id'].to_i }
    self.domains = Domain.all.select { |r| ids.include? r.id }
    if self.domains.size != assos.size
      updater.do_it Domain
      self.domains = Domain.all.select { |r| ids.include? r.id }
    end
    app = attributes['application']
    if app
      self.application = Application.get( app['id'] )
      if self.application.nil?
        updater.do_it Application
        self.application = Application.get( app['id'] )
      end
    end
    super
    #self.name = attributes['name']
  end

  def application
    if name == 'translator'
      a = @application
      a.locales = self.locales
      a.domains = self.domains
      a
    end
  end
end
