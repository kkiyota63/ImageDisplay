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

	public void computeHistogram() {
		for (int y = 0; y < bufImage.getHeight(); y++) {
			for (int x = 0; x < bufImage.getWidth(); x++) {
				int color = bufImage.getRGB(x, y);
				int red = getRed(color);
				histogram[red]++;
			}
		}
	}

	/**
 * ヒストグラムを描画します。
 * @param g Graphicsオブジェクト
 */
	public void drawHistogram(Graphics g) {
		int maxFrequency = 0;
		for (int i = 0; i < histogram.length; i++) {
			if (histogram[i] > maxFrequency) {
				maxFrequency = histogram[i];
			}
		}

		int histWidth = width;
		int histHeight = 200;  // ヒストグラムの高さは200ピクセルとする

		for (int i = 0; i < histogram.length; i++) {
			int barHeight = (int) ((double) histogram[i] / maxFrequency * histHeight);
			g.drawLine(i, histHeight, i, histHeight - barHeight);
		}
	}


	/**
 * ヒストグラムをCSVファイルに出力します。
 * @param filename 出力するCSVファイルの名前
 */
	public void exportHistogramToCSV(String filename) {
		try (FileWriter writer = new FileWriter(filename)) {
			// CSVのヘッダーを書き込む
			writer.append("Gray Value, Frequency\n");
			
			// ヒストグラムのデータを書き込む
			for (int i = 0; i < histogram.length; i++) {
				writer.append(String.valueOf(i)).append(",").append(String.valueOf(histogram[i])).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
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
	


}
