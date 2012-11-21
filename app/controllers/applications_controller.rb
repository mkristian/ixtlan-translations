class ApplicationsController < LocalController

  guard_filter :except => [:index] do |groups|
    groups.select { |g| g.allowed_applicatiom.id.to_s == params[:id] }
  end

  public

  # GET /applications
  def index
    @applications = serializer(current_user.allowed_applications)
    @applications.use(:collection) if @applications.size > 1
    respond_with @applications
  end

  # GET /applications/1
  def show
    @application = serializer(Application.get!(params[:id]))
    respond_with @application
  end

  # GET /applications/1/translations/de
  def translations
    @application = Application.get!(params[:id])
    translations = serializer(@application.translation_all(params[:locale]))
    respond_with translations
  end

  # POST /applications/1/translation
  def create_translation
    @application = Application.get!(params[:id])
    translation = serializer(@application.translation_new(params[:translation]))
    translation.modified_by = current_user
    translation.save

    respond_with(translation)
  end

  # PUT /applications/1/translation/2/1
  def update_translation   
    @application = Application.get!(params[:id])
    translation = serializer(@application.translation_get!(params[:translation_key_id],
                                                           params[:locale_id],
                                                           updated_at))
    translation.text = (params[:translation] || {})[:text]
    translation.modified_by = current_user
    translation.save

    respond_with(translation)
  end

  # POST /applications/1/remote_permission
  def create_remote_permission
    @application = Application.get!(params[:id])
    remote_permission = serializer(@application.remote_permission_new(params[:remote_permission]))
    remote_permission.modified_by = current_user
    remote_permission.save

    respond_with(remote_permission)
  end

  # PUT /applications/1/remote_permission/2
  def update_remote_permission   
    @application = Application.get!(params[:id])
    remote_permission = serializer(@application.remote_permission_get!(params[:remote_permission_id],
                                                                       updated_at))
    remote_permission.text = (params[:remote_permission] || {})[:text]
    remote_permission.modified_by = current_user
    remote_permission.save

    respond_with(remote_permission)
  end
end
