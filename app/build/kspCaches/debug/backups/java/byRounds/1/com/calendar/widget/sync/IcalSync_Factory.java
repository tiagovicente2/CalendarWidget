package com.calendar.widget.sync;

import com.calendar.widget.data.remote.api.IcalService;
import com.calendar.widget.parser.IcalParser;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "KotlinInternalInJava",
    "cast"
})
public final class IcalSync_Factory implements Factory<IcalSync> {
  private final Provider<IcalService> icalServiceProvider;

  private final Provider<IcalParser> icalParserProvider;

  public IcalSync_Factory(Provider<IcalService> icalServiceProvider,
      Provider<IcalParser> icalParserProvider) {
    this.icalServiceProvider = icalServiceProvider;
    this.icalParserProvider = icalParserProvider;
  }

  @Override
  public IcalSync get() {
    return newInstance(icalServiceProvider.get(), icalParserProvider.get());
  }

  public static IcalSync_Factory create(Provider<IcalService> icalServiceProvider,
      Provider<IcalParser> icalParserProvider) {
    return new IcalSync_Factory(icalServiceProvider, icalParserProvider);
  }

  public static IcalSync newInstance(IcalService icalService, IcalParser icalParser) {
    return new IcalSync(icalService, icalParser);
  }
}
