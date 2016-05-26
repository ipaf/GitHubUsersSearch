package ru.pascalman.githubuserssearch.model;

import ru.pascalman.githubuserssearch.model.dto.UsersDTO;

import rx.Observable;

public interface Model
{

    Observable<UsersDTO> getUsers(String name);

}
