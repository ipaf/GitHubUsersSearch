package ru.pascalman.githubuserssearch.model.api;

import ru.pascalman.githubuserssearch.model.dto.UsersDTO;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ApiInterface
{

    @GET("/search/users")
    Observable<UsersDTO> getUsers(@Query("q") String name);

}
