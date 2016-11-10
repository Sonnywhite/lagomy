package org.lagomy.rating.api;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Immutable
@JsonDeserialize
public final class Rating {

  public final int newRating;

  @JsonCreator
  public Rating(int newRating) {
    this.newRating = Preconditions.checkNotNull(newRating, "newRating");
  }

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof Rating && equalTo((Rating) another);
  }

  private boolean equalTo(Rating another) {
    return newRating == another.newRating;
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + newRating;
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("Rating").add("newRating", newRating).toString();
  }
}
