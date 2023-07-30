package com.example.form;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class Exercise {
	/*筋トレID*/
	@NotBlank
	private String exerciseID;
	/*会員ID*/
	private String userID;
	/*筋トレ名*/
	@NotBlank
	private String exerciseName;
	/*消費カロリー*/
	@NotNull
	private double exerciseCal;
	/*＊回数*/
	private int count;
	
	/**
	 * コンストラクタ
	 */
	public Exercise() {
	}

	/**
	 * コンストラクタ
	 * @param exerciseID 筋トレID
	 * @param userID 会員ID
	 * @param exerciseName 筋トレ名
	 * @param exerciseCal 消費カロリー
	 */
	public Exercise(String exerciseID, String userID, String exerciseName,
			double exerciseCal) {
		this.exerciseID = exerciseID;
		this.userID = userID;
		this.exerciseName = exerciseName;
		this.exerciseCal = exerciseCal;
	}

	/**
	 * コンストラクタ
	 * @param exerciseID 筋トレID
	 * @param userID 会員ID
	 * @param exerciseName 筋トレ名
	 * @param exerciseCal 消費カロリー
	 * @param count 回数
	 */
	public Exercise(String exerciseID, String userID, String exerciseName,
			double exerciseCal, int count) {
		this.exerciseID = exerciseID;
		this.userID = userID;
		this.exerciseName = exerciseName;
		this.exerciseCal = exerciseCal;
		this.count = count;
	}
}
