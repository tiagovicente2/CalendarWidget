package com.calendar.widget.sync;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class GoogleCalendarSync_Factory implements Factory<GoogleCalendarSync> {
  private final Provider<Context> contextProvider;

  public GoogleCalendarSync_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public GoogleCalendarSync get() {
    return newInstance(contextProvider.get());
  }

  public static GoogleCalendarSync_Factory create(Provider<Context> contextProvider) {
    return new GoogleCalendarSync_Factory(contextProvider);
  }

  public static GoogleCalendarSync newInstance(Context context) {
    return new GoogleCalendarSync(context);
  }
}
