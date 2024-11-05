/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend;

import java.util.function.Consumer;

import org.eclipse.swt.widgets.Display;

/**
 * TODO Klasse dokumentieren
 */
public interface AbstractBrowser {

  void loadUrl(String url);

  Object executeJavaScript(String javaScript);

  void onLoadFinished(Runnable action);

  boolean isFocusControl();

  void addFunction(String name, boolean once, Consumer<Object[]> callback);

  Display getDisplay();
}
