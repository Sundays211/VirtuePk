package org.virtue.network.protocol.render.update;

import org.virtue.game.node.entity.player.Player;
import org.virtue.network.protocol.packet.RS3PacketBuilder;

/**
 * @author Taylor
 * @version 1.0
 */
public abstract class UpdateBlock {
	
	/**
	 * Returns the mask flag used to descript what type of {@link UpdateBlock} this is.
	 * @return The mask flag.
	 */
	public abstract int getMask();
	
	/**
	 * Returns the position at which in the update buffer this
	 * {@link UpdateBlock} will be encoded at.
	 * @return The byte position of this {@link UpdateBlock}.
	 */
	public abstract int getBlockPosition();
	
	/**
	 * Called when this {@link UpdateBlock} should be appended to an update buffer.
	 * @param buffer The update buffer to write data to.
	 * @param player The player being updated.
	 * @param ref The block reference.
	 */
	public abstract void appendToUpdateBlock(RS3PacketBuilder buffer, Player player);
}
