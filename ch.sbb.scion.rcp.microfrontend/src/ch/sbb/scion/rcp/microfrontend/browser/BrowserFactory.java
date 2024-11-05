/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend.browser;

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
public final class BrowserFactory {

  public static AbstractBrowser createBrowser(final BrowserType type, final Composite composite) {
    if (type == BrowserType.JXBROWSER) {
      Engine engine = Engine.newInstance(EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey("k").build());
      com.teamdev.jxbrowser.browser.Browser browser = engine.newBrowser();
      return new JxBrowser(BrowserView.newInstance(composite, browser));
    }

    if (type == BrowserType.SWT) {
      Browser browser = new Browser(composite, SWT.EDGE);
      return new SwtBrowser(browser);
    }

    throw new IllegalArgumentException("Unsupported browser type " + type + ". ");
  }

  private BrowserFactory() {
  }
}
