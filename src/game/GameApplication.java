package game;

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
	private long aNanoTime;
	private double aX;
	private double aY;
	private long aFrameNanoTime;
	private Racket aRacket;
	
	@Override
	public void start(Stage pStage) throws Exception 
	{
	    this.aRacket = new Racket(new Image("Racket.png"));
		pStage.setTitle("JavaFX : Casse-Briques");
		this.aGroup = new Group();
	    this.aScene = new Scene(this.aGroup);
	    pStage.setScene(this.aScene);
	    this.aCanvas = new Canvas( 800, 600 );
	    this.aGroup.getChildren().add(this.aCanvas);
	    this.aGraphicsContext = this.aCanvas.getGraphicsContext2D();
	    this.aNanoTime = System.nanoTime();
	    this.aFrameNanoTime = this.aNanoTime;
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
	    new AnimationTimer()
	    {
	    	public void handle(long pCurrentNanoTime)
	    	{
	    		mUpdate(pCurrentNanoTime);
	    	}
	    }.start();	    
		pStage.show();
	}
	
	public void mUpdate(long pCurrentNanoTime)
    {
		double vNanoTimePerSeconds = 1000000000.0;
		double vFPS = 120;
		double vNanoTimePerFPS = vNanoTimePerSeconds / vFPS;

        double t = (pCurrentNanoTime - this.aNanoTime) / 1000000000.0; 

        //this.aX = ;
        
        this.aRacket.mY(560);
        this.aRacket.mUpdate(pCurrentNanoTime);
        if(pCurrentNanoTime - this.aFrameNanoTime > vNanoTimePerFPS)
        {
        	this.aGraphicsContext.setFill(Color.BLACK);
        	this.aGraphicsContext.fillRect(0.0, 0.0, this.aCanvas.getWidth(), this.aCanvas.getHeight());
        	this.mDrawFPS(pCurrentNanoTime);
        	this.mDraw();
        	this.aFrameNanoTime = pCurrentNanoTime;
        }
    }
	
	public void mDraw()
	{
	    AnimatedImage ufo = new AnimatedImage();
        Image[] imageArray = new Image[6];
        imageArray[0] = new Image( "Blue_brick.png" );
    	imageArray[1] = new Image( "Green_brick.png" );
    	imageArray[2] = new Image( "Lightblue_brick.png" );
    	imageArray[3] = new Image( "Metal_brick.png" );
    	imageArray[4] = new Image( "Orange_brick.png" );
    	imageArray[5] = new Image( "Pink_brick.png" );
        	
        ufo.frames = imageArray;
        ufo.duration = 0.100;
	    this.aRacket.mDraw(this.aGraphicsContext);
	}
	
	private void mDrawFPS(long pCurrentNanoTime)
	{
		double vNanoTimePerSeconds = 1000000000.0;
		double vFPS = 120;
		double vNanoTimePerFPS = vNanoTimePerSeconds / vFPS;
		Font vFont = Font.font( "Times New Roman", FontWeight.BOLD, 14 );
		this.mDrawText(10.0, 20.0, vFont, "FPS: " + (pCurrentNanoTime - this.aFrameNanoTime), 0.0, Color.GREEN, Color.BLACK);
		this.mDrawText(10.0, 40.0, vFont, "Floor: " + (vNanoTimePerFPS), 0.0, Color.GREEN, Color.BLACK);
	}
	
	private void mOnMouseMoved(MouseEvent e)
	{
		this.aRacket.mMouseMove(e);
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
