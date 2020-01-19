package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Racket 
{
	private double aX;
	private double aY;
	private Image aImage;
	
	public Racket(Image pImage)
	{
		this.aX = 0;
		this.aY = 0;
		this.aImage = pImage;
	}
	
	public void mX(double pX)
	{
		this.aX = pX;
	}
	
	public void mY(double pY)
	{
		this.aY = pY;
	}
	
	public double mX()
	{
		return this.aX;
	}
	
	public double mY()
	{
		return this.aY;
	}
	
	public void mMouseMove(MouseEvent e)
	{
		this.mOnMouseMoved(e);
	}
	
	private void mOnMouseMoved(MouseEvent e)
	{
		this.mX(e.getX());
		
	}
	
	public void mUpdate(long pDeltaTime)
	{
		
	}
	
	public void mDraw(GraphicsContext pGraphicsContext)
	{
		Canvas vCanvas = pGraphicsContext.getCanvas();
		if(this.mX() < 0.0 + (this.aImage.getWidth()/2))
		{
			this.mX(0.0 + (this.aImage.getWidth()/2));
		}
		if(this.mX() > vCanvas.getWidth() - (this.aImage.getWidth()/2))
		{
			this.mX(vCanvas.getWidth() - (this.aImage.getWidth()/2));
		}
		pGraphicsContext.drawImage( this.aImage, this.aX - (this.aImage.getWidth() / 2), this.aY - (this.aImage.getHeight() / 2));
	}
	
	public double mWidth()
	{
		return this.aImage.getWidth();
	}
	
	public double mHeight()
	{
		return this.aImage.getHeight();
	}
}
