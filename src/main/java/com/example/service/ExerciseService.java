package com.example.service;

import java.util.List;

import com.example.domain.user.model.MExercise;

public interface ExerciseService {
	/** 筋トレ1件表示 */
	public MExercise findByExerciseOne(String id);

	/** 筋トレ全件表示 */
	public List<MExercise> findExerciseAll();

	/** ユーザーIDと紐付いている筋トレとユーザーIDがnullのものだけ表示する */
	public List<MExercise> findIdExerciseMany(String id);
	
	/**検索キーワードにヒットした筋トレメニューを全て取得する*/
	public List<MExercise> findExerciseVarious(String keyWord);
	
	/** 筋トレ登録 */
	public void insertExerciseOne(MExercise exercise);

	/** 筋トレのMaxIdを取得する */
	public String findMaxExerciseId();

	/** 筋トレ1件削除 */
	public void deleteOneExercise(String id);

	/** 筋トレを更新する */
	public void updateOne(MExercise exercise);

}
