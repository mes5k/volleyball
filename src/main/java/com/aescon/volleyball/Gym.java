
package com.aescon.volleyball;

import java.util.Set;
import java.util.List;

public class Gym {

	private final String name;
	private final String nickName;
	private final int[] slots;
	private final List<Set<String>> slotSets;

	public Gym(String name, String nickName, int[] slots, List<Set<String>> slotSets) {
		this.name = name;
		this.nickName = nickName;
		this.slots = slots;
		this.slotSets = slotSets;
	}

	public String getName() {
		return name;
	}

	public String getNickName() {
		return nickName;
	}

	public int[] getSlotsPerDay() {
		return slots;
	}

	public String toString() {
		return name;
	}

	public List<Set<String>> getSlotSets() {
		return slotSets;
	}
}
