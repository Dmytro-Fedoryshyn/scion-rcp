/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend;

import java.util.function.Consumer;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.widgets.Display;

/**
 * SWT Browser implementation of the {@link AbstractBrowser} interface.
 */
public class SwtBrowser implements AbstractBrowser {

  private final Browser browser;

  public SwtBrowser(final Browser browser) {
    this.browser = browser;
  }

  @Override
  public void loadUrl(final String url) {
    browser.setUrl(url);
  }

  @Override
  public Object executeJavaScript(final String javaScript) {
    return browser.execute(javaScript);

  }

  @Override
  public void onLoadFinished(final Runnable action) {
    browser.addProgressListener(new ProgressAdapter() {

      @Override
      public void completed(final ProgressEvent event) {
        action.run();
      }
    });
  }

  @Override
  public boolean isFocusControl() {
    return browser.isFocusControl();
  }

  @Override
  public void addFunction(final String name, final boolean once, final Consumer<Object[]> callback) {
    BrowserFunction browserFunction = new BrowserFunction(browser, name) {

      @Override
      public Boolean function(final Object[] arguments) {
        if (once) {
          dispose();
        }
        // Invoke the callback asynchronously to first complete the invocation of this browser function.
        // Otherwise, creating a new {@link Browser} instance in the callback would lead to a deadlock.
        browser.getDisplay().asyncExec(() -> callback.accept(arguments));
        return Boolean.TRUE;
      }
    };
  }

  @Override
  public Display getDisplay() {
    return browser.getDisplay();
  };
}
