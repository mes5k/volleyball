
package com.aescon.volleyball.io;

import java.io.FileReader;
import java.io.BufferedReader;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import com.aescon.volleyball.Gym;

public class GymReader {

	public Map<String,Gym> getGyms(String fileName) {
		Map<String,Gym> gyms = new HashMap<String,Gym>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] l = line.split(",");
				String name = l[0].trim();
				String nickName = l[1].trim();
				String times = l[4].trim();
				int[] slots = genSlots(times);
				List<Set<String>> slotSets = genSlotSets(times);

				gyms.put(nickName, new Gym(name,nickName,slots,slotSets));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return gyms;
	}

	private int[] genSlots(String desc) {
		String[] t = desc.split("	");
		int[] slots = new int[t.length];
		for (int i = 0; i < t.length; i++) { 
			slots[i] = countSlots( t[i] ); 
		}
		return slots;
	}

	private int countSlots(String time) {
		int total = 0;
		for ( int i = 0; i < time.length(); i++ )
			if ( time.charAt(i) != '.' )
				total++;
		return total;
	}

	private List<Set<String>> genSlotSets(String desc) {
		String[] t = desc.split("	");
		List<Set<String>> sets = new ArrayList<Set<String>>();
		for (int i = 0; i < t.length; i++) { 
			sets.add( createSlotSet( t[i] )); 
		}
		return sets;
	}

	private Set<String> createSlotSet(String time) {
		Set<String> set = new HashSet<String>();	
		for ( int i = 0; i < time.length(); i++ )
			if ( time.charAt(i) != '.' )
				set.add(time.substring(i,i+1));
		return set;
	}
}
