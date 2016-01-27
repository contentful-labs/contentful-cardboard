package com.contentful.cardboard.vr.actor;

import com.contentful.cardboard.vr.model.Model;

import java.util.ArrayList;
import java.util.List;

public class ParallelActor extends Actor {

  private final List<Actor> toBeRemoved;
  private final List<Actor> actors;

  public ParallelActor() {
    super(null);
    actors = new ArrayList<>();
    toBeRemoved = new ArrayList<>();
  }

  @Override
  public void act(long delayNanos) {
    for (final Actor actor : actors) {
      actor.act(delayNanos);

      if (actor.isOver()) {
        toBeRemoved.add(actor);
      }
    }

    if (actors.removeAll(toBeRemoved)) {
      toBeRemoved.clear();
    }
  }

  @Override
  public boolean isOver() {
    return actors.size() == 0;
  }

  public boolean add(Actor actor) {
    return actors.add(actor);
  }

  public int size() {
    return actors.size();
  }

  public boolean actsOnModel(Model model) {
    for (Actor actor : actors) {
      if (actor instanceof ParallelActor) {
        if (((ParallelActor) actor).actsOnModel(model)) {
          return true;
        }
      }

      if (actor.subject == model) {
        return true;
      }
    }
    return false;
  }
}
