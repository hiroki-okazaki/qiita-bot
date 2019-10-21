package com.example.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.JsonData;

@Service
@Transactional
public class PushConfirmService {

	private static final Pattern jsonpattern = Pattern.compile(".\"trend\":.+?\"daily\"}", Pattern.DOTALL);

	public String selectionJsonData(String strurl) throws Exception {

		URL url = new URL(strurl);

		HTMLEditorKit kit = new HTMLEditorKit();
		HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
		doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
		Reader HTMLReader = new InputStreamReader(url.openConnection().getInputStream());
		kit.read(HTMLReader, doc, 0);

		StringWriter writer = new StringWriter();
		kit.write(writer, doc, 0, doc.getLength());
		String s = writer.toString();

		// デコードしたい文字列
		String source = s;
		// デコード後に文字列に置き換える際のエンコーディング
		String encoding = "UTF-8";

		// デコード処理
		String result = URLDecoder.decode(source, encoding);
		// 標準出力
//		System.out.format("デコード結果=%1$s", result);

		Matcher matcher = jsonpattern.matcher(result);
		String str;
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			str = matcher.group();
			// <a>タグの中のtref以降のパスを取得する
			sb.append(str);
		}
		String jsonstr = sb.toString();
		
		//取り出したJSONデータからランダムな投稿記事URLを取り出す
		String pushUrl = pushUrl(strurl,jsonstr);

		return pushUrl;
	}

	private String pushUrl(String topUrl, String jsonstr) throws Exception {

		try {
//    	     String型のJSONテキストをJSONObjectに変換
			JSONObject json = new JSONObject(jsonstr);

			// uuid,urlNameを入れるドメインクラス
			JsonData data = new JsonData();

			// JSONから取り出した値(各ブログ記事のurlパス名)を格納するリスト
			List<String> urlList = new ArrayList<>();

			// ブロック部：getJSONObjectメソッド
			// キー・バリュー部：getStringメソッド
			// 配列部分：getJSONArrayメソッド＋getJSONObjectメソッドで繰り返し取得
			String name = ((JSONObject) json.getJSONObject("trend").getJSONArray("edges").get(0)).getJSONObject("node")
					.get("uuid").toString();

			// key=edgesのvalue配列に入ったブログ記事の数
			int limit = json.getJSONObject("trend").getJSONArray("edges").length();

			// ブログ記事の数だけfor文を回し、uuidとurlNameを取り出しリストに格納
			for (int i = 0; i < limit; i++) {
				JSONObject jsonInNode = ((JSONObject) json.getJSONObject("trend").getJSONArray("edges").get(i))
						.getJSONObject("node");

				data.setUuid(jsonInNode.get("uuid").toString());
				data.setUrlName(jsonInNode.getJSONObject("author").get("urlName").toString());
				urlList.add(data.getUrlName() + "/items/" + data.getUuid());
				data = new JsonData();
			}

			// 0~ブログ記事の数からランダムの数字のブログ記事URLを取り出す
			Random rnd = new Random();
			StringBuilder pushUrl = new StringBuilder();
			
			 // シャッフルして、順番を変える
	        Collections.shuffle(urlList);
	        for(int i = 0;i < 5;i++) {
	        pushUrl.append(topUrl + "/" + urlList.get(i) + "\n");
	        }
	         System.out.println(urlList);
	        
			return pushUrl.toString();
		} catch (JSONException e) {
			System.out.println(e.getMessage());
		}
		return null;

	}
}