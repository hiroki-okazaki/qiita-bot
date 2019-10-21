package com.example.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * LINEサービス
 * 
 * @author s-tsuchida
 */

@Component
public class LineService {
	

	// 返答メッセージ
	private static final String PHILOSOPHY_MESSAGE = "ITに関わるすべての人たちを応援する楽楽パートナー";
	private static final String CORE_VALUE_01_MESSAGE = "1.応えるお客様の期待に120%応えます。";
	private static final String CORE_VALUE_02_MESSAGE = "2.育成する結果が出せる人財を育成します";
	private static final String CORE_VALUE_03_MESSAGE = "3.改善する日々その活動を改善します。";
	private static final String CORE_VALUE_04_MESSAGE = "4.偽らないステークホルダーに対して偽りません。";
	private static final String CORE_VALUE_05_MESSAGE = "5.進化する変化の予兆を読み取り柔軟に進化します。";
	private static final String CORE_VALUE_ALL_MESSAGE = CORE_VALUE_01_MESSAGE + "\r\n" + CORE_VALUE_02_MESSAGE + "\r\n"
			+ CORE_VALUE_03_MESSAGE + "\r\n" + CORE_VALUE_04_MESSAGE + "\r\n" + CORE_VALUE_05_MESSAGE + "\r\n";

	// メッセージ該当外メッセージ
	private static final String OTHER_MESSAGE = "メッセージありがとう！" + "\r\n" + "毎日18時にQiitaの人気投稿記事を送るよ!";

	// メッセージMAP
	private static final Map<String, String> MESSAGE_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{


			// 経営理念
			put("philosophy", PHILOSOPHY_MESSAGE);
			put("PHILOSOPHY", PHILOSOPHY_MESSAGE);
			put("けいえいりねん", PHILOSOPHY_MESSAGE);
			put("経営理念", PHILOSOPHY_MESSAGE);

			// コアバリュー1
			put("corevalue1", CORE_VALUE_01_MESSAGE);
			put("COREVALUE1", CORE_VALUE_01_MESSAGE);
			put("こあばりゅー1", CORE_VALUE_01_MESSAGE);
			put("コアバリュー1", CORE_VALUE_01_MESSAGE);

			// コアバリュー2
			put("corevalue2", CORE_VALUE_02_MESSAGE);
			put("COREVALUE2", CORE_VALUE_02_MESSAGE);
			put("こあばりゅー2", CORE_VALUE_02_MESSAGE);
			put("コアバリュー2", CORE_VALUE_02_MESSAGE);

			// コアバリュー3
			put("corevalue3", CORE_VALUE_03_MESSAGE);
			put("COREVALUE3", CORE_VALUE_03_MESSAGE);
			put("こあばりゅー3", CORE_VALUE_03_MESSAGE);
			put("コアバリュー3", CORE_VALUE_03_MESSAGE);

			// コアバリュー4
			put("corevalue4", CORE_VALUE_04_MESSAGE);
			put("COREVALUE4", CORE_VALUE_04_MESSAGE);
			put("こあばりゅー4", CORE_VALUE_04_MESSAGE);
			put("コアバリュー4", CORE_VALUE_04_MESSAGE);

			// コアバリュー5
			put("corevalue5", CORE_VALUE_05_MESSAGE);
			put("COREVALUE5", CORE_VALUE_05_MESSAGE);
			put("こあばりゅー5", CORE_VALUE_05_MESSAGE);
			put("コアバリュー5", CORE_VALUE_05_MESSAGE);

			// コアバリューすべて
			put("corevalue", CORE_VALUE_ALL_MESSAGE);
			put("COREVALUE", CORE_VALUE_ALL_MESSAGE);
			put("こあばりゅー", CORE_VALUE_ALL_MESSAGE);
			put("コアバリュー", CORE_VALUE_ALL_MESSAGE);

		}
	});

	/**
	 * 送信されたLINEメッセージをもとに返答メッセージを返す
	 * 
	 * @param sendMessage 送信されたLINEメッセージ
	 * @return 返答メッセージ
	 */
	public String createResponseMessage(String sendMessage) {

		if (MESSAGE_MAP.containsKey(sendMessage)) {
			// 送信されたLINEメッセージがMAPのキーに存在する場合
			return MESSAGE_MAP.get(sendMessage);
		} else {
			// 送信されたLINEメッセージがMAPのキーに存在しない場合
			return OTHER_MESSAGE;
		}
	}
	
	public String createResponseMessage() {
		return MESSAGE_MAP.get(CORE_VALUE_ALL_MESSAGE);
	}

}
