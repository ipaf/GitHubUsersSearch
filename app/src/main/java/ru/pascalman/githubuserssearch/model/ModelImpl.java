package ru.pascalman.githubuserssearch.model;

import ru.pascalman.githubuserssearch.model.api.ApiInterface;
import ru.pascalman.githubuserssearch.model.dto.UsersDTO;
import ru.pascalman.githubuserssearch.other.App;
import ru.pascalman.githubuserssearch.other.Const;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

public class ModelImpl implements Model
{

    private final Observable.Transformer schedulersTransformer;

    @Inject
    protected ApiInterface apiInterface;

    @Inject
    @Named(Const.UI_THREAD)
    Scheduler uiThread;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler ioThread;

    public ModelImpl()
    {
        App.getComponent().inject(this);
        schedulersTransformer = o -> ((Observable) o).subscribeOn(ioThread)
                .observeOn(uiThread)
                .unsubscribeOn(ioThread);
    }

    @Override
    public Observable<UsersDTO> getUsers(String name)
    {
        return apiInterface
                .getUsers(name)
                .compose(applySchedulers());
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers()
    {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

}
