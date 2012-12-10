require "minitest_helper"
require 'factories'

describe Application.to_s + "<->" + TranslationKey.to_s do

  subject { Application.first }

  let(:key1) { TranslationKey.first }
  let(:key2) { TranslationKey.first( :application => Application.last ) }

  let(:translation1) { Translation.first }  
  let(:translation2) { Translation.first( :translation_key => key2 ) }

  let(:remote_permission1) { RemotePermission.first }  
  let(:remote_permission2) { RemotePermission.first :offset => 1 }

  before do
    TranslationKey.all(:name.like => 'extra%').destroy!
    Translation.all(:text.like => 'hello%').destroy!
    TranslationKey.all.each {|t| t.update( :state => :ok ) }
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
