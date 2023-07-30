package com.example.form;

import lombok.Data;

@Data
public class Result {
	/* 筋トレ結果ID */
	private String resultID;
	/* 会員ID */
	private String userID;
	/* 合計消費カロリー */
	private double resultCal;
	/* 進んだ距離 */
	private double resultDistance;
	/* 筋トレ件数 */
	private int resultCount;
	/* 筋トレ日 */
	private String resultDate;

	/**
	 * コンストラクタ
	 */
	public Result() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param resultID       筋トレ結果ID
	 * @param userID         会員ID
	 * @param resultCal      合計消費カロリー
	 * @param resultDistance 進んだ距離
	 * @param resultCount    筋トレ件数
	 * @param resultDate     筋トレ日
	 */
	public Result(String resultID, String userID, double resultCal, double resultDistance, int resultCount,
			String resultDate) {
		this.resultID = resultID;
		this.userID = userID;
		this.resultCal = resultCal;
		this.resultDistance = resultDistance;
		this.resultCount = resultCount;
		this.resultDate = resultDate;
	}
}
