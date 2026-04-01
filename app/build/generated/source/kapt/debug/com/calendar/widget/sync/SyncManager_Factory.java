package com.calendar.widget.sync;

import android.content.Context;
import android.content.SharedPreferences;
import com.calendar.widget.data.repository.EventRepository;
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
public final class SyncManager_Factory implements Factory<SyncManager> {
  private final Provider<Context> contextProvider;

  private final Provider<EventRepository> eventRepositoryProvider;

  private final Provider<GoogleCalendarSync> googleCalendarSyncProvider;

  private final Provider<IcalSync> icalSyncProvider;

  private final Provider<SharedPreferences> sharedPreferencesProvider;

  public SyncManager_Factory(Provider<Context> contextProvider,
      Provider<EventRepository> eventRepositoryProvider,
      Provider<GoogleCalendarSync> googleCalendarSyncProvider, Provider<IcalSync> icalSyncProvider,
      Provider<SharedPreferences> sharedPreferencesProvider) {
    this.contextProvider = contextProvider;
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.googleCalendarSyncProvider = googleCalendarSyncProvider;
    this.icalSyncProvider = icalSyncProvider;
    this.sharedPreferencesProvider = sharedPreferencesProvider;
  }

  @Override
  public SyncManager get() {
    return newInstance(contextProvider.get(), eventRepositoryProvider.get(), googleCalendarSyncProvider.get(), icalSyncProvider.get(), sharedPreferencesProvider.get());
  }

  public static SyncManager_Factory create(Provider<Context> contextProvider,
      Provider<EventRepository> eventRepositoryProvider,
      Provider<GoogleCalendarSync> googleCalendarSyncProvider, Provider<IcalSync> icalSyncProvider,
      Provider<SharedPreferences> sharedPreferencesProvider) {
    return new SyncManager_Factory(contextProvider, eventRepositoryProvider, googleCalendarSyncProvider, icalSyncProvider, sharedPreferencesProvider);
  }

  public static SyncManager newInstance(Context context, EventRepository eventRepository,
      GoogleCalendarSync googleCalendarSync, IcalSync icalSync,
      SharedPreferences sharedPreferences) {
    return new SyncManager(context, eventRepository, googleCalendarSync, icalSync, sharedPreferences);
  }
}
