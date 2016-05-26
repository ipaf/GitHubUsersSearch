package ru.pascalman.githubuserssearch.other.di;

import ru.pascalman.githubuserssearch.model.Model;
import ru.pascalman.githubuserssearch.model.ModelImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class PresenterModule
{

    @Provides
    @Singleton
    Model provideUsers()
    {
        return new ModelImpl();
    }

    @Provides
    CompositeSubscription provideCompositeSubscription()
    {
        return new CompositeSubscription();
    }

}
