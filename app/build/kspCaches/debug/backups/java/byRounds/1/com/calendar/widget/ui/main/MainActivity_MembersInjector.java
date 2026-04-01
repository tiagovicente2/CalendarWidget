package com.calendar.widget.ui.main;

import com.calendar.widget.data.repository.EventRepository;
import com.calendar.widget.sync.SyncManager;
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
    "KotlinInternalInJava",
    "cast"
})
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<EventRepository> eventRepositoryProvider;

  private final Provider<SyncManager> syncManagerProvider;

  public MainActivity_MembersInjector(Provider<EventRepository> eventRepositoryProvider,
      Provider<SyncManager> syncManagerProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.syncManagerProvider = syncManagerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<EventRepository> eventRepositoryProvider,
      Provider<SyncManager> syncManagerProvider) {
    return new MainActivity_MembersInjector(eventRepositoryProvider, syncManagerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectEventRepository(instance, eventRepositoryProvider.get());
    injectSyncManager(instance, syncManagerProvider.get());
  }

  @InjectedFieldSignature("com.calendar.widget.ui.main.MainActivity.eventRepository")
  public static void injectEventRepository(MainActivity instance, EventRepository eventRepository) {
    instance.eventRepository = eventRepository;
  }

  @InjectedFieldSignature("com.calendar.widget.ui.main.MainActivity.syncManager")
  public static void injectSyncManager(MainActivity instance, SyncManager syncManager) {
    instance.syncManager = syncManager;
  }
}
