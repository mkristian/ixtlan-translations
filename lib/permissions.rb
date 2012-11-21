class Permissions

  def self.for(groups)
    Rails.configuration.guard.permissions( groups ) do |r,a|
      if r == 'applications'
        l = a.collect { |g| g.locales }
        l.flatten!
        l.uniq!;
        l = [Locale.new(:code => '')] if l.empty?
        d = a.collect { |g| g.domains }
        d.flatten!
        d.uniq!;
        d = [Domain.new(:name => '')] if d.empty?
        # calculate crossproduct of locales X domains
        r = d.collect do |dd| 
          l.collect { |ll| "#{ll.code}|#{dd.name}" }
        end
        r.flatten!
        r
      end
    end
  end
end
