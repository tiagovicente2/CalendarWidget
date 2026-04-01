package com.calendar.widget.service;

import com.calendar.widget.data.repository.EventRepository;
import com.calendar.widget.sync.SyncManager;
import com.calendar.widget.ui.overlay.EventAdapter;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class FloatingCalendarService_MembersInjector implements MembersInjector<FloatingCalendarService> {
  private final Provider<EventAdapter> eventAdapterProvider;

  private final Provider<EventRepository> eventRepositoryProvider;

  private final Provider<SyncManager> syncManagerProvider;

  public FloatingCalendarService_MembersInjector(Provider<EventAdapter> eventAdapterProvider,
      Provider<EventRepository> eventRepositoryProvider,
      Provider<SyncManager> syncManagerProvider) {
    this.eventAdapterProvider = eventAdapterProvider;
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.syncManagerProvider = syncManagerProvider;
  }

  public static MembersInjector<FloatingCalendarService> create(
      Provider<EventAdapter> eventAdapterProvider,
      Provider<EventRepository> eventRepositoryProvider,
      Provider<SyncManager> syncManagerProvider) {
    return new FloatingCalendarService_MembersInjector(eventAdapterProvider, eventRepositoryProvider, syncManagerProvider);
  }

  @Override
  public void injectMembers(FloatingCalendarService instance) {
    injectEventAdapter(instance, eventAdapterProvider.get());
    injectEventRepository(instance, eventRepositoryProvider.get());
    injectSyncManager(instance, syncManagerProvider.get());
  }

  @InjectedFieldSignature("com.calendar.widget.service.FloatingCalendarService.eventAdapter")
  public static void injectEventAdapter(FloatingCalendarService instance,
      EventAdapter eventAdapter) {
    instance.eventAdapter = eventAdapter;
  }

  @InjectedFieldSignature("com.calendar.widget.service.FloatingCalendarService.eventRepository")
  public static void injectEventRepository(FloatingCalendarService instance,
      EventRepository eventRepository) {
    instance.eventRepository = eventRepository;
  }

  @InjectedFieldSignature("com.calendar.widget.service.FloatingCalendarService.syncManager")
  public static void injectSyncManager(FloatingCalendarService instance, SyncManager syncManager) {
    instance.syncManager = syncManager;
  }
}
