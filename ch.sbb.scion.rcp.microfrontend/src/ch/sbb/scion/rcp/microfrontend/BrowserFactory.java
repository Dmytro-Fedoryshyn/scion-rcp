/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

/**
 * TODO Klasse dokumentieren
 */
public class BrowserFactory {

  public static AbstractBrowser createJxBrowser(final Composite composite) {
    return null;
  }

  public static AbstractBrowser createSwtBrowser(final Composite composite) {
    Browser browser = new Browser(composite, SWT.EDGE);
    return new SwtBrowser(browser);
  }
}
