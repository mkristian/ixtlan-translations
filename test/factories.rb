# -*- coding: utf-8 -*-
FactoryGirl.define do
  sequence(:number)

  factory :application do |f|
    i = Application.max(:id).to_i + 1
    f.name "name#{i}"
    f.url "http://example.com/#{i}"
    f.updated_at DateTime.now
  end

  factory :remote_permission do |f|
    i = RemotePermission.max(:id).to_i
    f.ip "#{i}.#{i}.#{i}.#{i}"
    f.token 'be happy'
    f.application { FactoryGirl.create(:application) }
    f.modified_by { User.first }
  end

  factory :translation_key do |f|
    ii = TranslationKey.max(:id).to_i + 1
    f.name "key#{ii}"
    f.state :ok
    f.application { FactoryGirl.create(:application) }
  end

  factory :translation do |f|
    f.locale          { Locale.get(1) }
    f.text            { |x| x.locale.code == 'en' ? 'Key' : "Schlüssel" }
    f.modified_by     { User.first }
    f.translation_key { Factory :translation_key }
  end
end
