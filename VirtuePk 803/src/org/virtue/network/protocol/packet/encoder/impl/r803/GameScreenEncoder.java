package org.virtue.network.protocol.packet.encoder.impl.r803;

import org.virtue.config.OutgoingOpcodes;
import org.virtue.network.protocol.packet.RS3PacketBuilder;
import org.virtue.network.protocol.packet.encoder.PacketEncoder;
import org.virtue.utility.DisplayMode;

/**
 * @author Virtue Development Team 2014 (c).
 * @since Apr 8, 2014
 */
public class GameScreenEncoder implements PacketEncoder<DisplayMode> {

	@Override
	public RS3PacketBuilder buildPacket(DisplayMode node) {
		RS3PacketBuilder buffer = new RS3PacketBuilder();
		buffer.putPacket(OutgoingOpcodes.WINDOW_PANE_PACKET);
		buffer.putByteC(0);		
		buffer.putLEInt(0);
		buffer.putLEShortA(node.getScreenId());
		buffer.putIntV2(0);
		buffer.putIntV2(0);
		buffer.putLEInt(0);
		return buffer;
	}
}
