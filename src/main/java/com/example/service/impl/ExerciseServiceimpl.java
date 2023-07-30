package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MExercise;
import com.example.repository.ExerciseMapper;
import com.example.service.ExerciseService;

@Service
public class ExerciseServiceimpl implements ExerciseService {
	@Autowired
	ExerciseMapper exerciseMapper;

	/** 筋トレ1件表示 */
	@Override
	public MExercise findByExerciseOne(String id) {
		MExercise mExercise = exerciseMapper.findById(id);
		return mExercise;
	}
	
	/**ユーザーIDと紐付いている筋トレだけ表示する*/
	public List<MExercise> findUserIdExercise(String id){
		List<MExercise> exerciseList = exerciseMapper.findbyUserIdOnly(id);
		return exerciseList;
	}
	
	/** ユーザーIDと紐付いている筋トレとユーザーIDがnullのものだけ表示する */
	public List<MExercise> findIdExerciseMany(String id) {
		List<MExercise> exerciseList = exerciseMapper.findMany(id);
		return exerciseList;
	}

	/** 検索キーワードにヒットした筋トレメニューを全て取得する */
	public List<MExercise> findExerciseVarious(String keyWord) {
		List<MExercise> exerciseList = exerciseMapper.findVarious(keyWord);
		return exerciseList;
	}

	/** 筋トレ全件表示 */
	@Override
	public List<MExercise> findExerciseAll() {
		List<MExercise> exerciseList = exerciseMapper.findAll();
		return exerciseList;
	}

	/** 筋トレ登録 */
	@Override
	public void insertExerciseOne(MExercise exercise) {
		String exerciseID = exercise.getExerciseID();
		String userID = exercise.getUserID();
		String exerciseName = exercise.getExerciseName();
		double exerciseCal = exercise.getExerciseCal();
		exerciseMapper.insertOne(exerciseID, userID, exerciseName, exerciseCal);
	}

	/** 筋トレのMaxIdを取得する */
	@Override
	public String findMaxExerciseId() {
		// 筋トレの最後のメニューを取得する
		MExercise mExercise = exerciseMapper.findMaxExercise();
		// 筋トレID取得
		String exerciseId = mExercise.getExerciseID();
		// Idの文字列部分を切り離す
		String start = exerciseId.substring(0, 2);
		// Idの数値部分を切り離す
		String end = exerciseId.substring(2, 6);
		// Idの数値部分をint型にキャストして1加える
		int value = Integer.parseInt(end) + 1;
		// 空の文字列を作成
		String maxValue = "";
		if (value >= 10) {
			// 最大値が10以上だった場合
			maxValue = "00" + Integer.toString(value);
		} else if (value >= 100) {
			// 最大値が100以上だった場合
			maxValue = "0" + Integer.toString(value);
		} else if (value >= 1000) {
			// 最大値が1000以上だった場合
			maxValue = Integer.toString(value);
		} else {
			// 最大値が10未満だった場合
			maxValue = "000" + Integer.toString(value);
		}
		// 切り離した2文字と最大値を文字結合してIDを作る
		String exerciseMaxId = start + maxValue;
		return exerciseMaxId;
	}

	/** 筋トレ1件削除 */
	@Override
	public void deleteOneExercise(String id) {
		exerciseMapper.deleteOne(id);
	}

	/** 筋トレを更新する */
	@Override
	public void updateOne(MExercise exercise) {
		String exerciseID = exercise.getExerciseID();
		String exerciseName = exercise.getExerciseName();
		double exerciseCal = exercise.getExerciseCal();
		exerciseMapper.updateOne(exerciseName, exerciseCal,exerciseID);
	}
}
