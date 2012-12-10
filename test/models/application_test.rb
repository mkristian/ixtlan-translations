require "minitest_helper"
require 'factories'

describe Application do

  subject { Application.first }

  let(:key1) { TranslationKey.first( :application => subject )  }
  let(:key2) { TranslationKey.first( :application => Application.last ) }
  let(:key3) { TranslationKey.first_or_create( :name => 'key new', :application => subject, :updated_at => DateTime.now ) }

  let(:translation1) { Translation.first( :translation_key => key1 ) }  
  let(:translation2) { Translation.first( :translation_key => key2 ) }

  let(:remote_permission1) { RemotePermission.first }  
  let(:remote_permission2) { RemotePermission.first :offset => 1 }

  before do
    key3.state = :new
    key3.save
    TranslationKey.all(:name.like => 'extra%').destroy!
    Translation.all(:text.like => 'hello%').destroy!
  end

  after do
    key3.destroy!
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
    t = subject.remote_permission_new( { :ip => '0.0.0.0', 
                                         :token => 'be happy',
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
    key3.update( :state => :new )
    translation1.translation_key.update( :state => :ok )
    t = subject.translations_all(true)
    t.size.must_equal 1
    t.member?(translation1).must_equal true
    t.member?(translation2).must_equal false
    t = subject.translations_all(false)
    t.size.must_equal 1
    key3.update( :state => :deleted )
    t = subject.translations_all(false)
    t.size.must_equal 1
    t.member?(translation1).must_equal true
    t.member?(translation2).must_equal false
  end

end
