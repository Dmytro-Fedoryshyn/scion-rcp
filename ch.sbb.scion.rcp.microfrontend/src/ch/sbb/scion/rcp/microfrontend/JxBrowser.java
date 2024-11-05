/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend;

import java.util.function.Consumer;

import org.eclipse.swt.widgets.Display;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.navigation.event.FrameLoadFinished;
import com.teamdev.jxbrowser.view.swt.BrowserView;

/**
 * TODO Klasse dokumentieren
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
  public void addFunction(final String name, final boolean once, final Consumer<Object[]> callback) {

  }

  @Override
  public Display getDisplay() {
    return browserView.getDisplay();
  }

}
