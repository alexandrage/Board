package board;

import java.util.Iterator;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WG {
	private static RegionQuery rq = Main.wg.getRegionContainer().createQuery();

	public static String set(Player p, String input) {
		String rg = "_";
		ApplicableRegionSet set = rq.getApplicableRegions(p.getLocation());
		if (set.getRegions().size() > 0) {
			Iterator<?> iter = set.iterator();
			if (iter.hasNext()) {
				ProtectedRegion region = (ProtectedRegion)iter.next();
				rg = region.getId();
			}
		}
		return input.replace("{region}", rg);
	}
}