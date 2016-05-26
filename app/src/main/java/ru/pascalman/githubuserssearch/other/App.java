package ru.pascalman.githubuserssearch.other;

import android.app.Application;

import ru.pascalman.githubuserssearch.other.di.AppComponent;
import ru.pascalman.githubuserssearch.other.di.DaggerAppComponent;

public class App extends Application
{

    private static AppComponent component;

    public static AppComponent getComponent()
    {
        return component;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent()
    {
        return DaggerAppComponent.builder()
                .build();
    }

}
