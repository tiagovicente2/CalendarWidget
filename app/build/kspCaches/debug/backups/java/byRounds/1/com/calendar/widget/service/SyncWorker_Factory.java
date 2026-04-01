package com.calendar.widget.service;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.calendar.widget.sync.SyncManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class SyncWorker_Factory {
  private final Provider<SyncManager> syncManagerProvider;

  public SyncWorker_Factory(Provider<SyncManager> syncManagerProvider) {
    this.syncManagerProvider = syncManagerProvider;
  }

  public SyncWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, syncManagerProvider.get());
  }

  public static SyncWorker_Factory create(Provider<SyncManager> syncManagerProvider) {
    return new SyncWorker_Factory(syncManagerProvider);
  }

  public static SyncWorker newInstance(Context context, WorkerParameters params,
      SyncManager syncManager) {
    return new SyncWorker(context, params, syncManager);
  }
}
