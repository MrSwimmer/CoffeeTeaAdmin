package com.mrswimmer.coffeeteaadmin.di.module;

import com.mrswimmer.coffeeteaadmin.domain.service.FilterService;
import com.mrswimmer.coffeeteaadmin.domain.service.FireService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FireModule {
    @Provides
    @Singleton
    FireService providesService() {
        return new FireService();
    }
    @Provides
    @Singleton
    FilterService provideFilter() {
        return new FilterService();
    }
}
