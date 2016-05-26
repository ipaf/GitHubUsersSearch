package ru.pascalman.githubuserssearch.presenter;

import ru.pascalman.githubuserssearch.model.dto.UsersDTO;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class UsersListMapper implements Func1<UsersDTO, List<User>>
{

    @Inject
    public UsersListMapper()
    {
    }

    @Override
    public List<User> call(UsersDTO userDTOs)
    {
        if (userDTOs == null)
            return null;

        return Observable.from(userDTOs.getItems())
                .map(userDTO -> new User(userDTO.getLogin()))
                .toList()
                .toBlocking()
                .first();
    }

}
