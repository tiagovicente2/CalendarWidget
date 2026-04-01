package com.calendar.widget.di;

import com.calendar.widget.data.remote.api.IcalService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideIcalServiceFactory implements Factory<IcalService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideIcalServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public IcalService get() {
    return provideIcalService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideIcalServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideIcalServiceFactory(retrofitProvider);
  }

  public static IcalService provideIcalService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideIcalService(retrofit));
  }
}
