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
public class JxBrowser implements AbstractBrowser {

  @Override
  public void loadUrl(final String url) {
    // TODO Auto-generated method stub

  }

  @Override
  public Object executeJavaScript(final String javaScript) {
    return null;

  }

  @Override
  public void onLoadFinished(final Runnable action) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isFocusControl() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void addFunction(final VarArgFunction function, final String name, final boolean once, final Consumer<Object[]> callback) {
    // TODO Auto-generated method stub

  }

  @Override
  public Display getDisplay() {
    // TODO Auto-generated method stub
    return null;
  }

}
