package ru.pascalman.githubuserssearch.other.di;

import ru.pascalman.githubuserssearch.model.ModelImpl;
import ru.pascalman.githubuserssearch.presenter.BasePresenter;
import ru.pascalman.githubuserssearch.presenter.UsersListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class})
public interface AppComponent
{

    void inject(ModelImpl dataUsers);

    void inject(BasePresenter basePresenter);

    void inject(UsersListPresenter usersListPresenter);

}
