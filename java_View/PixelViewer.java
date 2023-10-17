/***
 *** 画像情報処理　第1回課題　課題
 ***   画像表示用のパネル
 ***   引数無しだとファイルダイアログボックスが最初に立ち上がります．
 ***/

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class PixelViewer extends JFrame {
	
	String appName = "Pixel Viewer";
	int width, height;
	PixelViewerPanel  panel;
	
	public PixelViewer() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle( appName );
		Container container = this.getContentPane();

		Image image = load();
		if ( image != null ){
			Insets insets = getInsets();
			width = image.getWidth(this) + insets.left + insets.right;
			height = image.getHeight(this) + insets.top + insets.bottom;
		} else {
			System.out.println("Error");
			System.exit(0);
		}
		setSize(width, height);
		panel = new PixelViewerPanel( image );
		
		container.add( panel );
	}
	
	public PixelViewer( Image img ){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle( appName );
		Container container = this.getContentPane();
		
		Image image = img;

		Insets insets = getInsets();
		width = image.getWidth(this) + insets.left + insets.right;
		height = image.getHeight(this) + insets.top + insets.bottom;

		setSize(width, height );
		PixelViewerPanel ip = new PixelViewerPanel( image );
		container.add( ip );
	}
	
	public Image load( ){
		Image image = null;
        FileDialog l = new FileDialog( this, "Load", FileDialog.LOAD );
        l.setModal( true );
        l.setVisible( true );
        if ( l.getFile() != null ){
			
            MediaTracker tracker = new MediaTracker( this );
			try {
				image = ImageIO.read(new File(l.getDirectory()+l.getFile()));
			} catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}
            tracker.addImage( image, 0 );
            try {
                tracker.waitForID( 0 );
            } catch( InterruptedException e ){ }
        }
        return image;
	}
	
	public static void main( String [] args ){
		PixelViewer f = new PixelViewer();
		f.setVisible( true );
	}
}


