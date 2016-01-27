package com.contentful.cardboard.vr.actor;

import com.contentful.cardboard.vr.model.Model;

public abstract class Actor {
  protected final Model subject;

  public Actor(Model subject) {
    this.subject = subject;
  }

  /**
   * Perform what ever action this actor should do
   *
   * @param delayNanos the time between this and the last time act was called, in nanoseconds
   */
  public abstract void act(long delayNanos);

  /**
   * Return true, when this actor is not used anymore and should be removed
   */
  public abstract boolean isOver();
}
