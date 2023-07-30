package com.example.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
import org.bouncycastle.jcajce.provider.symmetric.AES.AlgParamsGCM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.model.MResult;
import com.example.form.ExerciseListDto;
import com.example.form.Result;
import com.example.form.ResultListDto;
import com.example.form.User;
import com.example.service.impl.ResultServiceimpl;

@Controller
@RequestMapping("/exerciseResult")
public class ResultController {
	@Autowired
	HttpSession session;
	@Autowired
	ResultServiceimpl resultService;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ResultListDto resultListDto;

	// 確定ボタン押下時
	@PostMapping("/MS203Enter")
	public String postMS203Enter(Model model) {
		// カレンダークラスを使って曜日によって煽り文を出す

		// セッションから総合回数と総合カロリーを出す
		int totalCount = (int) session.getAttribute("totalCount");
		double totalCal = (double) session.getAttribute("totalCal");
		// ユーザーをセッションから出す
		User user = (User) session.getAttribute("user");
		// ユーザーIdを取得する
		String userId = user.getUserID();
		// 結果テーブルのMaxIdを求める
		String resultId = resultService.findMaxResultId();
		// ＊総合カロリーから47.2kcalで割り算して距離（km)を割り出す
		double resultDistance = totalCal / 47.2;
		// ＊現在の時間を取得する
		Calendar cal1 = Calendar.getInstance(); // (1)オブジェクトの生成

		int year = cal1.get(Calendar.YEAR); // (2)現在の年を取得
		int month = cal1.get(Calendar.MONTH) + 1; // (3)現在の月を取得
		int day = cal1.get(Calendar.DATE); // (4)現在の日を取得
		String msg = "";
		
		StringBuffer dow = new StringBuffer();
		switch (cal1.get(Calendar.DAY_OF_WEEK)) { // (8)現在の曜日を取得
		case Calendar.SUNDAY:
			dow.append("日曜日");
			break;
		case Calendar.MONDAY:
			dow.append("月曜日");
			msg = "今週も頑張りましょう！"; 
			break;
		case Calendar.TUESDAY:
			dow.append("火曜日");
			break;
		case Calendar.WEDNESDAY:
			dow.append("水曜日");
			msg="なか日です。しんどいと思いますが頑張りましょうね";
			break;
		case Calendar.THURSDAY:
			dow.append("木曜日");
			break;
		case Calendar.FRIDAY:
			dow.append("金曜日");
			break;
		case Calendar.SATURDAY:
			dow.append("土曜日");
			msg="今週も頑張りましたね。お疲れさまでした！！";
			break;
		}

		// (9)現在の年、月、日、曜日
		String resultDate = String.format("%04d-%02d-%02d", year, month, day); // Date型にキャストする
		// Resultインスタンスを作成する
		Result result = new Result(resultId, userId, totalCal, resultDistance, totalCount, resultDate);
		// MResultインスタンスに変換する
		MResult mResult = modelMapper.map(result, MResult.class);
		//結果リストのユーザIDと紐付いている重複していない"日付"のみ取得
		List<MResult> daysList = resultService.findResultDay(userId);
		// サービスのinsertOne使用して1件追加する
		resultService.insertOne(mResult);
		// 筋トレ結果のユーザーIDと紐付いているものを検索する
		List<MResult> mresultList = resultService.findResultbyUserOnly(userId);
		//過去7日間の結果を格納するListを作成する
		List<Result> weeklyList = new ArrayList<>();
		// 合計消費カロリー、合計距離の変数を作成する
		double resultCalAll = 0;
		double resultDistanceAll = 0;
		//resultListから過去7日間の結果を抽出する
		for(int i = daysList.size()-1,j=0;j < 7;i--,j++) {
			if(daysList.size() - 1- j < 0) {
				break;
			}
			//日付リストから過去7日間逆引きする
			mResult = daysList.get(i);
			result = modelMapper.map(mResult,Result.class);
			String d = result.getResultDate();
			//日付とユーザIDが紐付いている結果を取得する
			mresultList = resultService.findResultOne_day(userId,d);
			for(MResult r : mresultList) {
				result = modelMapper.map(r, Result.class);
				weeklyList.add(result);
			}
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("weeklyList",weeklyList);
		model.addAttribute("resultCalAll",resultCalAll);
		model.addAttribute("resultDistanceAll",resultDistanceAll);
		return "exerciseRersult/MS206WeeklyTotal";
	}
	
	//進むボタン押下時
	@PostMapping("/MS207MonthlyTotal")
	public String postMS207MonthlyTotal(Model model) {
		//セッションからユーザIDを取得
		User user = (User)session.getAttribute("user");
		String userID = user.getUserID();

		Calendar cal1 = Calendar.getInstance(); // (1)オブジェクトの生成
		int year = cal1.get(Calendar.YEAR); // (2)現在の年を取得
		int month = cal1.get(Calendar.MONTH) + 1; // (3)現在の月を取得
		int day =  cal1.get(Calendar.DATE);// (4)現在の日を取得
		cal1.set(year, month, 1);//(5)月初をセット
		int maxDate = cal1.getActualMaximum(Calendar.DATE);//(6)月末を取得
		String msg = "";
		
		//煽り文を日付毎にメッセージとして格納
		if(day==1) {
			msg="月初めです。今月も頑張りましょうね！";
		}else if(day==16) {
			msg="今月も半分が過ぎましたね。無理せず行きましょう";
		}else if(day==maxDate) {
			msg="月末です。今月も頑張りました。目標には届きましたか？";
		}else {
			msg="お疲れさまでした。今日も頑張りましたね！！";
		}
		
		
		//結果リストのユーザIDと紐付いている重複していない"日付"のみ取得
		List<MResult> daysList = resultService.findResultDay(userID);
		//結果リストを格納するリストを作成
		List<MResult> mResultList = new ArrayList<>();
		List<Result> resultList = new ArrayList<>();
		//カロリーの合計
		double sumCal = 0;
		//距離の合計
		double sumDistance = 0;
		
		for(int i = daysList.size()-1,j=0;j < 30;i--,j++) {
			if(daysList.size() - 1 - j < 0) {
				break;
			}
			//日付リストから過去30日間逆引きする
			MResult mResult = daysList.get(i);
			Result result = modelMapper.map(mResult,Result.class);
			String d = result.getResultDate();
			//日付とユーザIDが紐付いている結果を取得する
			mResultList = resultService.findResultOne_day(userID,d);
			//過去30日間の結果をリストに追加、カロリーと距離の合計を計算する
			for(MResult r : mResultList) {
				result = modelMapper.map(r, Result.class);
				double resultCal = result.getResultCal();
				double resultDistance = result.getResultDistance();
				sumCal += resultCal;
				sumDistance += resultDistance;
				resultList.add(result);
			}
		}
		//カロリーの合計と距離の合計を小数点第2位で丸める
		sumCal = Math.round(sumCal * 100);
		sumDistance = Math.round(sumDistance * 100);
		sumCal = sumCal /100;
		sumDistance = sumDistance / 100;

		model.addAttribute("msg",msg);
		model.addAttribute("monthlytList",resultList);
		//モデルに結果リストとカロリーと距離の合計を格納する
		model.addAttribute("sumCal",sumCal);
		model.addAttribute("sumDistance",sumDistance);

		return "exerciseRersult/MS207MonthlyTotal";
	}
}

