/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swt.BrowserView;

/**
 * Factory class for creating different types of browser instances. This class provides methods to create browsers for both JxBrowser and
 * SWT environments.
 */
public class BrowserFactory {

  /**
   * Creates an instance of JxBrowser.
   *
   * @param composite
   *          the parent {@link Composite} widget where the browser view will be embedded. This must not be null.
   * @return an instance of {@link AbstractBrowser} representing the JxBrowser.
   */
  public static AbstractBrowser createJxBrowser(final Composite composite) {
    Engine engine = Engine.newInstance(EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey("k").build());
    com.teamdev.jxbrowser.browser.Browser browser = engine.newBrowser();
    return new JxBrowser(BrowserView.newInstance(composite, browser));
  }

  /**
   * Creates an instance of SWT Browser.
   *
   * @param composite
   *          the parent {@link Composite} widget where the browser will be displayed. This must not be null.
   * @return an instance of {@link AbstractBrowser} representing the SWT Browser.
   */
  public static AbstractBrowser createSwtBrowser(final Composite composite) {
    Browser browser = new Browser(composite, SWT.EDGE);
    return new SwtBrowser(browser);
  }
}
