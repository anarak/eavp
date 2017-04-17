package org.eclipse.eavp.viz.vaadin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class WebEAVPUI extends UI {
	/**
	 * A serialization ID - if you remove this, OSGI DS will fail!
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The HttpService Reference for the OSGI framework.
	 */
	static private HttpService httpService;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "WebEAVPUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WebEAVPUI.class, productionMode = false)
    public static class WebEAVPUIServlet extends VaadinServlet {
    }
    
    /**
	 * OSGi bundle activator with annotation instead of activator class.
	 * 
	 * @param context
	 */
	@Activate
	public void start(BundleContext context) {
		System.out.println("App Store VAADIN bundle started.");
		try {
			WebEAVPUI.httpService.registerServlet("/", new WebEAVPUIServlet(), null, null);
		} catch (ServletException | NamespaceException e) {
			e.printStackTrace();
		}
	}
    
    
	/**
	 * 
	 * @param httpService
	 */
	public void setService(HttpService httpService) {
		WebEAVPUI.httpService = httpService;
	}
}
