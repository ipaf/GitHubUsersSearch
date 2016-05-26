package ru.pascalman.githubuserssearch.other.di;

import ru.pascalman.githubuserssearch.model.api.ApiInterface;
import ru.pascalman.githubuserssearch.model.api.ApiModule;
import ru.pascalman.githubuserssearch.other.Const;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ModelModule
{

    @Provides
    @Singleton
    ApiInterface provideApiInterface()
    {
        return ApiModule.getApiInterface(Const.BASE_URL);
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideSchedulerUI()
    {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideSchedulerIO()
    {
        return Schedulers.io();
    }

}
