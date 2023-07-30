package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MResult;
import com.example.repository.ResultMapper;

@Service
public class ResultServiceimpl {
	@Autowired
	ResultMapper resultMapper;

	// 結果1件取得
	public MResult findByResultId(String resultId) {
		MResult mResult = resultMapper.findById(resultId);
		return mResult;
	}

	// 結果全件表示
	public List<MResult> findResultAll() {
		List<MResult> list = resultMapper.findAll();
		return list;
	}

	// ユーザーIdだけ紐付いている結果表示
	public List<MResult> findResultbyUserOnly(String userId) {
		List<MResult> list = resultMapper.findbyUserIdOnly(userId);
		return list;
	}

	//ユーザと紐付いている結果の日付だけ取得する
	public List<MResult> findResultDay(String userID){
		List<MResult> list =  resultMapper.findResultDay(userID);
		return list;
	}

	//ユーザと結果の日付が紐付いている結果を取得する
	public List<MResult> findResultOne_day(String userID,String resultDate) {
		List<MResult> mResultList = resultMapper.findResultOne_day(userID,resultDate);
		return mResultList;
	}

	// 結果を挿入する
	public void insertOne(MResult mResult) {
		String resultId = mResult.getResultID();
		String userID = mResult.getUserID();
		double resultCal = mResult.getResultCal();
		double resultDistance = mResult.getResultDistance();
		int resultCount = mResult.getResultCount();
		String resultDate = mResult.getResultDate();
		resultMapper.insertOne(resultId, userID, resultCal, resultDistance, resultCount, resultDate);
	}

	// 結果のMaxIdを取得する
	public String findMaxResultId() {
		// テーブルの最後のレコードを取得する
		MResult result = resultMapper.findMaxResult();
		// idを取得する
		String id = result.getResultID();
		// アルファベット部分を切り離す
		String start = id.substring(0, 2);
		// 数字部分を切り離す
		String end = id.substring(2, 6);
		// 数字を文字型から数値にキャストして1加える
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
		String resultmaxId = start + maxValue;
		return resultmaxId;
	}

	// 結果を1件削除する
	public void deleteOne(String resultId) {
		resultMapper.deleteOne(resultId);
	}
	
}
