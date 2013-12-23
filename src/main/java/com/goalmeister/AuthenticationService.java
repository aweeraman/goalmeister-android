package com.goalmeister;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

import com.goalmeister.model.UserToken;

public interface AuthenticationService {

  @FormUrlEncoded
  @POST("/api/oauth2/token")
  UserToken authToken(@Header("Authorization") String basicAuthHeader,
      @Field("grant_type") String grantType, @Field("username") String username,
      @Field("password") String password, @Field("client_id") String clientId,
      @Field("client_secret") String clientSecret);

}
