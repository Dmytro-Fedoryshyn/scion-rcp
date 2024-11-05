/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend.browser;

/**
 * A listener for receiving notifications when a frame has finished loading in the browser.
 */
public interface FrameLoadFinishedListener {

  /**
   * Invoked when a frame has completed loading.
   */
  public void onFrameLoadFinished();
}
