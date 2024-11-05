/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend;

import java.util.function.Consumer;

import org.eclipse.swt.widgets.Display;

/**
 * A web browser control, which provides essential features for working with web content.
 */
public interface AbstractBrowser {

  /**
   * Loads the specified URL in the browser.
   *
   * @param url
   *          the URL to load. Must not be null or empty.
   */
  void loadUrl(String url);

  /**
   * Executes the given JavaScript code in the context of the currently loaded page.
   *
   * @param javaScript
   *          the JavaScript code to execute. Must not be null.
   * @return the result of the JavaScript execution, or null if no return value.
   */
  Object executeJavaScript(String javaScript);

  /**
   * Sets a callback to be executed when the page loading is finished.
   *
   * @param action
   *          a {@link Runnable} to execute once loading is completed. Must not be null.
   */
  void onLoadFinished(Runnable action);

  /**
   * Checks if the browser currently has focus.
   *
   * @return true if the browser has focus; false otherwise.
   */
  boolean isFocusControl();

  /**
   * Adds a JavaScript function that can be called from JavaScript code.
   *
   * @param name
   *          the name of the JavaScript function. Must not be null or empty.
   * @param once
   *          if true, the function will be removed after its first invocation.
   * @param callback
   *          the callback to execute when the function is called. Must not be null.
   */
  DisposableFunction addFunction(String name, boolean once, Consumer<Object[]> callback);

  /**
   * Returns the display associated with this browser.
   *
   * @return the {@link Display} object associated with this browser.
   */
  Display getDisplay();
}
