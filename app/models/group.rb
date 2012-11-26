require 'ixtlan/user_management/group_model'
class Group < Ixtlan::UserManagement::Group

  attribute :application, Application
  attribute :locales, Array[Locale]
  attribute :domains, Array[Domain]

  def initialize(attributes = {})
    updater = Updater.new
    assos = attributes.delete('locales') || []
    ids = assos.collect { |a| a['id'].to_i }
    self.locales = Locale.all.select { |r| ids.include? r.id }
    if self.locales.size != assos.size
      updater.do_it Locale
      self.locales = Locale.all.select { |r| ids.include? r.id }
    end
    assos = attributes.delete('domains') || []
    ids = assos.collect { |a| a['id'].to_i }
    self.domains = Domain.all.select { |r| ids.include? r.id }
    if self.domains.size != assos.size
      updater.do_it Domain
      self.domains = Domain.all.select { |r| ids.include? r.id }
    end
    app = attributes.delete('application')
    if app
      @application = Application.get( app['id'] )
      if @application.nil?
        updater.do_it Application
        @application = Application.get( app['id'] )
      end
    end
    super
  end

  def application
    if name == 'translator'
p self
      a = @application
      a.locales = self.locales
      a.domains = self.domains
      a
    end
  end
end
