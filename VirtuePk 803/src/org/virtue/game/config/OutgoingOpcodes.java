/*
 * This file is part of RS3Emulator.
 *
 * RS3Emulator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RS3Emulator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RS3Emulator.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.virtue.game.config;

/**
 * RS3Emulator
 * OutgoingOpcode.java
 * 4/04/2014
 * @author Sundays211
 */
public class OutgoingOpcodes {
	
	//Configuration related
	public static final int LARGE_VARP_PACKET = 153;//803
	public static final int SMALL_VARP_PACKET = 96;//803
	public static final int LARGE_CS2_VARP_PACKET = 154;//803
	public static final int SMALL_CS2_VARP_PACKET = 7;//803
	
	//Item containers
	public static final int ITEMS_PACKET = 130;//803
	public static final int UPDATE_ITEMS_PACKET = 5;//803
	
	//Logout
	public static final int LOBBY_LOGOUT_PACKET = 69;//803
	public static final int FULL_LOGOUT_PACKET = 62;//803
	
	//Game scene related
	public static final int STATIC_MAP_REGION_PACKET = 77;//803
	public static final int DYNAMIC_MAP_REGION_PACKET = 31;//803
	public static final int MINIMAP_FLAG_PACKET = 138;//803
	public static final int PLAYER_UPDATE_PACKET = 44;//803
	public static final int NPC_UPDATE_PACKET = 27;//803
	public static final int LARGE_NPC_UPDATE_PACKET = 150;//803
	
	//World tile related
	public static final int ADD_GROUND_ITEM = 149;//803
	public static final int REMOVE_GROUND_ITEM_PACKET = 110;//803
	public static final int PROJECTILE_PACKET = 90;//803
	public static final int WORLD_TILE_PACKET = 122;//803
	public static final int UPDATE_WORLD_TILE_PACKET = 107;//803 - Basically an all-in-one world tile update packet.
	public static final int TILE_MESSAGE_PACKET = 45;//803
	public static final int SPAWN_OBJECT_PACKET = 111;//803
	public static final int DESTROY_OBJECT_PACKET = 114;//803
	
	//Miscellaneous
	public static final int PLAYER_OPTION_PACKET = 3;//803
	public static final int RUN_ENERGY_PACKET = 94;//803
	public static final int SKILL_DATA_PACKET = 4;//803
	public static final int KEEP_ALIVE_PACKET = 155;//803
	public static final int WORLD_LIST_PACKET = 98;//803
	public static final int SYSTEM_UPDATE_PACKET = 89;//803
	
	//Interface-related
	public static final int INTERFACE_PACKET = 49;//803
	public static final int INTERFACE_SETTINGS_PACKET = 134;//803
	public static final int WINDOW_PANE_PACKET = 76;//803
	public static final int RUN_CS2_PACKET = 167;//803
	public static final int RESET_INTERFACE_INFORMATION = 35;//803
	
	//Messages
	public static final int GAME_MESSAGE_PACKET = 137;//803
	public static final int PUBLIC_MESSAGE_PACKET = 158;//803
	public static final int INCOMMING_PRIVATE_MESSAGE_PACKET = 116;//803
	public static final int OUTGOING_PRIVATE_MESSAGE_PACKET = 38;//803
	public static final int FRIENDS_CHAT_MESSAGE_PACKET = 85;//803
	
	//Chat-related
	public static final int UNLOCK_FRIENDS_LIST = 18;//803
	public static final int ONLINE_STATUS_PACKET = 43;//803
	public static final int TRADE_PUBLIC_STATUS_PACKET = 128;//803
	public static final int FRIENDS_PACKET = 56;//803
	public static final int IGNORES_PACKET = 68;//803
	public static final int FRIENDS_CHANNEL_PACKET = 8;//803
	public static final int FRIENDS_CHANNEL_UPDATE_PACKET = 119;//803
	public static final int CLAN_CHANNEL_PACKET = 47;//795
	
}
