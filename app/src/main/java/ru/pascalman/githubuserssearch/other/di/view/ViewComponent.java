package ru.pascalman.githubuserssearch.other.di.view;

import ru.pascalman.githubuserssearch.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ViewDynamicModule.class})
public interface ViewComponent
{

    void inject(MainActivity activity);

}
