package com.calendar.widget.ui.settings;

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
public final class SettingsActivity_MembersInjector implements MembersInjector<SettingsActivity> {
  private final Provider<SyncManager> syncManagerProvider;

  public SettingsActivity_MembersInjector(Provider<SyncManager> syncManagerProvider) {
    this.syncManagerProvider = syncManagerProvider;
  }

  public static MembersInjector<SettingsActivity> create(
      Provider<SyncManager> syncManagerProvider) {
    return new SettingsActivity_MembersInjector(syncManagerProvider);
  }

  @Override
  public void injectMembers(SettingsActivity instance) {
    injectSyncManager(instance, syncManagerProvider.get());
  }

  @InjectedFieldSignature("com.calendar.widget.ui.settings.SettingsActivity.syncManager")
  public static void injectSyncManager(SettingsActivity instance, SyncManager syncManager) {
    instance.syncManager = syncManager;
  }
}
