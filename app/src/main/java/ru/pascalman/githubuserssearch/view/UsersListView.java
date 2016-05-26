package ru.pascalman.githubuserssearch.view;

import ru.pascalman.githubuserssearch.presenter.User;

import java.util.List;

public interface UsersListView extends View
{

    void showUsersList(List<User> vo, String key);

    void showEmptyList();

    String getUserName();

}
