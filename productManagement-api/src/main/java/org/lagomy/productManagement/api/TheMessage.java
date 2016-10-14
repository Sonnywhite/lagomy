package org.lagomy.productManagement.api;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Immutable
@JsonDeserialize
public final class TheMessage {

  public final String message;

  @JsonCreator
  public TheMessage(String message) {
    this.message = Preconditions.checkNotNull(message, "message");
  }

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof TheMessage && equalTo((TheMessage) another);
  }

  private boolean equalTo(TheMessage another) {
    return message.equals(another.message);
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + message.hashCode();
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("GreetingMessage").add("message", message).toString();
  }
}
