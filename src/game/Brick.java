package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick 
{
	private double aX;
	private double aY;
	private Image aImage;
	private boolean aIsBroken;
	
	public Brick(Image pImage)
	{
		this.aX = 0.0;
		this.aY = 0.0;
		this.aImage = pImage;
		this.aIsBroken = false;
	}
	
	public double mX()
	{
		return this.aX;
	}
	
	public void mX(double pX)
	{
		this.aX = pX;
	}
	
	public double mY()
	{
		return this.aY;
	}
	
	public void mY(double pY)
	{
		this.aY = pY;
	}
	
	public double mWidth()
	{
		return this.aImage.getWidth();
	}
	
	public double mHeight()
	{
		return this.aImage.getHeight();
	}
	
	public boolean mIsBroken()
	{
		return this.aIsBroken;
	}
	
	public void mIsBroken(boolean pIsBroken)
	{
		this.aIsBroken = pIsBroken;
	}
	
	public void mDraw(GraphicsContext pGraphicsContext)
	{
		if(!this.aIsBroken)
		{
			pGraphicsContext.drawImage(this.aImage, this.aX, this.aY);
		}
	}
}
