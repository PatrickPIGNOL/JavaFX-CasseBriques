package game;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameApplication extends Application
{
	private Stage aStage;
	private Scene aScene;
	private Group aGroup;
	private Canvas aCanvas;
	private GraphicsContext aGraphicsContext;
	private double aFPS;
	private double aFPSTime;
	private double aFrameCount;
	private double aNanoTime;
	private double aTime;
	private Racket aRacket;
	private Ball aBall;
	private List<List<Brick>> aBricks;
	
	@Override
	public void start(Stage pStage) throws Exception 
	{
		this.aStage = pStage;
	    this.aStage.setTitle("JavaFX : Casse-Briques");	    
		this.aGroup = new Group();
	    this.aScene = new Scene(this.aGroup);
	    this.aStage.setScene(this.aScene);
	    this.aCanvas = new Canvas( 800, 600 );
	    this.aGroup.getChildren().add(this.aCanvas);
	    this.aGraphicsContext = this.aCanvas.getGraphicsContext2D();
	    this.aNanoTime = System.nanoTime();
	    this.aFrameCount = this.aNanoTime;
	    this.aScene.setOnKeyPressed
	    (
	    	new EventHandler<KeyEvent>()
	        {
	    		public void handle(KeyEvent e)
	            {
	                 
	            }
	        }
	    );
	    this.aScene.setOnKeyReleased
	    (
	    	new EventHandler<KeyEvent>()
	    	{
	    		public void handle(KeyEvent e)
	    		{
	    			
	    		}
	    	}
	    );
	    this.aScene.setOnMouseMoved
	    (
	    	new EventHandler<MouseEvent>()
	    	{
	    		public void handle(MouseEvent e)
	    		{
	    			mOnMouseMoved(e);
	            }
	    	}
	    );
	    this.aScene.setOnMouseClicked
	    (
	    	new EventHandler<MouseEvent>()
	    	{
	    		public void handle(MouseEvent e)
	    		{
	    			mOnMouseClicked(e);
	            }
	    	}
	    );

	    this.aStage.setResizable(false);
	    this.mLoad();
	    
	    new AnimationTimer()
	    {
	    	public void handle(long pCurrentNanoTime)
	    	{
	    		double vCurrentNanoTime = Double.valueOf(pCurrentNanoTime);
	    		mLoop(vCurrentNanoTime);
	    	}
	    }.start();	    
		this.aStage.show();
	}
	
	private void mLoop(double pCurrentNanoTime)
	{		
		double vDeltaTime = pCurrentNanoTime - this.aNanoTime;
		this.aTime += vDeltaTime;
		this.aFPSTime += vDeltaTime;
		
		double vNanoTimePerSeconds = 1000000000.0;
		double vMiliTimePerSeconds = vNanoTimePerSeconds / 1000.0;
		double vFPS = 120;
		double vNanoTimePerFPS = vNanoTimePerSeconds / vFPS;
		this.mUpdate(vDeltaTime/vMiliTimePerSeconds);
        // limit acceleration card overheat and CPU usage ...
		if(this.aFPSTime > vNanoTimePerFPS)
        {
			this.aFrameCount++;
	    	this.aGraphicsContext.setFill(Color.BLACK);
	    	this.aGraphicsContext.fillRect(0.0, 0.0, this.aCanvas.getWidth(), this.aCanvas.getHeight());
	    	this.mDraw(this.aGraphicsContext);
			//this.mDrawFPS(vDeltaTime);
			this.aFPSTime = 0.0;
		}	
		
        if(this.aTime > vNanoTimePerSeconds)
		{			
        	this.aFPS = this.aFrameCount;
			this.aFrameCount = 0.0;
			this.aTime = 0.0;
		}
		
		this.aNanoTime = pCurrentNanoTime;
	}
	
	public void mLoad()
	{
		this.aRacket = new Racket(new Image("Racket.png"));
		this.aRacket.mY(560);
	    this.aBall = new Ball(new Image("Ball.png"));
	    this.mStart();
	}
	
	public void mStart()
	{
	    this.aBall.mIsSticky(true);
		this.aBall.mSpeedX(0.5);
		this.aBall.mSpeedY(-0.5);
		if(this.aBricks != null)
		{
			for(List<Brick> vBricks : this.aBricks)
			{
				if(vBricks != null)
				{
					vBricks.clear();
				}
			}
			this.aBricks.clear();
		}
		else
		{
			this.aBricks = new ArrayList<List<Brick>>();			
		}
		Brick vBrick = new Brick(new Image("Blue_brick.png"));
		double vRatio = this.aCanvas.getWidth() / (vBrick.mWidth() + 2);
		for(int vYIndex = 0; vYIndex < 6; vYIndex++)
		{
			this.aBricks.add(new ArrayList<Brick>());
			for(int vXIndex = 0; vXIndex < vRatio; vXIndex++)
			{
				Brick vArrayBrick = new Brick(new Image("Blue_brick.png"));
				vArrayBrick.mX(1 + (vXIndex * (vArrayBrick.mWidth() + 2)));
				vArrayBrick.mY(1 + (vYIndex * (vArrayBrick.mHeight() + 2)));
				this.aBricks.get(vYIndex).add(vArrayBrick);				
			}
		}
	}
	
	public void mUpdate(double pDeltaTime)
    {				
		this.mOnUpdate(pDeltaTime);        
    }
	
	private void mOnUpdate(double pDeltaTime)
	{
		double vHalfBallWidth = this.aBall.mWidth() / 2.0;
		double vHalfBallHeight = this.aBall.mHeight() / 2.0;
		double vHalfRacketWidth = this.aRacket.mWidth() / 2.0;
		double vHalfRacketHeight = this.aRacket.mHeight() / 2.0;
		
        this.aRacket.mUpdate(pDeltaTime);
        this.aBall.mUpdate(pDeltaTime);
        
		if(this.aRacket.mX() < (this.aRacket.mWidth()/2))
		{
			this.aRacket.mX(vHalfRacketWidth);
		}
		if(this.aRacket.mX() > this.aCanvas.getWidth() - vHalfRacketWidth)
		{
			this.aRacket.mX(this.aCanvas.getWidth() - vHalfRacketWidth);
		}
        if(this.aBall.mIsSticky())
        {
        	this.aBall.mX(this.aRacket.mX());
        	this.aBall.mY(this.aRacket.mY() - vHalfRacketHeight - vHalfBallHeight);
        }
        else
        {
    		this.aBall.mX(this.aBall.mX() + this.aBall.mSpeedX() * pDeltaTime);
    		this.aBall.mY(this.aBall.mY() + this.aBall.mSpeedY() * pDeltaTime);
        }
        
        // too right
		if( this.aBall.mX() + vHalfBallWidth > this.aCanvas.getWidth() )
		{
			this.aBall.mX(this.aCanvas.getWidth() - vHalfBallWidth);
			this.aBall.mSpeedX(-this.aBall.mSpeedX()); 
		}
        // too left
		if( this.aBall.mX() - vHalfBallWidth < 0 )
		{
			this.aBall.mX(vHalfBallWidth);
			this.aBall.mSpeedX(-this.aBall.mSpeedX()); 
		}
		// too top
		if(this.aBall.mY() - vHalfBallHeight < 0)
		{
			this.aBall.mY(vHalfBallHeight);
			this.aBall.mSpeedY(-this.aBall.mSpeedY());
		}    
		if(this.aBall.mY() - vHalfBallHeight > this.aCanvas.getHeight())
		{
			this.mStart();
		}
		//collision with racket
		if
		(
			(this.aBall.mX() + vHalfBallWidth > this.aRacket.mX() - vHalfRacketWidth)
			&&
			(this.aBall.mX() - vHalfBallWidth < this.aRacket.mX() + vHalfRacketWidth)
			&&
			(this.aBall.mY() + vHalfBallHeight > this.aRacket.mY() - vHalfRacketHeight)
			&&
			(this.aBall.mY() - vHalfBallHeight < this.aRacket.mY() + vHalfRacketHeight)
		)
		{
			if(this.aBall.mY() < this.aRacket.mY() - vHalfRacketHeight)
			{
				this.aBall.mY(this.aRacket.mY() - vHalfRacketHeight - vHalfBallHeight);
				this.aBall.mSpeedY(-this.aBall.mSpeedY());
			}
			if
			(
				(this.aBall.mX() < this.aRacket.mX() - vHalfRacketWidth)
				||
				(this.aBall.mX() > this.aRacket.mX() + vHalfRacketWidth)
			)
			{
				this.aBall.mSpeedX(-this.aBall.mSpeedX());
			}
		}
		boolean vIsEmpty = true;
		// collision with bricks 
		for(List<Brick> vBricks : this.aBricks)
		{
			for(Brick vBrick : vBricks)
			{
				if(!vBrick.mIsBroken())
				{
					vIsEmpty = false;
					if
					(
						(this.aBall.mX() + vHalfBallWidth > vBrick.mX())
						&&
						(this.aBall.mX() - vHalfBallWidth < vBrick.mX() + vBrick.mWidth())
						&&
						(this.aBall.mY() + vHalfBallHeight > vBrick.mY())
						&&
						(this.aBall.mY() - vHalfBallHeight < vBrick.mY() + vBrick.mHeight())
					)
					{
						//collision
						vBrick.mIsBroken(true);
						if
						(
							(this.aBall.mX() < vBrick.mX())
							||
							(this.aBall.mX() > vBrick.mX() + vBrick.mWidth())
						)
						{
							this.aBall.mSpeedX(-this.aBall.mSpeedX());
							if(this.aBall.mX() < vBrick.mX())
							{
								this.aBall.mX(vBrick.mX() - vHalfBallWidth);
							}
							if(this.aBall.mX() > vBrick.mX() + vBrick.mWidth())
							{
								this.aBall.mX(vBrick.mX() + vBrick.mWidth() + vHalfBallWidth);
							}
						}
						if
						(
							(this.aBall.mY() < vBrick.mY())
							||
							(this.aBall.mY() > vBrick.mY() + vBrick.mHeight())
						)
						{
							this.aBall.mSpeedY(-this.aBall.mSpeedY());
							if(this.aBall.mY() < vBrick.mY())
							{
								this.aBall.mY(vBrick.mY() - vHalfBallHeight);
							}
							if(this.aBall.mY() > vBrick.mY() + vBrick.mHeight())
							{
								this.aBall.mY(vBrick.mY() + vBrick.mHeight() + vHalfBallHeight);
							}
						}
					}
				}
			}
		}
		if(vIsEmpty)
		{
			this.mStart();
		}
	}
	
	public void mDraw(GraphicsContext pGraphicsContext)
	{
	    this.aRacket.mDraw(pGraphicsContext);
	    this.aBall.mDraw(pGraphicsContext);
	    for(List<Brick> vBricks : this.aBricks)
	    {
	    	for(Brick vBrick : vBricks)
	    	{
	    		vBrick.mDraw(pGraphicsContext);
	    	}
	    }
	}
	
	private void mDrawFPS(double pDeltaTime)
	{
		double vNanoTimePerSeconds = 1000000000.0;
		double vFPS = 240;
		double vNanoTimePerFPS = vNanoTimePerSeconds / vFPS;
		
		Font vFont = Font.font( "Times New Roman", FontWeight.BOLD, 14 );
		this.mDrawText(10.0, 20.0, vFont, "Delta: " + pDeltaTime, 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 40.0, vFont, "FPS: " + this.aFPS, 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 60.0, vFont, "FPStime: " + this.aFPSTime / vNanoTimePerSeconds, 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 80.0, vFont, "n/FPS: " + vNanoTimePerFPS / vNanoTimePerSeconds, 0.0, Color.GREEN, Color.BLACK);
	}
	
	private void mOnMouseMoved(MouseEvent e)
	{
		this.aRacket.mMouseMove(e);
	}
	
	private void mOnMouseClicked(MouseEvent e)
	{
		this.aBall.mMouseClick(e);
	}
	
	private void mDrawText(double pX, double pY, Font pFont, String pText, double pLineWidth, Paint pFillColor, Paint pStrokeColor)
	{
		this.aGraphicsContext.setFill( pFillColor );
		this.aGraphicsContext.setFont( pFont );
	    this.aGraphicsContext.fillText( pText, pX, pY );
		if(pLineWidth > 0.0)
		{
			this.aGraphicsContext.setStroke(pStrokeColor);
			this.aGraphicsContext.setLineWidth(pLineWidth);
		    this.aGraphicsContext.strokeText( pText, pX, pY );
		}
	}
}
