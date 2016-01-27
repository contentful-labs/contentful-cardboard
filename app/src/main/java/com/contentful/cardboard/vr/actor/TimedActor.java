package com.contentful.cardboard.vr.actor;

import com.contentful.cardboard.vr.model.Model;

public abstract class TimedActor extends Actor {

  protected long nanosExpected;
  protected long nanosElapsed;
  protected boolean looping;

  public TimedActor(Model subject, long durationNanos) {
    super(subject);
    this.nanosExpected = durationNanos;
    this.looping = durationNanos < 0;
  }

  @Override public void act(long delayNanos) {
    nanosElapsed += delayNanos;
  }

  @Override public boolean isOver() {
    return !looping && nanosExpected < nanosElapsed;
  }
}
