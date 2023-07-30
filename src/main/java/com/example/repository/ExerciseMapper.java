package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.user.model.MExercise;

@Mapper
public interface ExerciseMapper {
	// 筋トレ1件表示
	public MExercise findById(String id);

	// 筋トレ全件表示
	public List<MExercise> findAll();

	public List<MExercise> findbyUserIdOnly(String id);
	
	// ユーザーIDと紐付いている筋トレとユーザーIDがnullのものだけ表示する
	public List<MExercise> findMany(String id);
	
	
	//検索キーワードにヒットした筋トレメニューを全て取得する
	public List<MExercise> findVarious(String Keyword);
	
	// 筋トレ登録
	public void insertOne(@Param("exerciseID") String exerciseID, @Param("userID") String userID,
			@Param("exerciseName") String exerciseName, @Param("exerciseCal") double exerciseCal);

	// 筋トレの1番最後に登録されたものを検索する
	public MExercise findMaxExercise();

	// 筋トレ1件削除
	public void deleteOne(String id);

	// 筋トレを更新する
	public void updateOne(@Param("exerciseName") String exerciseName,
			@Param("exerciseCal") double exerciseCal, @Param("exerciseID") String exerciseID);
}
