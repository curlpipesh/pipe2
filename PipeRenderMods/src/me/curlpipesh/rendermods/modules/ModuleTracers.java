package me.curlpipesh.rendermods.modules;

import me.curlpipesh.pipe.event.events.Render3D;
import me.curlpipesh.pipe.plugin.Plugin;
import me.curlpipesh.pipe.plugin.module.ToggleModule;
import me.curlpipesh.pipe.plugin.router.Route;
import me.curlpipesh.pipe.util.GLRenderer;
import me.curlpipesh.pipe.util.Keybind;
import me.curlpipesh.pipe.util.Vec3;
import me.curlpipesh.pipe.util.helpers.Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * @author audrey
 * @since 10/6/15.
 */
public class ModuleTracers extends ToggleModule {
    private final Vec3 offset = new Vec3(0, 1.62D, 0);
    
    public ModuleTracers(Plugin plugin) {
        super(plugin, "Tracers", "Draws pretty lines from here to there");
    }
    
    @Override
    public void registerRoutes() {
        registerRoute(new Route<Render3D>(this) {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void route(Render3D render3D) {
                // Sneak bug fix
                offset.y(Helper.isEntitySneaking(Helper.getPlayer()) ? 1.54D : 1.62D);
                int count = 0;
                //Vec3 p = Helper.getEntityVec(Helper.getPlayer());
                Vec3 prev = Helper.getEntityPrevVec(Helper.getPlayer());
                Vec3 cur = Helper.getEntityVec(Helper.getPlayer());
                Vec3 p = new Vec3(
                        prev.x() + ((cur.x() - prev.x()) * render3D.getPartialTickTime()),
                        prev.y() + ((cur.y() - prev.y()) * render3D.getPartialTickTime()),
                        prev.z() + ((cur.z() - prev.z()) * render3D.getPartialTickTime())
                );
                GLRenderer.pre();
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                for(Object o : Helper.getLoadedEntities()) {
                    if(!o.equals(Helper.getPlayer())) {
                        if(Helper.isEntityLiving(o) || Helper.isEntityPlayer(o)) {
                            boolean isPlayer = Helper.isEntityPlayer(o);
                            Vec3 e = Helper.getEntityVec(o);
                            if(e != null) {
                                e.sub(p);
                                GLRenderer.drawLine(offset, e,
                                        Helper.isEntityAnimal(o) ? 0x7700FF00 :
                                                Helper.isEntityMonster(o) ? 0x77FF0000 :
                                                        Helper.isEntityPlayer(o) ? 0xFFFF5555 : 0x770000FF, 2.235F);
                                Vec3 v = Helper.getEntityVec(o);
                                Vec3 v2 = Helper.getEntityVec(o);
                                v.sub(p).sub(new Vec3(0.5D, 0.5D, 0.5D));
                                v2.add(Vec3.unit()).sub(p).addY(1D);
                                GLRenderer.drawBoxFromPoints(v, v2,
                                        (Helper.isEntityAnimal(o) ? 0x7700FF00 :
                                                Helper.isEntityMonster(o) ? 0x77FF0000 :
                                                        Helper.isEntityPlayer(o) ? 0xFFFF5555 : 0x770000FF)
                                                & (isPlayer ? 0x77FFFFFF : 0x34FFFFFF));
                                ++count;
                            }
                        }
                    }
                }
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GLRenderer.post();
                setStatus(count > 0 ? "§a" + count : "§cNot rendering");
            }
        });
    }
    
    @Override
    public void init() {
        setKeybind(new Keybind(Keyboard.KEY_M));
    }
}
