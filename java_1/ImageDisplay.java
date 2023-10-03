// 必要なライブラリをインポートします。
import javax.swing.*;
import java.awt.*;

public class ImageDisplay {

    public static void main(String[] args) {
        // 新しいウィンドウを作成。
        JFrame frame = new JFrame("Image Display");
        // ウィンドウを閉じたときの挙動を設定。
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ウィンドウのサイズを設定。
        frame.setSize(800, 800);

        // 指定された画像を表示するための新しいパネルを作成。
        ImagePanel panel = new ImagePanel("pokemon_rowlet.png");
        // パネルをウィンドウに追加。
        frame.add(panel);

        // ウィンドウを表示。
        frame.setVisible(true);
    }
}

class ImagePanel extends JPanel {
    private Image img; // 画像を保持するための変数

    // 画像のパスを受け取るコンストラクタ
    public ImagePanel(String imgPath) {
        this(new ImageIcon(imgPath).getImage());
    }

    // Imageオブジェクトを受け取るコンストラクタ
    public ImagePanel(Image img) {
        this.img = img;
    }

    //描画処理用のメソッド
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 基本の描画を行うための呼び出し
        // 画像をパネル上に描画。
        g.drawImage(img, 0, 0, null);
    }
}
