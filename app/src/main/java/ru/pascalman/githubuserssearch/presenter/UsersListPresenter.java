package ru.pascalman.githubuserssearch.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import ru.pascalman.githubuserssearch.other.App;
import ru.pascalman.githubuserssearch.view.UsersListView;
import ru.pascalman.githubuserssearch.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

public class UsersListPresenter extends BasePresenter
{

    private String key;

    @Inject
    protected UsersListMapper usersListMapper;

    private UsersListView view;

    private List<User> userList;

    // for DI
    @Inject
    public UsersListPresenter()
    {}

    public UsersListPresenter(UsersListView view)
    {
        App.getComponent().inject(this);
        this.view = view;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    @Override
    protected View getView()
    {
        return view;
    }

    public void onSearchButtonClick()
    {
        String name = view.getUserName();

        if (TextUtils.isEmpty(name))
            return;

        Subscription subscription = model.getUsers(name)
                .map(usersListMapper)
                .subscribe(new Observer<List<User>>()
                {

                    @Override
                    public void onCompleted()
                    {}

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.d(UsersListPresenter.class.getName(), e.toString());
                        showError(e);
                    }

                    @Override
                    public void onNext(List<User> list)
                    {
                        if (list != null && !list.isEmpty())
                        {
                            userList = list;
                            view.showUsersList(list, key);
                        }
                        else
                            view.showEmptyList();
                    }

                });

        addSubscription(subscription);
    }

    public void onCreateView(Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
            userList = (List<User>) savedInstanceState.getSerializable(key);

        if (isUserListNotEmpty())
            view.showUsersList(userList, key);
    }

    private boolean isUserListNotEmpty()
    {
        return (userList != null && !userList.isEmpty());
    }

    public void onSaveInstanceState(Bundle outState)
    {
        if (isUserListNotEmpty())
            outState.putSerializable(key, new ArrayList<>(userList));
    }

}
