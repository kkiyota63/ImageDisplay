import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;


/**
 * RGBImagePanelは、与えられた画像のRGB色の処理を行い、その結果を表示するJPanelです。
 */

public class RGBImagePanel extends JPanel {
	
	int width, height;         // 画像の幅と高さ
	Image image = null;        // 元の画像
	BufferedImage bufImage = null;  // 色反転されたBufferedImage
	private int[] histogram = new int[256]; // グレースケールのヒストグラム用

	/**
	 * コンストラクタ。
	 * 与えられた画像のRGB色の処理を行い、結果をBufferedImageとして保存します。
	 * @param image 反転する元の画像
	 */

	public RGBImagePanel( Image image ){
		this.image = image;
		width = image.getWidth(this);  // 画像の幅を取得
		height = image.getHeight(this);  // 画像の高さを取得
		this.setSize(width, height);  // パネルのサイズを画像のサイズに設定
		
		// 画像をBufferedImageに変換
		bufImage = createBufferedImage(image);

		// 画像の各ピクセルの色を反転
		// for (int y = 0; y < bufImage.getHeight(); y++) {
		// 	for (int x = 0; x < bufImage.getWidth(); x++) {
		// 		int color = bufImage.getRGB(x, y);  // 現在のピクセルの色を取得
		// 		int r = 255 - getRed(color);   // 赤成分の反転
		// 		int g = 255 - getGreen(color); // 緑成分の反転
		// 		int b = 255 - getBlue(color);  // 青成分の反転
		// 		// 反転された色をBufferedImageに設定
		// 		bufImage.setRGB(x, y, 255 << 24 | r << 16 | g << 8 | b);
		// 	}
		// }

		// 画像の各ピクセルから緑成分のみを抽出
		// for (int y = 0; y < bufImage.getHeight(); y++) {
		// 	for (int x = 0; x < bufImage.getWidth(); x++) {
		// 		int color = bufImage.getRGB(x, y);  // 現在のピクセルの色を取得
		// 		int r = 0;               // 赤成分は0に
		// 		int g = getGreen(color); // 緑成分をそのまま
		// 		int b = 0;               // 青成分は0に
		// 		// 抽出された色をBufferedImageに設定
		// 		bufImage.setRGB(x, y, 255 << 24 | r << 16 | g << 8 | b);
		// 	}
		// }

		// 画像の各ピクセルをグレースケールに変換
		for (int y = 0; y < bufImage.getHeight(); y++) {
			for (int x = 0; x < bufImage.getWidth(); x++) {
				int color = bufImage.getRGB(x, y);
				double r = (double) getRed(color);
				double g = (double) getGreen(color);
				double b = (double) getBlue(color);
				int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
				bufImage.setRGB(x, y, (255 << 24) | (gray << 16) | (gray << 8) | gray);
			}
		}

		
	}

	/**
	 * 画像のヒストグラムを計算。
	 */
	public void computeHistogram() {
		//画像の各ピクセルの赤色の値（0から255まで）を取得して、それに基づいてヒストグラムを計算
		for (int y = 0; y < bufImage.getHeight(); y++) {
			for (int x = 0; x < bufImage.getWidth(); x++) {
				int color = bufImage.getRGB(x, y);
				int red = getRed(color);
				histogram[red]++;
			}
		}
	}

	/**
 	* ヒストグラムを描画。
 	* @param g Graphicsオブジェクト
 	*/
	public void drawHistogram(Graphics g) {
		//ストグラムの中で最も高い棒の高さを見つけるために、最大頻度を計算
		int maxFrequency = 0;
		for (int i = 0; i < histogram.length; i++) {
			if (histogram[i] > maxFrequency) {
				maxFrequency = histogram[i];
			}
		}

		int histWidth = width;
		int histHeight = 200;  // ヒストグラムの高さは200ピクセルとする

		//描画。棒の高さは、それぞれの赤色の値の頻度に基づいてスケール
		for (int i = 0; i < histogram.length; i++) {
			int barHeight = (int) ((double) histogram[i] / maxFrequency * histHeight);
			//g.drawLineを使用して、ヒストグラムの各棒を線として描画
			g.drawLine(i, histHeight, i, histHeight - barHeight);
		}
	}

	// 画像の各ピクセルをグレースケールに変換し、閾値を32として2値化
	public void convertToBinary() {
		final int THRESHOLD = 32;
		for (int y = 0; y < bufImage.getHeight(); y++) {
			for (int x = 0; x < bufImage.getWidth(); x++) {
				int color = bufImage.getRGB(x, y);
				double r = (double) getRed(color);
				double g = (double) getGreen(color);
				double b = (double) getBlue(color);
				int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);

				// 2値化
				if (gray > THRESHOLD) {
					gray = 255;
				} else {
					gray = 0;
				}
				bufImage.setRGB(x, y, (255 << 24) | (gray << 16) | (gray << 8) | gray);
			}
		}
	}
	

	/**
	 * 与えられたImageをBufferedImageに変換します。
	 * @param img 変換するImage
	 * @return BufferedImageに変換されたイメージ
	 */
	public BufferedImage createBufferedImage(Image img) {
		BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bimg.getGraphics();
		g.drawImage(img, 0, 0, null);  // 画像をBufferedImageにコピー
		g.dispose();
		return bimg;
	}

	// 与えられた色から赤成分を取得
	public int getRed(int color) {
		return color >> 16 & 0xff;
	}

	// 与えられた色から緑成分を取得
	public int getGreen(int color) {
		return color >> 8 & 0xff;
	}

	// 与えられた色から青成分を取得
	public int getBlue(int color) {
		return color & 0xff;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);  // 背景などのデフォルトの描画を行うための呼び出し
		g.drawImage(bufImage, 0, 0, this);
		
		computeHistogram();

		// 画像の下部にヒストグラムを描画
		g.translate(0, height);
		drawHistogram(g);
	}

	//2値変換用のメソッド
	// public void paint(Graphics g) {
	// 	super.paint(g);  // 背景などのデフォルトの描画を行うための呼び出し
	// 	convertToBinary();  // 2値化の処理を行う
	// 	g.drawImage(bufImage, 0, 0, this);
		
	// 	computeHistogram();

	// 	// 画像の下部にヒストグラムを描画
	// 	g.translate(0, height);
	// 	drawHistogram(g);
	// }
	


}
