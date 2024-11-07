/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend.browser;

/**
 * A browser function which should be disposed of to free up resources when no longer needed.
 */
public interface DisposableFunction {

  /**
   * Disposes of the function or resource, releasing any resources allocated to it. This method should be called when the function is no
   * longer needed to ensure efficient resource management.
   */
  void dispose();
}
