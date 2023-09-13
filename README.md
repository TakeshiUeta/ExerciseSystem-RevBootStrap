# ExerciseSystem-RevBootStrap
ExerciseSystem-RevBootStrap
アプリ名：筋トレ_消費カロリー・徒歩距離計算アプリ
URL
http://54.150.193.189:8080/ExerciseSystem-0.0.1-SNAPSHOT/exerciseUser/MS101UserLogin
（こちらからアクセスできますが、プライバシー保護のため個人情報は入力しないでください。）

概要
筋トレの実績を消費カロリーとそれを基に歩いた距離を計算して、記録するアプリケーションです。
目指した課題解決
筋トレを漫然と行っていて成果がわからず、モチベーションが上がらない人向け。
毎日行う筋トレの成果を消費カロリー、歩いた距離に変換。
ユーザーの健康を維持するとともに達成感を味わってもらいモチベーションを上げてもらう。
使用した技術
バックエンド
・Spring-Boot2.6.3
・MySQL
・Java17
・Maven
・MyBatis
フロントエンド
・thyme-leaf
・html・CSS
・Boot-Strap4.5
・jquery
インフラ
・AWS(IAMUser,EC2)
・Tomcat
・mariaDB
・H2DB

テスト用アカウント
ID/Passは以下をお使いください。

   ID    :UR0013
   Pass  :ps0013
使用方法
※アプリのページ構造に関しては101筋トレアプリ画面遷移図をご確認ください。
（DesignBookディレクトリに格納されています。）

ログイン画面でID/PASSを入力してください。
各画面・モジュールの説明
  ■UserLogin
     ログイン画面です。
      ・ログイン・・・ID/PASSを入力してTOP画面に遷移します。
      ・ユーザー登録・・・ユーザー登録画面に遷移します。

  ■ユーザー登録
     ユーザーを新規作成します。
      ・各項目を記入して登録を押してください。
      ・登録・・・ユーザーを登録します。
      ・クリア・・・記入した内容を全て消去します。
      ・戻る・・・ログイン画面に遷移します。
      ・登録を押すと情報が画面下部に表示されます。確認してください。
      
  ■Let’sExercise！
      TOPページです。
       ・会員更新・・・ユーザー更新画面に遷移します。
       ・トレーニングメニュー・・・トレーニングメニュー画面に遷移します。
  
  ■ユーザー更新
      ユーザー情報を変更します。       
        ・変更したい項目を記入してください。
        ・更新ボタン・・・ユーザー情報が変更したものに更新されます。
        ・クリア・・・記入した内容がすべて消去されます。
        ・戻る・・・TOP画面に遷移します。
        ・更新ボタンを押すと画面下部の情報が更新されます。確認してください。
  
  ■トレーニングメニュー
      トレーニングメニューを選びカートに格納します。格納したものは画面上部のトレーニング件数に追加され、計算された総合カロリーが表示されます。
        ・検索キーワード・・・検索したい筋トレのキーワードを入力します。
        ・検索・・・上記で入力したものを検索し、画面下部のトレーニングメニューに反映されます。
        ・クリア・・・入力した検索キーワードを消去します。
        ・確認・・・トレーニングメニュー確認に遷移します。（カートが空の場合はメッセージが表示され遷移しません。）
        ・戻る・・・TOP画面に遷移します。
        ・新規設定・・・メニュー新規設定画面に遷移します。
        ・ログアウト・・・ログアウト画面に遷移します。
        ・回数・・・トレーニングメニューを何回おこなったか入力します。
        ・追加する・・・トレーニングメニューの件数と kcal * 回数 を計算しカートに追加します。
  
  ■メニュー新規設定
      トレーニングメニューをユーザー個人で追加したり、トレーニングメニューの名前や消費カロリーを変更できる画面です。
        ・戻る・・・トレーニングメニュー画面に遷移します。
        ・ログアウト・・・ログアウト画面に遷移します。
        ・新規トレーニングメニュー・・・新しく追加するメニューの名前を入力するフォーム
        ・新規メニュー消費カロリー(kcal)・・・新しく追加するメニューの消費カロリーを入力するフォーム
        ・追加する・・・新規トレーニングメニュー、新規メニュー消費カロリー(kcal)で入力した内容をDBに追加するボタン
        ・変更するトレーニングメニュー・・・ユーザー個人で追加したメニューの名前を編集できるフォーム
        ・変更するメニュー消費カロリー(kcal)・・・ユーザー個人で追加したメニューの消費カロリーを編集できるフォーム
        ・変更する・・・変更した内容を更新するボタン

 ■トレーニングメニュー確認
       カートに格納したトレーニングメニュー、回数、合計消費カロリーを確定し、結果に反映するための画面です。
       またここで回数を変更し再計算することで反映する結果を変更することもできます。
         ・確定・・・結果：1週間の集計画面に遷移します。
         ・戻る・・・トレーニングメニュー画面に遷移します。
         ・ログアウト・・・ログアウト画面に遷移します。
         ・再計算・・・入力された回数と消費カロリーを基に再計算するボタン　 
         ・回数・・・トレーニングメニュー画面で追加した回数を変更するフォーム

 ■結果：一週間の集計
       これまでに行ってきた筋トレの実績が1週間単位で出力される画面です。月曜日、水曜日、土曜日でメッセージが表示されます。
         ・次に進む・・・結果一ヶ月の集計画面に遷移します。
         ・TOPに戻る・・・TOP画面に遷移します。
         ・ログアウト・・・ログアウト画面に遷移します。 

■結果：1ヶ月の集計
       これまでに行ってきた筋トレの実績が1ヶ月単位で出力される画面です。月初、15日、月末、年末、元旦にメッセージが表示されます。
           ・TOPに戻る・・・TOP画面に遷移します。
           ・ログアウト・・・ログアウト画面に遷移します。

■ログアウト画面
      ログアウト画面です。尚、ログアウトボタンを押すとセッションは切れます。
      ログイン画面に戻る・・・ログイン画面に遷移します。    