package com.example.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.QiitaBotApplication;
import com.example.domain.User;
import com.example.repository.UserRepository;
import com.example.service.PushConfirmService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

//@Slf4j
//@Controller
//@RequestMapping("/")

@Component
public class PushConfirmController {

	@Autowired
	private LineMessagingClient lineMessagingClient;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PushConfirmService pushConfirmService ;

	private static final Pattern pattern = Pattern.compile("<a class=\"tr-Item.+?</a>", Pattern.DOTALL);

	// 指定したユーザーにブログ記事のURLをメッセージ送信するメソッド
	public void pushMessage() throws Exception {

		List<User> userList = userRepository.findAll();
		Logger log = LoggerFactory.getLogger(QiitaBotApplication.class);
		
		String url = pushConfirmService.selectionJsonData("https://qiita.com");
		String message = "本日のQiita人気記事はこちら↓";
		String message2 = "毎日読んで知識を広げよう！";
		for (User user : userList) {
			String userId = user.getUserId();
//			String registration_url = user.getRegistrationUrl();
			

			try {
				BotApiResponse apiResponse = lineMessagingClient
						.pushMessage(new PushMessage(userId, new TextMessage(message + "\n" + url + "\n" + message2))).get();
				log.info("Sent messages: {}", apiResponse);
			} catch (Exception e) {
				System.out.println(e);
				// 送信先ID消失によるエラーの可能性があるため、IDを削除したのちcontinueする
			}
		}
	}

//	public void pushMessage(String challenge) {
//		String userId = "Udd89ec41ae851f75bc33dc4c331d56fb";
//		Logger log = LoggerFactory.getLogger(QiitaBotApplication.class);
//
//		try {
//			BotApiResponse apiResponse = lineMessagingClient.pushMessage(new PushMessage(userId, new TemplateMessage(
//					"明日は燃えるごみの日だよ！",
//					new ConfirmTemplate("ごみ捨ては終わった？", new MessageAction("はい", "はい"), new MessageAction("いいえ", "いいえ")))))
//					.get();
//			log.info("Sent messages: {}", apiResponse);
//		} catch (Exception e) {
//			System.out.println(e);
//			// 送信先ID消失によるエラーの可能性があるため、IDを削除したのちcontinueする
//		}
//	}

	// 指定したURLからHTMLを読み込み
	public String loadHtml() {
		URL url = null;
		InputStreamReader isr = null;
		StringBuilder str = new StringBuilder();
		String tags = null;

		try {
			url = new URL("https://qiita.com");

			// InputStream(バイトストリーム)のままでもHTMLは取得できるが文字化けする
			InputStream is = url.openStream();

			// InputStreamをUTF8のInputStreamReader(文字ストリーム)に変換する
			isr = new InputStreamReader(is, "UTF-8");

			// 一文字毎に読み込む
			while (true) {
				int i = isr.read();
				if (i == -1) {
					break;
				}
				str.append((char) i);
			}
			tags = filterOnlyAtag(str.toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				isr.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return tags;
	}

	// htmlテキストの内、<a>タグで囲まれた文字列だけを抽出
	private String filterOnlyAtag(String text) {
		StringBuilder sb = new StringBuilder();
		Matcher matcher = pattern.matcher(text);
		String str;
		System.out.println(matcher.find());
		while (matcher.find()) {
			str = matcher.group();
			// <a>タグの中のtref以降のパスを取得する
			sb.append(text + str.substring(str.indexOf("\"/") + 1, str.indexOf("\">")));
			// 改行
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	//指定したurlのHTMLを文字列として取り出すメソッド.
	public List<String> read(String url, String charset) throws Exception {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			URLConnection conn = new URL(url).openConnection();
			is = conn.getInputStream();
			isr = new InputStreamReader(is, charset);
			br = new BufferedReader(isr);

			ArrayList<String> lineList = new ArrayList<String>();
			String line = null;
			while ((line = br.readLine()) != null) {
				lineList.add(line);
			}
			return lineList;
		} finally {
			try {
				br.close();
			} catch (Exception e) {
			}
		}
	}
}