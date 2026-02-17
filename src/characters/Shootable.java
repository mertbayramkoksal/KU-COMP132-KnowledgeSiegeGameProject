package characters;

import shotbox.ShotBox;

/**
 * An interface for game entities that can shoot.
 * 
 * Any class implementing this interface must provide an implementation
 * of the {@code shoot()} method, which returns a {@link ShotBox}
 * representing the projectile or knowledge shot by the entity.
 *
 * Used in enemy classes such as {@code SectionLeader}, {@code TeachingAssistants},
 * and {@code Professors}.
 */
public interface Shootable {

    /**
     * Fires a shot, which may be a question or informational box.
     *
     * @return A {@link ShotBox} object representing the shot, or {@code null} if no shot is produced.
     */
    ShotBox shoot();
}
