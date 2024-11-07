/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend.browser;

import java.util.ArrayList;
import java.util.List;

/**
 * The base implementation of [BrowserView], which provides essential features for working with web content.
 */
public abstract class AbstractBrowserView implements BrowserView {

  private final List<NavigationListener> listeners = new ArrayList<>();

  @Override
  public void addNavigationListener(final NavigationListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeNavigationListener(final NavigationListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies that a frame has finished loading. This method should be called to trigger frame load completion events.
   */
  protected void notifyFrameLoadFinished() {
    for (NavigationListener listener : listeners) {
      listener.onFrameLoadFinished();
    }
  }
}