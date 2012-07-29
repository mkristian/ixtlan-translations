require "minitest_helper"
require 'factories'

describe Application do

  subject { Application.first }

  let(:key1) { TranslationKey.first }
  let(:key2) { TranslationKey.first :offset => 1 }

  let(:translation1) { Translation.first }  
  let(:translation2) { Translation.first :offset => 1 }

  before do
    TranslationKey.all(:name.like => 'extra%').destroy!
  end

  it 'must exist' do
    subject.wont_be_nil
  end

  it 'must retrieve translation from same application' do
    t = subject.translation_get!(translation1.id, translation1.updated_at)
    t.must_equal translation1
  end

  it 'wont retrieve translation from other applcation' do
    lambda { subject.translation_get!(translation2.id, translation2.updated_at) }.must_raise DataMapper::ObjectNotFoundError
  end

  it 'must create new translation for key of same application' do
    t = subject.translation_new( { :text => 'hello',
                                   :locale => { :id => Locale.first.id },
                                   :translation_key => { :id => key1.id },
                                   :modified_by => User.first } )
    t.save.must_equal true
    t.locale.must_equal Locale.first
    t.translation_key.application.must_equal subject
  end

  it 'wont create new translation for key of other application' do
    lambda {subject.translation_new( { :text => 'hello',
                                       :locale => { :id => Locale.first.id },
                                       :translation_key => { :id => key2.id },
                                       :modified_by => User.first } ) }.must_raise DataMapper::ObjectNotFoundError
  end

  it 'must retrieve all translations from application' do
    t = subject.translations_all(translation1.locale.code)
    t.member?(translation1).must_equal true
    t.member?(translation2).must_equal false
    t = subject.translations_all(Locale.last)
    t.size.must_equal 0
  end

  it 'should update the keys - new ones only' do
    all = subject.translation_keys.collect {|n| n.name }
    diff = ['hello world', 'some new one']
    new_set = all | diff
    subject.update_keys(new_set)
    subject.translation_keys.collect {|n| n.name }.sort.must_equal new_set.sort
    subject.translation_keys.select {|n| n.name.in? all }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? diff }.collect {|n| n.state.must_equal :new }

    diff = ['some new one']
    new_set = all | diff
    subject.update_keys(new_set)
    subject.translation_keys.collect {|n| n.name }.sort.must_equal new_set.sort
    subject.translation_keys.select {|n| n.name.in? all }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? diff }.collect {|n| n.state.must_equal :new }

    new_set = all
    subject.update_keys(new_set)
    subject.translation_keys.collect {|n| n.name }.sort.must_equal new_set.sort
    subject.translation_keys.collect {|n| n.state.must_equal :ok }
  end

  it 'should update the keys' do
    all = subject.translation_keys.collect {|n| n.name }
    t = subject.translation_keys.create(:name => 'extra', :state => :ok)
    extra = [t.name]

    diff = ['hello world', 'some new one']
    new_set = all | diff
    subject.update_keys(new_set)
    subject.translation_keys.collect {|n| n.name }.sort.must_equal((extra | new_set).sort)
    subject.translation_keys.select {|n| n.name.in? all }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? diff }.collect {|n| n.state.must_equal :new }
    subject.translation_keys.select {|n| n.name.in? extra }.collect {|n| n.state.must_equal :hidden }

    diff = ['some new one']
    new_set = all | diff
    subject.update_keys(new_set)
#p    subject.translation_keys.collect {|n| "#{n.name} - #{n.state}" }
    subject.translation_keys.collect {|n| n.name }.sort.must_equal((extra | new_set).sort)
    subject.translation_keys.select {|n| n.name.in? all }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? diff }.collect {|n| n.state.must_equal :new }
    subject.translation_keys.select {|n| n.name.in? extra }.collect {|n| n.state.must_equal :hidden }

    new_set = all
    subject.update_keys(new_set)
    subject.translation_keys.collect {|n| n.name }.sort.must_equal((extra | new_set).sort)
    subject.translation_keys.select {|n| !n.name.in? extra }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? extra }.collect {|n| n.state.must_equal :hidden }

    diff = extra
    new_set = all + diff
    subject.update_keys(new_set)
    subject.translation_keys.collect {|n| n.name }.sort.must_equal((extra | new_set).sort)
    subject.translation_keys.collect {|n| n.state.must_equal :ok }
  end

  it 'should update the keys - with deleted' do
    all = subject.translation_keys.collect {|n| n.name }
    t = subject.translation_keys.create(:name => 'extra', :state => :deleted)
    extra = [t.name]

    diff = ['hello world', 'some new one']
    new_set = all | diff
    subject.update_keys(new_set)
    subject.translation_keys.collect {|n| n.name }.sort.must_equal((extra | new_set).sort)
    subject.translation_keys.select {|n| n.name.in? all }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? diff }.collect {|n| n.state.must_equal :new }
    subject.translation_keys.select {|n| n.name.in? extra }.collect {|n| n.state.must_equal :deleted }

    diff = extra
    new_set = all + diff
    subject.update_keys(new_set)
    subject.translation_keys.collect {|n| n.name }.sort.must_equal((extra | new_set).sort)
    subject.translation_keys.select {|n| !n.name.in? extra }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? extra }.collect {|n| n.state.must_equal :restored }

    new_set = all
    subject.update_keys(new_set)
    subject.translation_keys.collect {|n| n.name }.sort.must_equal((extra | new_set).sort)
    subject.translation_keys.select {|n| !n.name.in? extra }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? extra }.collect {|n| n.state.must_equal :deleted }
  end

  it 'should commit the last update' do
    all = subject.translation_keys.collect {|n| n.name }
    t1 = subject.translation_keys.create(:name => 'extra1', :state => :hidden)
    t2 = subject.translation_keys.create(:name => 'extra2', :state => :new)
    t3 = subject.translation_keys.create(:name => 'extra3', :state => :restored)
    extras = [t1.name, t2.name, t3.name]
    deleted = [t1.name]

    subject.commit_keys
    subject.translation_keys.collect {|n| n.name }.sort.must_equal((all | extras ).sort)
    subject.translation_keys.select {|n| !n.name.in? deleted }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? deleted }.collect {|n| n.state.must_equal :deleted }
  end

  it 'should rollback the last update' do
    all = subject.translation_keys.collect {|n| n.name }
    t1 = subject.translation_keys.create(:name => 'extra1', :state => :hidden)
    t2 = subject.translation_keys.create(:name => 'extra2', :state => :new)
    t3 = subject.translation_keys.create(:name => 'extra3', :state => :restored)
    deleted = [t3.name]
    extras = [t1.name, t3.name]
    
    subject.rollback_keys
    subject.translation_keys.collect {|n| n.name }.sort.must_equal((all | extras ).sort)
    subject.translation_keys.select {|n| !n.name.in? deleted }.collect {|n| n.state.must_equal :ok }
    subject.translation_keys.select {|n| n.name.in? deleted }.collect {|n| n.state.must_equal :deleted }
  end
end
