package net.larntech.user;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface UserService {

    @GET("api/ValidaUsuario/")
    Call<String> loginUser(@Query("ptipoUsuario") Integer ptipoUsuario, @Query("pUsuario") String pUsuario, @Query("pClave") String pClave);


    @POST("users/")
    Call<RegisterResponse> registerUsers(@Body RegisterRequest registerRequest);


    @GET("api/ObtieneOrdenes")
    Call<String> getAllResultados(@Query("pId") Integer ptipoUsuario);

    @GET("api/ObtieneSucursales")
    Call<String> getAllSucursales(@Query("pId") Integer ptipoUsuario);


    @GET("api/SolicitarClave")
    Call<String> getSolicitarClave(@Query("pId") Integer ptipoUsuario);



}
