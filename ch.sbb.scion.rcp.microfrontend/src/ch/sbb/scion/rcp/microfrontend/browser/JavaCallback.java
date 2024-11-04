package ch.sbb.scion.rcp.microfrontend.browser;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.widgets.Display;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.js.JsFunctionCallback;
import com.teamdev.jxbrowser.js.JsObject;
import com.teamdev.jxbrowser.view.swt.BrowserView;

import ch.sbb.scion.rcp.microfrontend.IDisposable;

/**
 * Allows interaction from JavaScript with Java code. Injects a function to the {Window} of the currently loaded document that can be
 * invoked from JavaScript. Invoking that function calls the passed callback. The function's name can be obtained via
 * {@link JavaCallback#name}. Wraps a SWT {@link BrowserFunction}.
 */
public class JavaCallback implements IDisposable {

  public final String name;

  private final CompletableFuture<BrowserView> whenBrowser;
  private final Consumer<Object[]> callback;
  private Browser browser;

  public JavaCallback(final BrowserView browser, final Consumer<Object[]> callback) {
    this(CompletableFuture.completedFuture(browser), callback);
  }

  public JavaCallback(final CompletableFuture<BrowserView> whenBrowser, final Consumer<Object[]> callback) {
    this.whenBrowser = whenBrowser;
    this.name = toValidJavaScriptIdentifier("__scion_rcp_browserfunction_" + UUID.randomUUID());
    this.callback = callback;
  }

  /**
   * Installs this callback on the {Window} of the currently loaded document in the browser. Applications must dispose this function if not
   * used anymore. This method resolves to the callback when installed the callback.
   */
  public CompletableFuture<JavaCallback> install() {
    return install(false);
  }

  /**
   * Installs this callback on the {Window} of the currently loaded document in the browser. This callback is automatically uninstalled
   * after first invocvation. This method resolves to the callback when installed the callback.
   */
  public CompletableFuture<JavaCallback> installOnce() {
    return install(true);
  }

  public CompletableFuture<JavaCallback> install(final boolean once) {
    return whenBrowser.thenAccept(browserView -> {
      // Retrieve main frame's window object in JavaScript
      JsObject window = browserView.getBrowser().mainFrame().orElseThrow().executeJavaScript("window");
      this.browser = browserView.getBrowser();
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

    }).thenApply(browserView -> this);
  }

  /**
   * Adds this {@link JavaCallback} to the passed collection.
   */
  public JavaCallback addTo(final Collection<IDisposable> disposables) {
    disposables.add(this);
    return this;
  }

  /**
   * Disposes of the resources associated with this BrowserFunction. Applications must dispose of all BrowserFunctions that they create.
   * <p>
   * Note that disposing a Browser automatically disposes all BrowserFunctions associated with it.
   * </p>
   */
  @Override
  public void dispose() {
    // JsObject window = browser.mainFrame().orElseThrow().executeJavaScript("window");
    //window.removeProperty(name);
    //todo
    /* if (browserFunction != null && !browserFunction.isDisposed()) {
      browserFunction.dispose();
      browserFunction = null;
    }*/
  }

  private String toValidJavaScriptIdentifier(final String name) {
    if (Pattern.matches("^\\d.+", name)) {
      throw new IllegalArgumentException(String.format("JavaScript identifier must not start with a digit. [name=%s]", name));
    }
    return name.replaceAll("[^\\w\\d\\$]", "_");
  }
}