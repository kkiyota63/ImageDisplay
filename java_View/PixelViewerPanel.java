/***
 *** 画像情報処理　第1回課題　課題
 ***   画像表示用のパネル
 ***   引数無しだとファイルダイアログボックスが最初に立ち上がります．
 ***/

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

public class PixelViewerPanel extends JPanel implements MouseListener {
	
	int width, height;
	Image image = null;
	int mouseX, mouseY;
	
	BufferedImage bufImage;
	
	int fontSize = 18;
    Font font = new Font( "TimesRoman" , Font.ITALIC , fontSize );
	String string;
	
	public PixelViewerPanel( Image image ){
		this.image = image;
		width = image.getWidth( this );
		height = image.getHeight( this );
		this.setSize( width , height );
		
		bufImage = createBufferedImage( image );
		
		string = "";
		addMouseListener( this );
	}
	
	public BufferedImage createBufferedImage(Image img) {
		BufferedImage bimg = 
			new BufferedImage( img.getWidth(null),
			    		    img.getHeight(null), 
					     BufferedImage.TYPE_INT_RGB);

		Graphics g = bimg.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		return bimg;
	}
	
	public int getRed( int color ){
		return color >> 16 & 0xff;
	}

	public int getGreen( int color ){
		return color >> 8 & 0xff;
	}

	public int getBlue( int color ){
		return color & 0xff;
	}
	
	public void mouseClicked(MouseEvent e){
		Point point = e.getPoint();
		mouseX = point.x;
		mouseY = point.y;
		int pixelColor = bufImage.getRGB( mouseX, mouseY );
		
		int r = getRed( pixelColor );
		int g = getGreen( pixelColor );
		int b = getBlue( pixelColor );
		
		float[] hsb =  Color.RGBtoHSB(r , g, b, null );
		
		string = "( " + String.valueOf( mouseX )  
			+ " , " + String.valueOf( mouseY ) + " )" + " HSB = "
			 + String.valueOf( hsb[0] ) + ", " + String.valueOf( hsb[1] ) + ", " + String.valueOf( hsb[2] );
		repaint();
		// System.out.println( getBlue( pixelColor ) );
		
	}
	// その他のマウスイベント（未使用）
	public void mouseEntered(MouseEvent e){ }
	public void mouseExited(MouseEvent e){  }
	public void mousePressed(MouseEvent e){  }
	public void mouseReleased(MouseEvent e){ }
	
	public void paint( Graphics g ){
		
		g.drawImage( image , 0 , 0 , this );
		if (string != "" ){
			g.setFont( font );
	        	g.drawString(string , 10 , fontSize+10 ); 
		}
	}
}

