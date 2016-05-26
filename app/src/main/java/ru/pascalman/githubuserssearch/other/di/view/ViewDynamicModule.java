package ru.pascalman.githubuserssearch.other.di.view;

import ru.pascalman.githubuserssearch.presenter.UsersListPresenter;
import ru.pascalman.githubuserssearch.view.UsersListView;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewDynamicModule
{

    private UsersListView view;

    public ViewDynamicModule(UsersListView view)
    {
        this.view = view;
    }

    @Provides
    UsersListPresenter provideUsersListPresenter()
    {
        return new UsersListPresenter(view);
    }

}
