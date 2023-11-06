/***
 *** 画像情報処理　第4回課題　課題 Laplacian Filter
 *** 
 ***
*/
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;


public class LaplacianFilter extends JFrame {
	
	String appName = "LaplacianFilter";
	int width, height;
	ImagePanel  panel;
	
	public LaplacianFilter() {
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
		panel = new ImagePanel( image );
		
		container.add( panel , BorderLayout.CENTER);
	}
	
	public LaplacianFilter( Image img ){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle( appName );
		Container container = this.getContentPane();
		
		Image image = img;

		Insets insets = getInsets();
		width = image.getWidth(this) + insets.left + insets.right;
		height = image.getHeight(this) + insets.top + insets.bottom;

		setSize(width, height );
		ImagePanel ip = new ImagePanel( image );
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
	
	public void stateChanged(ChangeEvent e) {
		// label.setText("値：" + slider.getValue());
  	}
	
	public static void main( String [] args ){
		LaplacianFilter f = new LaplacianFilter();
		f.setVisible( true );
	}
}

class ImagePanel extends JPanel {
	
	int width, height;
	Image image = null;
	int mouseX, mouseY;
	
	BufferedImage bufImage;
	BufferedImage laplacianImage;
	
	// 空間フィルタ用のカーネル
	double kernel[][] = {{1.0, 1.0, 1.0},
						 {1.0,-8.0, 1.0},
						 {1.0, 1.0, 1.0}};
	
	public ImagePanel( Image image ){
		this.image = image;
		width = image.getWidth( this );
		height = image.getHeight( this );
		this.setSize( width , height );
		
		bufImage = createBufferedImage( image );
		laplacianImage = createBufferedImage( image );
		grayConversion();
		filtering();
		repaint();
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
	
	public void grayConversion(){

		for (int y = 0; y < height; y++ ){
			for ( int x = 0; x < width; x++ ){
				int color = bufImage.getRGB( x, y );
				double r = (double)getRed( color );
				double g = (double)getGreen( color ); 
				double b = (double)getBlue( color );
				int gray = (int)(0.299*r + 0.587 * g + 0.114 *b);
				bufImage.setRGB(x,y, 255<<24 | gray<<16 | gray<<8 | gray);
			}
		}
	}

	
	public void filtering(){
		
		for (int h = 1; h < height-1; h++ ){
			for ( int w = 1; w < width-1; w++ ){
				double value = 0.0;
				for (int ky = -1; ky < 2; ky++ ){
					for (int kx = -1; kx < 2; kx++ ){
						double color = getRed( bufImage.getRGB(w+kx, h+ky) );
						value += kernel[kx+1][ky+1] * color;
					}
				}
				int gray = (int)value;
				if (gray < 0 ) gray = 0;
				if (gray > 255 ) gray = 256;
				laplacianImage.setRGB(w,h, 255<<24 | gray<<16 | gray<<8 | gray);
				
			}
		}
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
	
	public void paint( Graphics g ){
		g.drawImage( (Image)laplacianImage , 0 , 0 , this );
	}
}
