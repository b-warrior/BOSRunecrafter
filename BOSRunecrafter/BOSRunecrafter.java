
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.Script;

import Frame.StartupFrame;
import tasks.*;


@Script.Manifest(name="BOS Runecrafter", description="Supports air and fire altar. Only tiara support atm.", properties = "client=4;topic=1278331;")
public class BOSRunecrafter extends PollingScript<ClientContext> implements PaintListener{
	
	
	 private final long initTime = System.currentTimeMillis();
	 private final int startXP = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING);
	 private String currentStatus = "Starting up";
	

	private List<Task<?>> taskList = new ArrayList<Task<?>>();
	 
	@Override
    public void start() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartupFrame frame = new StartupFrame(ctx,taskList);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
    }    

	@Override
	public void poll() { 
        for (Task<?> task : taskList) {
            if (task.activate()) {
            	currentStatus = task.getState();
                task.execute();
            }
        }
	}

	@Override
	public void repaint(Graphics graphics) {
		
		Image background= getImage("http://i.imgur.com/t3gqM6R.png");
		graphics.drawImage(background, 2, 335, null);
		
		long currentTime = System.currentTimeMillis() - initTime;
		long currentTimeSec = (int)(currentTime / 1000);
		
		int hours = (int) (currentTime / 3600000);
        int minutes = (int) (currentTime / 60000 - hours * 60);
        int seconds = (int) (currentTime / 1000 - hours * 3600 - minutes * 60);
        
        String theTime = String.format("%02d:%02d:%02d", hours,minutes,seconds);
		
        int currentLvl = ctx.skills.level(Constants.SKILLS_RUNECRAFTING);
        int currentXP = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING);
        int gainedXP = currentXP - startXP; 
        int perHourXp = 0;
         
        if(currentTimeSec > 0 )
        	perHourXp = (int)( (gainedXP * 3600) /(currentTimeSec));

        Graphics2D graphics2D = (Graphics2D) graphics;
      //  graphics2D.setColor(new Color(79, 215, 197, 150));
      //  graphics2D.fillRect(10, 200, 140, 135);
        graphics2D.setColor(new Color(68, 70, 67));
        graphics2D.setFont(new Font("Razer Header Regular Oblique", 0, 12));

        graphics2D.drawString("v1.04", 425, 368);
        graphics2D.drawString("" + theTime, 345, 429);
        graphics2D.drawString("" + currentLvl, 345, 402);
        graphics2D.drawString("" + gainedXP, 198, 426);
        graphics2D.drawString("" + perHourXp, 198, 402);
        graphics2D.drawString("" + currentStatus, 198, 455);

        graphics2D.setColor(Color.ORANGE);
        final Point p = ctx.input.getLocation();
        graphics2D.drawLine(p.x - 5, p.y - 5, p.x + 5, p.y + 5);
        graphics2D.drawLine(p.x - 5, p.y + 5, p.x + 5, p.y - 5);
        
        
        
	}
	
	private Image getImage(String url) {
        try { return ImageIO.read(new URL(url)); } 
        catch(IOException e) { return null; }
      }

}
