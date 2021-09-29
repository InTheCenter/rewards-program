package com.rewards.program.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains response information
 * 
 * @author Alexis
 *
 */
public class RewardsResponse {

	private List<Reward> rewards;

	public List<Reward> getRewards() {
		if (rewards == null) {
			rewards = new ArrayList<>();
		}
		return rewards;
	}

	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}

}
