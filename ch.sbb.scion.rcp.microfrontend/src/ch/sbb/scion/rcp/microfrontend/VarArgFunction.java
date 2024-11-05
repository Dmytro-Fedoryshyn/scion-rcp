/*
 * Project: RCS - Rail Control System
 *
 * © Copyright by SBB AG, Alle Rechte vorbehalten
 */
package ch.sbb.scion.rcp.microfrontend;

/**
 * TODO Klasse dokumentieren
 */
@FunctionalInterface
public interface VarArgFunction {

  Object apply(Object... o);
}