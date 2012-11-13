require 'ixtlan/gettext/locale_resource'
class Ixtlan::UserManagement::Locale

  # use the same table as Locale
  def self.storage_name(repo = :default)
    'locales'
  end
end

class Locale < Ixtlan::Gettext::Locale

  def self.changed_all(from = nil)
    if from
      all(:updated_at.gt => from)
    else
      all
    end
  end
end
