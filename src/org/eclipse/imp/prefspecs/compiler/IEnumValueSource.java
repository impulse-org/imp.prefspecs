package org.eclipse.imp.prefspecs.compiler;

/**
 * A tag interface for value sources for enumerated fields (combo, radio box groups).
 * Could be either a static set of key/value pairs, or the qualified name of a class
 * that will provide the key/value pairs at runtime.
 */
public interface IEnumValueSource { }