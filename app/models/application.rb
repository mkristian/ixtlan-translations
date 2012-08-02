class Application

  include DataMapper::Resource

  property :id, Serial, :auto_validation => false

  property :name, String, :required => true, :unique => true, :length => 32
  property :url, String, :required => true, :format => :url, :length => 64, :lazy => true
  property :updated_at, DateTime, :required => true, :lazy => true

  has n, :translation_keys
  
  attr_accessor :locales
  
  # do not record timestamps since they are set from outside
  def set_timestamps_on_save
  end

  def rollback_keys
    translation_keys.all(:state => :new).destroy!
    translation_keys.reload # to reflect the deleted entries
    translation_keys.all(:state => :hidden).each do |k|
      k.update(:state => :ok)
    end
    translation_keys.all(:state => :restored).each do |k|
      k.update(:state => :deleted)
    end
    translation_keys.select { |tk| tk.state != :deleted }
  end

  def commit_keys
    translation_keys.all(:state => [:new, :restored]).each do |k|
      k.update(:state => :ok)
    end
    translation_keys.all(:state => :hidden).each do |k|
      k.update(:state => :deleted)
    end
    translation_keys.select { |tk| tk.state != :deleted }
  end

  def update_keys(keys)
    # the update is idempotent
    # update_keys(set1) + update_keys(set2) = update_keys(set2)
    old = {:new => [], :ok => [], :hidden => [], :deleted => [], :restored => []}
    translation_keys.each do |k| 
      old[k.state] << k.name
    end

    #create the ones which are missing
    (keys - (old[:new] | old[:ok] | old[:hidden] | old[:deleted] | old[:restored])).each do |t|
      translation_keys.create(:name => t, :state => :new)
    end
    
    # delete the new entries which are gone now
    translation_keys.all(:name => (old[:new] - keys)).destroy!
    translation_keys.reload # to reflect the deleted entries

    # hide the entries which shall be deleted on commit
    (old[:ok] - keys).each do |t|
      tk = translation_keys.first(:name => t)
      tk.state = :hidden
      tk.save
    end

    # "delete" the restored entries which are not anymore
    (old[:restored] - keys).each do |t|
      tk = translation_keys.first(:name => t)
      tk.state = :deleted
      tk.save
    end

    # unhide the entries which are back
    (old[:hidden] & keys).each do |t|
      tk = translation_keys.first(:name => t)
      tk.state = :ok
      tk.save
    end

    # all the deteled which are back are restored
    (old[:deleted] & keys).each do |t|
      tk = translation_keys.first(:name => t)
      tk.state = :restored
      tk.save
    end

    translation_keys.select { |tk| tk.state != :deleted }
  end

  def translations_all(code = nil, from = nil)
    cond = {}
    cond[Translation.translation_key.application.id] = id
    cond[Translation.locale.code] = code if code
    cond[:updated_at.gt] = from if from
    Translation.all(cond)
  end

  def translation_new(params)
    locale_id = params.delete(:locale)[:id]
    key = TranslationKey.get!(params.delete(:translation_key)[:id])
    if key.application != self
      raise DataMapper::ObjectNotFoundError.new 
    end
    locale = Locale.get!(locale_id)
    t = Translation.new(params)
    t.locale = locale
    t.translation_key = key
    t
  end

  def translation_get!(key_id, locale_id, updated_at)
    t = Translation.optimistic_get!(updated_at, key_id, locale_id)
    if t.translation_key.application != self
      raise DataMapper::ObjectNotFoundError.new 
    end
    t
  end

  def remote_permission_new(params)
    t = RemotePermission.new(params)
    t.application = self
    t
  end

  def remote_permission_get!(id, updated_at)
    t = RemotePermission.optimistic_get!(updated_at, id)
    if t.application != self
      raise DataMapper::ObjectNotFoundError.new 
    end
    t
  end
end
