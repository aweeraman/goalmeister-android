package com.goalmeister;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;

import com.goalmeister.model.Goal;

public interface GoalService {

  @GET("/api/goals")
  List<Goal> list(@Header("Authorization") String bearerToken);

}
