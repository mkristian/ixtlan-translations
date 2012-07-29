class Group
  include Virtus

  attribute :name, String
  attribute :application, Application
  attribute :locales, Array[Locale]

  def initialize(attributes = {})
    heart = Heartbeat.new
    assos = attributes['locales'] || []
    ids = assos.collect { |a| a['id'].to_i }
    # the Region.where clause produces something which can not be
    # serialized into a session !!!
    self.locales = Locale.all.select { |r| ids.include? r.id }
    if self.locales.size != assos.size
      heart.beat Locale
      self.locales = Locale.all.select { |r| ids.include? r.id }
    end
    app = attributes['application']
    if app
      self.application = Application.get( app['id'] )
      if self.application.nil?
        heart.beat Application
        self.application = Application.get( app['id'] )
      end
    end
    self.name = attributes['name']
  end

  def application_of_translator
    if name == 'translator'
      a = application
      a.locales = locales
      a
    end
  end
end
