package com.example.domain.user.model;

import lombok.Data;

@Data
public class MExercise {
	/*筋トレID*/
	private String exerciseID;
	/*会員ID*/
	private String userID;
	/*筋トレ名*/
	private String exerciseName;
	/*消費カロリー*/
	private double exerciseCal;
	/*回数*/
	private String count;
}
