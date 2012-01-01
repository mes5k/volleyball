
// ----------------------------------------------------------------------
//  This file is part of volleyball.
//
//  volleyball is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  volleyball is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with volleyball.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright (C) 2011 Michael E. Smoot
//-----------------------------------------------------------------------

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
