package com.calendar.widget.parser;

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
public final class IcalParser_Factory implements Factory<IcalParser> {
  @Override
  public IcalParser get() {
    return newInstance();
  }

  public static IcalParser_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static IcalParser newInstance() {
    return new IcalParser();
  }

  private static final class InstanceHolder {
    private static final IcalParser_Factory INSTANCE = new IcalParser_Factory();
  }
}
