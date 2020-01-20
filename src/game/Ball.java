package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Ball 
{
	private double aX;
	private double aY;
	private double aSpeedX;
	private double aSpeedY;
	private boolean aIsSticky;
	private Image aImage;
	
	public Ball(Image pImage)
	{
		this.aX = 0;
		this.aY = 0;
		this.aSpeedX = 0;
		this.aSpeedY = 0;
		this.aIsSticky = false;
		this.aImage = pImage;
	}

	public void mUpdate(double pDeltaTime)
	{
		this.mOnUpdate(pDeltaTime);
	}
	
	private void mOnUpdate(double pDeltaTime)
	{
		this.aX = this.aX + this.aSpeedX * pDeltaTime;
		this.aY = this.aY + this.aSpeedY * pDeltaTime;
	}
	
	public void mDraw(GraphicsContext pGraphicsContext)
	{
		//Canvas vCanvas = pGraphicsContext.getCanvas();
		pGraphicsContext.drawImage(this.aImage, this.aX - (this.aImage.getWidth()/2), this.aY - (this.aImage.getHeight()/2));
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
	
	public boolean mIsSticky()
	{
		return this.aIsSticky;
	}
	
	public void mIsSticky(boolean pIsSticky)
	{
		this.aIsSticky = pIsSticky;
	}
	
	public double mWidth()
	{
		return this.aImage.getWidth();
	}
	
	public double mHeight()
	{
		return this.aImage.getHeight();
	}
	
	public void mMouseClick(MouseEvent e)
	{
		this.mOnMouseClicked(e);
	}
	
	private void mOnMouseClicked(MouseEvent e)
	{
		if(this.aIsSticky)
		{
			this.aIsSticky = false;
			this.aSpeedX = 200;
			this.aSpeedY = -200;
		}
	}
	
	public void mSpeedX(double pSpeedX)
	{
		this.aSpeedX = pSpeedX;
	}
	
	public double mSpeedX()
	{
		return this.aSpeedX;
	}
	
	public void mSpeedY(double pSpeedY)
	{
		this.aSpeedY = pSpeedY;
	}
	
	public double mSpeedY()
	{
		return this.aSpeedY;
	}
}
