package org.virtue.game.logic.node.entity.player;

import java.util.EnumMap;

import org.virtue.Constants;
import org.virtue.game.config.ClientVarps;
import org.virtue.game.config.OutgoingOpcodes;
import org.virtue.game.logic.World;
import org.virtue.game.logic.content.skills.SkillManager;
import org.virtue.game.logic.message.ChatType;
import org.virtue.game.logic.node.entity.Entity;
import org.virtue.game.logic.node.entity.player.identity.Account;
import org.virtue.game.logic.node.entity.region.Tile;
import org.virtue.game.logic.node.entity.social.OnlineStatus;
import org.virtue.game.logic.node.interfaces.InterfaceManager;
import org.virtue.game.logic.node.interfaces.impl.Equipment;
import org.virtue.game.logic.node.interfaces.impl.Inventory;
import org.virtue.network.protocol.messages.ClientScriptVar;
import org.virtue.network.protocol.messages.EntityOptionMessage;
import org.virtue.network.protocol.messages.InterfaceMessage;
import org.virtue.network.protocol.messages.VarpMessage;
import org.virtue.network.protocol.packet.encoder.PacketDispatcher;
import org.virtue.network.protocol.packet.encoder.impl.EmptyPacketEncoder;
import org.virtue.network.protocol.packet.encoder.impl.GameScreenEncoder;
import org.virtue.network.protocol.packet.encoder.impl.MapSceneEncoder;
import org.virtue.network.protocol.packet.encoder.impl.NPCEncoder;
import org.virtue.network.protocol.packet.encoder.impl.OnlineStatusEncoder;
import org.virtue.network.protocol.packet.encoder.impl.PlayerEncoder;
import org.virtue.utility.DisplayMode;

/**
 * @author Taylor
 * @date Jan 13, 2014
 */
public class Player extends Entity {
	
	private EnumMap<PlayerOption, EntityOptionMessage> playerOptions = new EnumMap<PlayerOption, EntityOptionMessage>(PlayerOption.class);
	
	/**
	 * Represents this player's account.
	 */
	private Account account;
	
	/**
	 * Represents the viewport.
	 */
	private Viewport viewport;
	
	/**
	 * Represents the interface manager.
	 */
	private InterfaceManager interfaceManager;
	
	/**
	 * Represents the inventory.
	 */
	private Inventory inventory;
	
	/**
	 * Represents the equipment.
	 */
	private Equipment equipment;
	
	/**
	 * Represents the skill manager.
	 */
	private SkillManager skillManager;
	
	/**
	 * Represents the packet dispatcher.
	 */
	private PacketDispatcher packetDispatcher;
	
	/**
	 * Represents the type of message that the next message(s) will be
	 */
	private ChatType chatType;
	
	private boolean largeSceneView = false;
	
	private boolean exists = true;
	
	private boolean inWorld = false;
	
	/**
	 * Constructs a new {@code Player.java}.
	 * @param account The account
	 */
	public Player(Account account) {
		super();
		System.out.println("Creating player: "+account.getUsername().getName());
		this.account = account;
		tile = Constants.DEFAULT_LOCATION;
		lastTile = getTile();
		lastLoadedRegion = new Tile(lastTile);
		viewport = new Viewport(this);
		interfaceManager = new InterfaceManager(this);
		inventory = new Inventory(this);
		equipment = new Equipment(this);
		packetDispatcher = new PacketDispatcher(this);
		skillManager = new SkillManager(this);
	}

	@Override
	public void start() {
		inWorld = true;
		getUpdateArchive().getAppearance().packBlock();
		//System.out.println("Sending game information to player...");
		packetDispatcher.dispatchMessage("Welcome to " + Constants.NAME + ".");
		int[] varps = ClientVarps.getGameVarps();
		for (int i = 0; i < varps.length; i++) {
			int val = varps[i];
			if (val != 0) {
				packetDispatcher.dispatchVarp(new VarpMessage(i, val));
			}
		}
		interfaceManager.sendScreen();
		inventory.load();
		equipment.load();
		skillManager.sendAllSkills();
		packetDispatcher.dispatchRunEnergy(100);//Sends the current run energy level to the player
		account.getSession().getTransmitter().send(OnlineStatusEncoder.class, OnlineStatus.EVERYONE);
		account.getSession().getTransmitter().send(EmptyPacketEncoder.class, OutgoingOpcodes.UNLOCK_FRIENDS_LIST);

		getPacketDispatcher().dispatchInterface(new InterfaceMessage(1252, 65, 1477, true));//Treasure hunter pop-up thing
	}
	
	public void sendDefaultPlayerOptions () {
		EntityOptionMessage followOption = new EntityOptionMessage("Follow", 2, false, -1);
		playerOptions.put(PlayerOption.TWO, followOption);
		packetDispatcher.dispatchPlayerOption(followOption);
		
		EntityOptionMessage tradeOption = new EntityOptionMessage("Trade with", 4, false, -1);
		playerOptions.put(PlayerOption.FOUR, tradeOption);
		packetDispatcher.dispatchPlayerOption(tradeOption);
	}
	
	public boolean hasLargeSceneView () {
		return largeSceneView;
	}
	
	public void startLobby() {
		//started = true;
		account.getSession().getTransmitter().send(GameScreenEncoder.class, DisplayMode.LOBBY);
	}

	@Override
	public void destroy() {
		if (!exists) {
			return;
		}
		exists = false;
		if (World.getWorld().contains(getAccount().getUsername().getAccountName())) {
			World.getWorld().removePlayer(this); 
		}		
	}

	@Override
	public void onCycle() {
		getUpdateArchive().getMovement().process();
	}
	
	@Override
	public void loadMapRegion () {
		super.loadMapRegion();
		getAccount().getSession().getTransmitter().send(MapSceneEncoder.class, viewport);
	}

	public void sendLogout (boolean toLobby) {
		packetDispatcher.dispatchLogout(toLobby);
		destroy();
	}
	
	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	
	@Override
	public void update() {
		if (inWorld) {
			account.getSession().getTransmitter().send(PlayerEncoder.class, this);//Send player updates
			account.getSession().getTransmitter().send(NPCEncoder.class, this);//Send NPC updates
		}
	}
	
	@Override
	public void refreshOnDemand() {
		getUpdateArchive().reset();//Refresh update flags
	}

	@Override
	public boolean exists() {
		return exists;
	}
	
	public InterfaceManager getInterfaces () {
		return interfaceManager;
	}

	/**
	 * @return the viewport
	 */
	public Viewport getViewport() {
		return viewport;
	}

	/**
	 * @param viewport the viewport to set
	 */
	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}

	/**
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * Sets the type for the player's chat messages
	 * @param typeCode	The code representing the type
	 */
	public void setChatType (int typeCode) {
		setChatType(ChatType.forCode(typeCode));
	}
	
	/**
	 * Sets the type for the player's chat messages
	 * @param typeCode	The chat type
	 */
	public void setChatType (ChatType type) {
		chatType = type;
	}
	
	/**
	 * Gets the type for the player's chat messages
	 * @return
	 */
	public ChatType getChatType () {
		return chatType;
	}

	/**
	 * @return the equipment
	 */
	public Equipment getEquipment() {
		return equipment;
	}

	/**
	 * @param equipment the equipment to set
	 */
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	/**
	 * @return the packetDispatcher
	 */
	public PacketDispatcher getPacketDispatcher() {
		return packetDispatcher;
	}

	/**
	 * @param packetDispatcher the packetDispatcher to set
	 */
	public void setPacketDispatcher(PacketDispatcher packetDispatcher) {
		this.packetDispatcher = packetDispatcher;
	}
	
	/**
	 * Sends a packet to the client that opens a small dialogue box, promting to
	 * enter specified data. The {@code PendingEventManager} is then used to
	 * create a delayed event, while the client processes the input and sends it
	 * back to the server. Once packet arival, the event is fired and the input
	 * is returned. And future code in the specific block is not executed until
	 * the client and server finish the data process.
	 * @param dialogue The dialogue to display in the chatbox, promting the user toenter some sort of data.
	 * @return The input from the client.
	 */
	public String requestInput(String dialogue) {
		packetDispatcher.dispatchClientScriptVar(new ClientScriptVar(109, dialogue));
//		return (String) PendingEventManager.registerPendingEvent(new PendingEvent<String>() {
//
//			@Override
//			protected String execute() {
//				String input = getAccount().getFlag("input", "");
//				getAccount().removeFlag("input");
//				getAccount().removeFlag("recent_string_input");
//				return input;
//			}
//
//			@Override
//			protected boolean condition() {
//				return getAccount().getFlag("recent_string_input", false);
//			}
//		});
		return "ERROR: MISSING CODE - player.requestInput(...);";
	}
	
	/**
	 * Sends a packet to the client that opens a small dialogue box, prompting to
	 * enter specified data. The {@code PendingEventManager} is then used to
	 * create a delayed event, while the client processes the input and sends it
	 * back to the server. Once packet arrival, the event is fired and the input
	 * is returned. And future code in the specific block is not executed until
	 * the client and server finish the data process.
	 * @param dialogue The dialogue to display in the chatbox, prompting the user to enter some sort of data.
	 * @return The input from the client.
	 */
	public int requestIntegerInput(String dialogue) {
		packetDispatcher.dispatchClientScriptVar(new ClientScriptVar(108, dialogue));
//		return (int) PendingEventManager.registerPendingEvent(new PendingEvent<Integer>() {
//
//			@Override
//			protected Integer execute() {
//				int input = getAccount().getFlag("input", -1);
//				getAccount().removeFlag("input");
//				getAccount().removeFlag("recent_int_input");
//				return input;
//			}
//
//			@Override
//			protected boolean condition() {
//				return getAccount().getFlag("recent_int_input", false);
//
//			}
//		});
//	}
		return -1;
  }
}