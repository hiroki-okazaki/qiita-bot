package com.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.QiitaBotApplication;
import com.example.domain.User;
import com.example.repository.UserRepository;
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
	private PushConfirmController pushConfirmController;
//	private static final Pattern pattern = Pattern.compile("<a class=\"u-link-no-underline.+?</a>", Pattern.DOTALL);
	private static final Pattern pattern = Pattern.compile("<a class=\"tr-Item.+?</a>", Pattern.DOTALL);
	private static final Pattern jsonpattern = Pattern.compile("\"createdAt\".+?node", Pattern.DOTALL);

	// 指定したユーザーにメッセージを送信するメソッド
	public void pushMessage() {

		List<User> userList = userRepository.findAll();
		Logger log = LoggerFactory.getLogger(QiitaBotApplication.class);
		String html = pushConfirmController.loadHtml();

		for (User user : userList) {
			String userId = user.getUserId();

			try {
				BotApiResponse apiResponse = lineMessagingClient
						.pushMessage(new PushMessage(userId, new TextMessage(html))).get();
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

	public String test() throws IOException, BadLocationException {

		URL url = new URL("https://qiita.com");

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
		System.out.format("デコード結果=%1$s", result);

		Matcher matcher = jsonpattern.matcher(result);
		String str;
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			str = matcher.group();
			// <a>タグの中のtref以降のパスを取得する
			sb.append(str + System.getProperty("line.separator") + "\n");
		}
		return sb.toString();
	}
}