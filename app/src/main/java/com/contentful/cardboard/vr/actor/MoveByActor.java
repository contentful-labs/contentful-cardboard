package com.contentful.cardboard.vr.actor;

import com.contentful.cardboard.vr.model.Model;

public class MoveByActor extends TimedActor {

  private final float x, y, z;

  public MoveByActor(Model subject, long durationNanos, float x, float y, float z) {
    super(subject, durationNanos);
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public void act(long delayNanos) {
    if (!isOver()) {
      final double factor = looping ? 1.0f : (double) delayNanos / nanosExpected;
      subject.moveBy((float) (x * factor), (float) (y * factor), (float) (z * factor));
    }

    super.act(delayNanos);
  }
}
