package org.virtue.network.protocol.packet.encoder.impl.r803;

import org.virtue.config.OutgoingOpcodes;
import org.virtue.game.social.OnlineStatus;
import org.virtue.network.protocol.packet.RS3PacketBuilder;
import org.virtue.network.protocol.packet.encoder.PacketEncoder;

public class OnlineStatusEncoder implements PacketEncoder<OnlineStatus> {

	@Override
	public RS3PacketBuilder buildPacket(OnlineStatus node) {
		RS3PacketBuilder buffer = new RS3PacketBuilder();
		buffer.putPacket(OutgoingOpcodes.ONLINE_STATUS_PACKET);
		buffer.put(node.getStatusCode());
		return buffer;
	}

}