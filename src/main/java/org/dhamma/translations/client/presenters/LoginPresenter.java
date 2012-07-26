package org.dhamma.translations.client.presenters;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.translations.client.TranslationsApplication;
import org.dhamma.translations.client.models.User;
import org.dhamma.translations.client.restservices.SessionRestService;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.session.Authentication;
import de.mkristian.gwt.rails.session.LoginView;
import de.mkristian.gwt.rails.session.Session;
import de.mkristian.gwt.rails.session.SessionHandler;
import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class LoginPresenter implements LoginView.Presenter{

    private final SessionRestService service;
    private final SessionManager<User> sessionManager;
    private final Notice notice;

    @Inject
    public LoginPresenter(final SessionRestService service,
            final SessionManager<User> sessionManager,
            final Notice notice) {
        this.service = service;
        this.sessionManager = sessionManager;
        this.notice = notice;
    }

    public void init(final TranslationsApplication app){
        sessionManager.addSessionHandler(new SessionHandler<User>() {
            
            @Override
            public void timeout() {
                notice.info("timeout");
                logout();
            }
                
            @Override
            public void logout() {
                app.stopSession();
                service.destroy(new MethodCallback<Void>() {
                    public void onSuccess(Method method, Void response) {
                    }
                    public void onFailure(Method method, Throwable exception) {
                    }
                });
            }
                
            @Override
            public void login(User user) {
                app.startSession(user);
            }
            
            @Override
            public void accessDenied() {
                notice.error("access denied");
            }
        });        
    }

    public void login(final String login, final String password) {
        Authentication authentication = new Authentication(login, password);
        service.create(authentication, new MethodCallback<Session<User>>() {

            public void onSuccess(Method method, Session<User> session) {
                GWT.log("logged in: " + login);
                sessionManager.login(session);
            }

            public void onFailure(Method method, Throwable exception) {
                GWT.log("login failed: " + exception.getMessage(), exception);
                sessionManager.accessDenied();
            }
        });
    }

    public void resetPassword(final String login) {
        Authentication authentication = new Authentication(login);
        service.resetPassword(authentication, new MethodCallback<Void>() {

            public void onSuccess(Method method, Void result) {
                notice.info("new password was sent to your email address");
            }

            public void onFailure(Method method, Throwable exception) {
                notice.error("could not reset password - username/email unknown");
            }
        });
    }
}