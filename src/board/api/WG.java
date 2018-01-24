package board.api;

import java.util.Iterator;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import board.Main;

public class WG {
	private static RegionQuery rq = Main.wg.getRegionContainer().createQuery();

	public static String set(Player p, String input) {
		String rg = "&6__ global__";
		ApplicableRegionSet set = rq.getApplicableRegions(p.getLocation());
		if (set.getRegions().size() > 0) {
			Iterator<?> iter = set.iterator();
			if (iter.hasNext()) {
				ProtectedRegion region = (ProtectedRegion) iter.next();
				if (Main.wg.canBuild(p, p.getLocation())) {
					rg = "&2" + region.getId();
				} else {
					rg = "&4" + region.getId();
				}
			}
		}
		return input.replace("{region}", rg);
	}
}