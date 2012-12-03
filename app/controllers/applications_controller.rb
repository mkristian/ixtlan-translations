class ApplicationsController < LocalController

  guard_filter :select_groups, :except => [:index]

  private
  
  def select_groups(groups)
    groups.select { |g| g.application == application }
  end

  def application
    @application ||= Application.get!(params[:id])
  end

  public

  # GET /applications
  def index
    @applications = serializer(current_user.allowed_applications)
    respond_with @applications
  end

  # GET /applications/1
  def show
    respond_with serializer(application)
  end

  def translations
    params = self.params[:translation] || {}
    key = TranslationKey.get!(params[:translation_key_id])
    locale = Locale.get!(params[:locale_id])
    domain = Domain.get(params[:domain_id])

    translation = application.translation_update!(key,
                                                  locale,
                                                  domain,
                                                  params[:updated_at],
                                                  params[:text],
                                                  current_user)

    respond_with serializer( translation )
  end

  # POST /applications/1/remote_permission
  def create_remote_permission
    remote_permission = application.remote_permission_new(params[:remote_permission])
    remote_permission.modified_by = current_user
    remote_permission.save

    respond_with(serializer(remote_permission))
  end

  # PUT /applications/1/remote_permission/2
  def update_remote_permission   
    remote_permission = application.remote_permission_get!(params[:remote_permission_id],
                                                           updated_at)
    remote_permission.attributes = params[:remote_permission] || {}
    remote_permission.modified_by = current_user
    remote_permission.save

    respond_with(serializer(remote_permission))
  end
end
