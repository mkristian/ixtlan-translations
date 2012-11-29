require 'ixtlan/user_management/application_resource'
class Ixtlan::UserManagement::Application

  # use the same table as Application
  def self.storage_name(repo = :default)
    'applications'
  end
end

class Application < Ixtlan::UserManagement::Application

  has n, :translation_keys
  
  attr_accessor :locales, :domains

  def self.get_or_create( params = {} )
    a = first( :id => params['id'], :fields => [:name, :url] )
    unless a
      a = create( params.merge( {:updated_at => DateTime.new( 0 ) }) )
    end
    a
  end

  def rollback_keys
    translation_keys.all(:state => :new).destroy!
    translation_keys.reload # to reflect the deleted entries
    #TODO bulk updates
    translation_keys.all(:state => :hidden).each do |k|
      k.update(:state => :ok)
    end
    #TODO bulk updates
    translation_keys.all(:state => :restored).each do |k|
      k.update(:state => :deleted)
    end
    translation_keys.select { |tk| tk.state != :deleted }
  end

  def commit_keys
    #TODO bulk updates
    translation_keys.all(:state => [:new, :restored]).each do |k|
      k.update(:state => :ok)
    end
    #TODO bulk updates
    translation_keys.all(:state => :hidden).each do |k|
      k.update(:state => :deleted)
    end
    translation_keys.select { |tk| tk.state != :deleted }
  end

  def update_keys(keys)
    # the update is idempotent:
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

    # all the deleted which are back are restored
    (old[:deleted] & keys).each do |t|
      tk = translation_keys.first(:name => t)
      tk.state = :restored
      tk.save
    end

    translation_keys.select { |tk| tk.state != :deleted }
  end

  def translations_all(from = nil)
    cond = {}
    cond[Translation.translation_key.application.id] = id
    cond[:updated_at.gt] = from if from
    Translation.all(cond)
  end

  def translations
    cond = {}
    cond[Translation.translation_key.application.id] = id
    cond[Translation.translation_key.state.not] = :deleted
    Translation.all(cond)
  end

  def keys
    translation_keys.all(:state.not => :deleted, :fields => [:id,:name])
  end

  # def translations_complete(locale, domain = nil)
  #   warn 'translation list for domains are not fully implemented' if domain
  #   result = Translation.all(Translation.translation_key.application.id => self.id,Translation.translation_key.state.not => :deleted,
  #                            :locale => locale, :domain => domain)
  #   keys = result.collect { |t| t.translation_key.id }
  #   # TODO if domain then collect translations of default domain
  #   missing = TranslationKey.all( :id.not => keys, :state.not => :deleted ).collect do |k| 
  #     Translation.new(:translation_key => k, 
  #                     :locale => locale,
  #                     :domain => domain,
  #                     :text => k.name)
  #   end
  #   result + missing
  # end

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

  def translation_get!(key, locale, domain, updated_at)
    if key.application != self
      raise DataMapper::ObjectNotFoundError.new 
    end
    Translation.optimistic_get!(updated_at, key, locale, domain)
  end

  def translation_update!(key, locale, domain, updated_at, text, current_user)
    if key.application != self
      # app is not matching
      raise DataMapper::ObjectNotFoundError.new 
    end
    
    # get the translation unless stale or a new instance
    begin
      t = Translation.optimistic_get!(updated_at, key.id, locale.id, domain ? domain.id : nil) if updated_at
#TODO optimistic should raise stale and not-found errors respectively
    rescue DataMapper::ObjectNotFoundError
      #
      #raise e if Translation.get(key.id, locale.id, domain ? domain.id : nil)
    end

    unless t
      t = Translation.new(:translation_key => key, 
                          :locale => locale, 
                          :domain => domain)
    end

    is_virtual = false
    if domain
      default = Translation.get(key.id, locale.id, nil)
      # delete it if text matches from default domain
      if default
        if default.text == text
          t.destroy!
          is_virtual = true
        end
      else
        # delete it if text is the default text
        if t.translation_key.name == text
          t.destroy!
          is_virtual = true
        end
      end
    else
      # delete it if text is the default text
      if t.translation_key.name == text
        t.destroy!
        is_virtual = true
      end
    end
    if is_virtual
        t = Translation.new( :text => text,
                             :translation_key => t.translation_key,
                             :locale => t.locale,
                             :domain => t.domain )
    else
      # save text
      t.text = text
      t.modified_by = current_user
      t.save
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
