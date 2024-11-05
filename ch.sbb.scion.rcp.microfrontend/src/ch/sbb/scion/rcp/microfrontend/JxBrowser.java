/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend;

import java.util.function.Consumer;

import org.eclipse.swt.widgets.Display;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.js.JsFunctionCallback;
import com.teamdev.jxbrowser.js.JsObject;
import com.teamdev.jxbrowser.navigation.event.FrameLoadFinished;
import com.teamdev.jxbrowser.view.swt.BrowserView;

/**
 * JxBrowser implementation of the {@link AbstractBrowser} interface.
 */
public class JxBrowser implements AbstractBrowser {

  private final BrowserView browserView;
  private final Browser browser;

  public JxBrowser(final BrowserView browserView) {
    this.browserView = browserView;
    this.browser = browserView.getBrowser();
  }

  @Override
  public void loadUrl(final String url) {
    browser.navigation().loadUrlAndWait(url);

  }

  @Override
  public Object executeJavaScript(final String javaScript) {
    return browser.mainFrame().orElseThrow().executeJavaScript(javaScript);
  }

  @Override
  public void onLoadFinished(final Runnable action) {
    browser.navigation().on(FrameLoadFinished.class, e -> {
      if (e.frame().isMain()) {
        action.run();
      }
    });
  }

  @Override
  public boolean isFocusControl() {
    return browserView.isFocusControl();
  }

  @Override
  public DisposableFunction addFunction(final String name, final boolean once, final Consumer<Object[]> callback) {
    JsObject window = browserView.getBrowser().mainFrame().orElseThrow().executeJavaScript("window");
    // Define the JsFunctionCallback to handle invocations from JavaScript
    JsFunctionCallback c = new JsFunctionCallback() {

      @Override
      public Object invoke(final Object... args) {
        if (once) {
          // Remove the callback from the JavaScript context after one use
          window.removeProperty(name);
        }

        // Execute the callback in SWT's display thread
        Display display = browserView.getDisplay();
        display.asyncExec(() -> {
          callback.accept(args);
        });

        return Boolean.TRUE;
      }
    };

    // Bind the JavaScript function to the `window` object
    window.putProperty(name, c);

    return new DisposableFunction() {

      @Override
      public void dispose() {
        window.removeProperty(name);
      }
    };
  }

  @Override
  public Display getDisplay() {
    return browserView.getDisplay();
  }

}
