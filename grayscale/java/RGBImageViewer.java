import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;

public class RGBImageViewer extends JFrame {
	
	String appName = "RGBImage Viewer";  // アプリケーションの名前
	int width, height;                   // ウィンドウの幅と高さ
	RGBImagePanel  panel;                // RGB色が反転された画像を表示するためのパネル
	
	// コンストラクタ
	public RGBImageViewer() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ウィンドウが閉じられたらプログラムを終了
		setTitle(appName);  // ウィンドウのタイトルを設定
		Container container = this.getContentPane();  // コンテンツペインを取得

		Image image = load();  // 画像をロード
		if (image != null){
			Insets insets = getInsets();  // ウィンドウのボーダーのサイズを取得
			// 画像のサイズとボーダーのサイズを加算して、ウィンドウのサイズを計算
			width = image.getWidth(this) + insets.left + insets.right;
			height = image.getHeight(this) + insets.top + insets.bottom;
		} else {
			System.out.println("Error");
			System.exit(0);  // 画像がロードできなかった場合、プログラムを終了
		}
		setSize(width, height);  // ウィンドウのサイズを設定
		panel = new RGBImagePanel(image);  // パネルを作成
		
		container.add(panel);  // パネルをコンテンツペインに追加
	}

	// 画像をロードするメソッド
	public Image load() {
		Image image = null;
		// ファイルダイアログを表示して、画像ファイルを選択
		FileDialog l = new FileDialog(this, "Load", FileDialog.LOAD);
		l.setModal(true);
		l.setVisible(true);
		if (l.getFile() != null){
			MediaTracker tracker = new MediaTracker(this);  // メディアトラッカーを作成
			try {
				// 選択された画像ファイルをロード
				image = ImageIO.read(new File(l.getDirectory()+l.getFile()));
			} catch (Exception e) {
				System.out.println(e);
				System.exit(0);  // エラーが発生した場合、プログラムを終了
			}
			tracker.addImage(image, 0);  // 画像をメディアトラッカーに追加
			try {
				// 画像のロードが完了するのを待つ
				tracker.waitForID(0);
			} catch(InterruptedException e) { }
		}
		return image;  // ロードした画像を返す
	}
	
	public static void main(String[] args) {
		RGBImageViewer f = new RGBImageViewer();  // アプリケーションを起動
		f.setVisible(true);  // ウィンドウを表示
	}
}
