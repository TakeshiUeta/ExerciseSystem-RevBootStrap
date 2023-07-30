package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.user.model.MExercise;
import com.example.form.Exercise;
import com.example.form.ExerciseListDto;
import com.example.form.User;
import com.example.service.impl.ExerciseServiceimpl;

@Controller
@RequestMapping("/exerciseMenu")
public class ExerciseController {
	@Autowired
	ExerciseServiceimpl exerciseService;
	@Autowired
	HttpSession session;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ExerciseListDto exerciseListDto;

	@ModelAttribute
	ExerciseListDto setExerciseList() {
		return new ExerciseListDto();
	}

	// 筋トレメニュー遷移
	@PostMapping("/MS201ExerciseMenu")
	public String postMS201ExerciseMenu(Model model) {
		// セッションからユーザーIDをゲットする
		User user = (User) session.getAttribute("user");
		String userId = user.getUserID();
		// userIdと紐付いている筋トレメニューとuserIdがnullの筋トレメニューを取得する
		List<MExercise> mExerciseList = exerciseService.findIdExerciseMany(userId);
		List<Exercise> exercises = new ArrayList<>();
		// MExerciseクラスをExerciseクラスに変換してexerciseListに格納する
		for (int i = 0; i < mExerciseList.size(); i++) {
			MExercise mExercise = mExerciseList.get(i);
			// MExerciseクラスをExerciseクラスに変換
			Exercise exercise = modelMapper.map(mExercise, Exercise.class);
			exercises.add(exercise);
		}
		exerciseListDto.setExerciseList(exercises);
		// modelに格納する
		model.addAttribute("exerciseListDto", exerciseListDto);
		// セッションから筋トレの件数をゲットする
		int exerciseCount = 0;
		// セッションから筋トレの件数をゲットする
		double sumCal = 0;
		try {
			exerciseCount = (int) session.getAttribute("exerciseCount");
			sumCal = (double) session.getAttribute("sumCal");
			
		} catch (NullPointerException e) {
			exerciseCount = 0;
			sumCal = 0;
		}
		// 筋トレの件数をセッションに格納する
		session.setAttribute("exerciseCount", exerciseCount);
		// 総合カロリーをセッションに格納する
		session.setAttribute("sumCal", sumCal);
		// 検索キーワードを格納する
		String keyword = "";
		model.addAttribute("keyword", keyword);
		// 画面遷移
		return "exerciseMenu/MS201ExerciseMenu";
	}

	// 検索ボタン押下時
	@PostMapping("/searchExercise")
	public String postSearchExercise(Model model, @ModelAttribute @RequestParam String keyword, Exercise exercise) {
		// 検索キーワードにヒットした筋トレメニューを取得する
		List<MExercise> mExerciseList = exerciseService.findExerciseVarious(keyword);
		// ユーザーIDをセッションから抜き出す
		User user = (User) session.getAttribute("user");
		String userId = user.getUserID();
		List<Exercise> exercises = new ArrayList<>();
		// ユーザーIDと紐付いている筋トレメニューをUserクラスのListに格納する
		for (int i = 0; i < mExerciseList.size(); i++) {
			MExercise mExercise = mExerciseList.get(i);
			String mUserId = mExercise.getUserID();
			exercise = modelMapper.map(mExercise, Exercise.class);
			if (userId == mUserId || mUserId.equals("")) {
				exercises.add(exercise);
			}
		}
		exerciseListDto.setExerciseList(exercises);
		// modelに格納する
		model.addAttribute("exerciseListDto", exerciseListDto);
		return "exerciseMenu/MS201ExerciseMenu";
	}

	// 追加するボタン押下時
	@GetMapping("/MS201add")
	public String getMS201Check(Model model, ExerciseListDto exerciseListDto) {
		// ExerciseListDtoクラスに内包してあるListを取得する
		List<Exercise> exerciseList = exerciseListDto.getExerciseList();
		int count = 0;
		List<Exercise> exercisesCart = new ArrayList<>();
		// メニューを追加したときに格納するカートを用意する
		exercisesCart = (List<Exercise>) session.getAttribute("exercisesCart");
		// トレーニング件数
		int exerciseCount = (int) session.getAttribute("exerciseCount");
		// 総合カロリー
		double sumCal = (double) session.getAttribute("sumCal");
		// カートがnullの場合は改めてNewする
		if (exercisesCart == null) {
			exercisesCart = new ArrayList<>();
		}
		// 回数が1以上ある筋トレメニューを抜き出してカートに格納する
		for (int i = 0; i < exerciseList.size(); i++) {
			Exercise exercise = exerciseList.get(i);
			count = exercise.getCount();
			double cal = exercise.getExerciseCal();
			// 回数 ＊ カロリー ＝ その筋トレにおけるカロリー
			double totalCal = cal * count;
			if (count >= 1) {
				exercisesCart.add(exercise);
				// カートに入れた筋トレ件数を加える
				exerciseCount++;
				// カートに入れた筋トレの総カロリーを出す
				sumCal += totalCal;
			}
		}

		// セッションスコープにカートを格納する
		session.setAttribute("exercisesCart", exercisesCart);
		// セッションスコープにトレーニング件数を格納する
		session.setAttribute("exerciseCount", exerciseCount);
		// セッションスコープに総合カロリーを格納する
		session.setAttribute("sumCal", sumCal);
		return "exerciseMenu/MS201ExerciseMenu";
	}

	// 新規設定ボタン押下時
	@PostMapping("/MS201NewSettings")
	public String postMS201NewSettings(Model model) {
		// セッションからユーザーIDをゲットする
		User user = (User) session.getAttribute("user");
		String userId = user.getUserID();
		// userIdと紐付いている筋トレメニューとuserIdがnullの筋トレメニューを取得する
		List<MExercise> mExerciseList = exerciseService.findUserIdExercise(userId);
		List<Exercise> exercises = new ArrayList<>();
		// MExerciseクラスをExerciseクラスに変換してexerciseListに格納する
		for (int i = 0; i < mExerciseList.size(); i++) {
			MExercise mExercise = mExerciseList.get(i);
			// MExerciseクラスをExerciseクラスに変換
			Exercise exercise = modelMapper.map(mExercise, Exercise.class);
			exercises.add(exercise);
		}
		exerciseListDto.setExerciseList(exercises);
		// modelに格納する
		model.addAttribute("exerciseListDto", exerciseListDto);
		// 新しい筋トレIDを取得する
		String exerciseId = exerciseService.findMaxExerciseId();
		// Exerciseクラスにセットする
		Exercise exercise = new Exercise();
		exercise.setExerciseID(exerciseId);
		exercise.setUserID(userId);
		// modelにセットする
		model.addAttribute("exercise", exercise);
		return "exerciseMenu/MS202PutExerciseMenu";
	}

	// 追加するボタン押下時
	@PostMapping("/MS202Put")
	public String postMS202Put(Model model, Exercise exercise, @ModelAttribute ExerciseListDto exerciseListDto,
			BindingResult bindingResult) {
		// バリデーション処理（エラーがあったときは呼び出し元に返す）
		/*
		 * if (bindingResult.hasErrors()) { postMS201NewSettings(model); }
		 */
		// セッションからユーザーIDを取得する
		User user = (User) session.getAttribute("user");
		String userId = user.getUserID();
		// ExerciseクラスをMExerciseに変換する
		MExercise mExercise = modelMapper.map(exercise, MExercise.class);
		// 筋トレを1件追加する
		exerciseService.insertExerciseOne(mExercise);
		// userIdと紐付いている筋トレメニューを取得する
		List<MExercise> mExerciseList = exerciseService.findUserIdExercise(userId);
		// ExerciseクラスのListをnewする
		List<Exercise> exerciseList = new ArrayList<>();
		// mExerciseListの中身をExerciseクラスに変換してexerciseListに格納する
		for (MExercise m : mExerciseList) {
			exercise = modelMapper.map(m, Exercise.class);
			exerciseList.add(exercise);
		}
		// exerciseListDtoにexerciseListをセット
		exerciseListDto.setExerciseList(exerciseList);
		// modelに格納する
		model.addAttribute(exerciseListDto);
		// 筋トレIDの最大値を新たに作成
		String exerciseId = exerciseService.findMaxExerciseId();
		// フィールドがnullのExerciseクラスを作成
		exercise = new Exercise();
		// 筋トレIDをセット
		exercise.setExerciseID(exerciseId);
		// ユーザーIDをセット
		exercise.setUserID(userId);
		// modelに格納する
		model.addAttribute("exercise", exercise);
		// 画面遷移
		return "exerciseMenu/MS202PutExerciseMenu";
	}

	// 変更するボタン押下時
	@PostMapping("/MS202Modify")
	public String postMS202Modify(Model model, ExerciseListDto exerciseListDto, Exercise exercise) {
		// セッションからユーザーを取得
		User user = (User) session.getAttribute("user");
		// ユーザーIDを取得する
		String userId = user.getUserID();
		// exerciseListをExerciseListDtoから取得する
		List<Exercise> eListBefore = exerciseListDto.getExerciseList();
		// userIdと紐付いている筋トレメニューを取得する
		List<MExercise> mExerciseList = exerciseService.findUserIdExercise(userId);
		// 空のexerciseListをnewする（見える化用）
		List<Exercise> showEList = new ArrayList<>();
		for (int i = 0; i < mExerciseList.size(); i++) {
			// DBに入っている筋トレメニューを取得する
			MExercise mExercise = mExerciseList.get(i);
			// 入力された筋トレメニューを取得する
			exercise = eListBefore.get(i);
			// DBに入っている筋トレ名を取得する
			String mExerciseName = mExercise.getExerciseName();
			// 入力された筋トレ名を取得する
			String exerciseName = exercise.getExerciseName();
			// DBに入っている筋トレカロリーを取得する
			double mExerciseCal = mExercise.getExerciseCal();
			// 入力された筋トレカロリーを取得する
			double exerciseCal = exercise.getExerciseCal();
			// 入力された筋トレ名もしくは筋トレカロリーがDB側と違う場合DBを更新する
			if (!mExerciseName.equals(exerciseName) || mExerciseCal != exerciseCal) {
				mExercise = modelMapper.map(exercise, MExercise.class);
				exerciseService.updateOne(mExercise);
				showEList.add(exercise);
			}
		}
		eListBefore.clear();
		// userIdと紐付いている筋トレメニューを取得する
		mExerciseList = exerciseService.findUserIdExercise(userId);
		for (MExercise m : mExerciseList) {
			exercise = modelMapper.map(m, Exercise.class);
			eListBefore.add(exercise);
		}
		// ExerciseListDtoにeListBeforeをセットする
		exerciseListDto.setExerciseList(eListBefore);
		// modelに格納する
		model.addAttribute("exerciseListDto", exerciseListDto);
		// 筋トレIDの最大値を取得
		String exerciseId = exerciseService.findMaxExerciseId();
		exercise = new Exercise();
		// 筋トレメニューに筋トレIDをセット
		exercise.setExerciseID(exerciseId);
		// modelに格納する
		model.addAttribute("exercise", exercise);
		// modelに見える化するリストを格納する
		model.addAttribute("showEList", showEList);
		// 画面遷移
		return "exerciseMenu/MS202PutExerciseMenu";
	}

	// 確認ボタン押下時
	@PostMapping("/MS201Check")
	public String postMS201Check(Model model, ExerciseListDto exerciseListDto) {
		List<Exercise> exercisesCart = new ArrayList<>();
		// セッションからカートを取り出す
		exercisesCart = (List<Exercise>) session.getAttribute("exercisesCart");
		// カートがnullだった場合メッセージをmodelに格納してメニュー画面にリダイレクトする
		if (exercisesCart == null) {
			// セッションからユーザーを取り出してユーザーIDを取得する
			User user = (User) session.getAttribute("user");
			String userId = user.getUserID();
			// ユーザーIDが紐付いているものとnullのものを検索して筋トレDtoクラスにセットする
			exercisesCart = new ArrayList<>();
			List<MExercise> mExerciseList = exerciseService.findIdExerciseMany(userId);
			for (MExercise m : mExerciseList) {
				Exercise exercise = modelMapper.map(m, Exercise.class);
				exercisesCart.add(exercise);
			}
			exerciseListDto.setExerciseList(exercisesCart);
			String msg = "トレーニングメニューを追加してください";
			model.addAttribute("exerciseListDto", exerciseListDto);
			model.addAttribute("msg", msg);
			return "exerciseMenu/MS201ExerciseMenu";
		}
		// 総合カロリーを作成
		double totalCal = 0;
		// 総合回数を作成
		int totalCount = 0;
		for (Exercise e : exercisesCart) {
			int count = e.getCount();
			double cal = e.getExerciseCal();
			double calVallue = count * cal;
			totalCount += count;
			totalCal += calVallue;
		}
		// exerciseListDtoにカートをセットする
		exerciseListDto.setExerciseList(exercisesCart);
		// modelに総合カロリーをセットする
		model.addAttribute("totalCal", totalCal);
		// modelに総合回数をセットする
		model.addAttribute("totalCount", totalCount);
		// modelにexerciseListDtoをセットする
		model.addAttribute("exerciseListDto", exerciseListDto);
		//セッションに総合回数をセットする
		session.setAttribute("totalCount", totalCount);
		//セッションに総合カロリーをセットする	
		session.setAttribute("totalCal", totalCal);
		// 画面遷移
		return "exerciseMenu/MS203CheckExerciseMenu";
	}

	// 再計算ボタン押下時
	@PostMapping("/MS203Recalculation")
	public String postMS203Recalculation(Model model, @ModelAttribute ExerciseListDto exerciseListDto) {
		List<Exercise> exerciseList = exerciseListDto.getExerciseList();
		// カートをセッションから取得する
		List<Exercise> cart = (List<Exercise>) session.getAttribute("exercisesCart");
		// 総合カロリーを作成
		double totalCal = 0;
		// 総合回数を作成
		int totalCount = 0;
		// 回数がカートとリストとで違う場合カートにセットする
		for (int i = 0; i < exerciseList.size(); i++) {
			Exercise cExercise = cart.get(i);
			Exercise lExercise = exerciseList.get(i);
			int cCount = cExercise.getCount();
			int lCount = lExercise.getCount();
			double calValue = lExercise.getExerciseCal() * lCount;
			if (cCount != lCount) {
				cart.set(i, lExercise);
			}
			// 総合回数
			totalCount += lCount;
			// 総合カロリー
			totalCal += calValue;
		}
		// セッションにカートを格納する
		session.setAttribute("exercisesCart", cart);
		// modelに総合カロリーと総合回数を格納する
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalCal", totalCal);
		//セッションに総合カロリーと総合回数を格納する
		session.setAttribute("totalCount", totalCount);
		session.setAttribute("totalCal", totalCal);	
		return "exerciseMenu/MS203CheckExerciseMenu";
	}
}
