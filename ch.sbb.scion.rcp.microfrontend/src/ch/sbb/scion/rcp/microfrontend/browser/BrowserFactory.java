/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend.browser;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swt.BrowserView;

/**
 * Factory class for creating different types of browser instances. This class provides methods to create browsers for both JxBrowser and
 * SWT environments.
 */
public final class BrowserFactory {

  /**
   * Creates a new browser of the specified type within the provided composite.
   *
   * @param type
   *          the type of the browser to create.
   * @param composite
   *          the parent composite in which the browser will be embedded. Must not be null.
   * @return a new {@code Browser} instance created with the specified type and embedded in the given composite.
   */
  public static Browser createBrowser(final BrowserType type, final Composite composite) {
    if (type == BrowserType.JXBROWSER) {
      Engine engine = Engine.newInstance(EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey("k").build());
      var browser = engine.newBrowser();
      return new JxBrowser(BrowserView.newInstance(composite, browser));
    }

    if (type == BrowserType.SWT) {
      var browser = new org.eclipse.swt.browser.Browser(composite, SWT.EDGE);
      return new SwtBrowser(browser);
    }

    throw new IllegalArgumentException("Unsupported browser type " + type + ". ");
  }

  private BrowserFactory() {
  }
}
