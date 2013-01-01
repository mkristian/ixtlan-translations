require "minitest_helper"
require 'factories'

describe Application do

  subject { Application.first }

  let(:key1) { TranslationKey.first( :application => subject )  }
  let(:key2) { TranslationKey.first( :application => Application.last ) }
  let(:key3) { TranslationKey.first_or_create( :name => 'key new', :application => subject ) }

  let(:translation1) { Translation.first( :translation_key => key1 ) }  
  let(:translation2) { Translation.first( :translation_key => key2 ) }

  let(:remote_permission1) { RemotePermission.first }  
  let(:remote_permission2) { RemotePermission.first :offset => 1 }

  before do
    if key3.new?
      key3.state = :new
      key3.updated_at = DateTime.now
      key3.save
    end
    TranslationKey.all(:name.not => [key1.name, key2.name, key3.name]).destroy!
    Translation.all(:text => 'hello').destroy!
  end

  it 'must exist' do
    subject.wont_be_nil
  end

  it 'must retrieve remote_permission from same application' do
    t = subject.remote_permission_get!(remote_permission1.id,
                                       remote_permission1.updated_at)
    t.must_equal remote_permission1
  end

  it 'wont retrieve remote_permission from other application' do
    lambda { subject.remote_permission_get!(remote_permission2.id,
                                            remote_permission2.updated_at) }.must_raise DataMapper::ObjectNotFoundError
  end

  it 'must create new remote_permission for key of same application' do
    t = subject.remote_permission_new( { :allowed_ip => '0.0.0.0', 
                                         :authentication_token => 'be happy',
                                         :modified_by => User.first } )
    t.save.must_equal true
    t.application.must_equal subject
  end

  it 'must create new translation for key of same application' do
    t = subject.translation_update!( key1,
                                     Locale.last,
                                     nil,
                                     nil,
                                     'hello',
                                     User.first )
    t.save.must_equal true
    t.locale.must_equal Locale.last
    t.translation_key.application.must_equal subject
  end

  it 'wont create new translation for key of other application' do
    lambda {subject.translation_update!( key2,
                                         Locale.first,
                                         nil,
                                         nil,
                                         'hello',
                                         User.first ) }.must_raise DataMapper::ObjectNotFoundError
  end

  it 'must retrieve all translations from application' do
    TranslationKey.all(:name.like => "key_").update(:state => :ok)
    TranslationKey.all(:name => "key new").update(:state => :new)

    t = subject.translations_all(true)

    t.size.must_equal 1
    t.member?(translation1).must_equal true
    t.member?(translation2).must_equal false

    t = subject.translations_all(false)

    t.size.must_equal 1

    TranslationKey.all(:name => "key new").update(:state => :deleted)
    t = subject.translations_all(false)

    t.size.must_equal 1
    t.member?(translation1).must_equal true
    t.member?(translation2).must_equal false
  end

  it 'must retrieve all translation_keys from application' do
    TranslationKey.all(:name.like => "key_").update(:state => :ok)
    TranslationKey.all(:name => "key new").update(:state => :new)

    t = subject.translation_keys_all(true)

    t.size.must_equal 1
    t.member?(key1).must_equal true
    t.member?(key2).must_equal false
    t.member?(key3).must_equal false

    t = subject.translation_keys_all(false)

    t.size.must_equal 2

    TranslationKey.all(:name => "key new").update(:state => :deleted)

    t = subject.translation_keys_all(false)
    t.size.must_equal 1
    t.member?(key1).must_equal true
    t.member?(key2).must_equal false
    t.member?(key3).must_equal false
  end
end
