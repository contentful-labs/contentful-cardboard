package com.contentful.cardboard.vr.actor;

import com.contentful.cardboard.vr.model.Model;

public class RotateByActor extends TimedActor {

  private final float x, y, z;

  public RotateByActor(Model subject, long durationNanos, float x, float y, float z) {
    super(subject, durationNanos);
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public void act(long delayNanos) {
    if (!isOver()) {
      final float factor = looping ? 1.0f : (float) delayNanos / nanosExpected;
      subject.rotateBy(x * factor, y * factor, z * factor);
    }
    super.act(delayNanos);
  }
}
