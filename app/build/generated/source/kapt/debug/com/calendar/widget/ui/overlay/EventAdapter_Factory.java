package com.calendar.widget.ui.overlay;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class EventAdapter_Factory implements Factory<EventAdapter> {
  @Override
  public EventAdapter get() {
    return newInstance();
  }

  public static EventAdapter_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static EventAdapter newInstance() {
    return new EventAdapter();
  }

  private static final class InstanceHolder {
    private static final EventAdapter_Factory INSTANCE = new EventAdapter_Factory();
  }
}
