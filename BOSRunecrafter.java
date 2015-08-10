
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.Script;

import constants.ItemConstants;
import constants.NPCConstants;
import constants.ObjectContants;
import constants.PathConstants;
import tasks.*;


@Script.Manifest(name="BOS Runecrafter", description="Only supports air rune crafting for the moment", properties = "client=4;")
public class BOSRunecrafter extends PollingScript<ClientContext> implements PaintListener{
	
	
	 private final long initTime = System.currentTimeMillis();
	 private final int startXP = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING);
	 private String currentStatus = "Starting up";
	
	@SuppressWarnings("rawtypes")
	private List<Task> taskList = new ArrayList<Task>();
	 
	@Override
    public void start() {
	//airRunes
		taskList.addAll(Arrays.asList(new WalkToBank(ctx,ItemConstants.ESSENCE_NORMAL,PathConstants.AIRRUNEPATH, NPCConstants.BANKERIDS)
									, new Banking(ctx, ItemConstants.ESSENCE_NORMAL, NPCConstants.BANKERIDS)
									, new WalkToAltar(ctx, ItemConstants.ESSENCE_NORMAL,PathConstants.AIRRUNEPATH, ObjectContants.ALTER_AIR)
									, new EnterAltar(ctx, ItemConstants.ESSENCE_NORMAL, ObjectContants.ALTER_AIR_ENTRANCE)
									, new CraftRunes(ctx, ItemConstants.ESSENCE_NORMAL, ObjectContants.ALTER_AIR)
									, new LeaveAltar(ctx, ItemConstants.ESSENCE_NORMAL, ObjectContants.PORTAL_AIR)
									, new CheckForRun(ctx)));
	/*	taskList.addAll(Arrays.asList(new WalkToBank(ctx,ItemConstants.ESSENCE_PURE,PathConstants.SHOPTONATURE, AreaConstants.SHOP_NATURE)
		, new TradeShop(ctx, ItemConstants.ESSENCE_PURE, NPCConstants.SHOPNATURRUNE)
		, new WalkToAltar(ctx, ItemConstants.ESSENCE_PURE,PathConstants.SHOPTONATURE, AreaConstants.ALTAR_NATURE_OUTSIDE)
		, new EnterAltar(ctx, ItemConstants.ESSENCE_PURE, ObjectContants.ALTER_NATURE_ENTRANCE, AreaConstants.ALTAR_NATURE_OUTSIDE)
		, new CraftRunes(ctx, ItemConstants.ESSENCE_PURE, ObjectContants.ALTER_NATURE, AreaConstants.ALTAR_NATURE_INSIDE)
		, new LeaveAltar(ctx, ItemConstants.ESSENCE_PURE, ObjectContants.PORTAL_NATURE, AreaConstants.ALTAR_NATURE_INSIDE)
		, new CheckForRun(ctx)));*/
    }    

	@Override
	public void poll() {
        for (@SuppressWarnings("rawtypes") Task task : taskList) {
            if (task.activate()) {
            	currentStatus = task.getState();
                task.execute();
            }
        }
	}

	@Override
	public void repaint(Graphics graphics) {
		
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
        graphics2D.setColor(new Color(79, 215, 197, 150));
        graphics2D.fillRect(10, 200, 140, 135);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 0, 12));

        graphics2D.drawString("BOS Runecrafter", 15, 215);
        graphics2D.drawString("Run time: " + theTime, 15, 245);
        graphics2D.drawString("Current Lvl: " + currentLvl, 15, 265);
        graphics2D.drawString("Xp gained: " + gainedXP, 15, 285);
        graphics2D.drawString("Xp per hour: " + perHourXp, 15, 305);
        graphics2D.drawString("Status: " + currentStatus, 15, 325);

        graphics2D.setColor(Color.ORANGE);
        final Point p = ctx.input.getLocation();
        graphics2D.drawLine(p.x - 5, p.y - 5, p.x + 5, p.y + 5);
        graphics2D.drawLine(p.x - 5, p.y + 5, p.x + 5, p.y - 5);
        
	}

}
