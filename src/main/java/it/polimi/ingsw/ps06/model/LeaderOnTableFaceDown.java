package it.polimi.ingsw.ps06.model;

public class LeaderOnTableFaceDown implements LeaderState {

	private Leader leader;
	
	public LeaderOnTableFaceDown(Leader leader) {
		this.leader = leader;
	}
	
	@Override
	public String playLeader() {
		return "La carta Leader " + leader.title + "è già stata giocata";
	}

	@Override
	public String activateLeader() {
		return "La carta Leader " + leader.title + "è già a faccia in giù";
	}

	@Override
	public String deactivateLeader() {
		
		leader.setLeaderState( leader.getDeactivatedLeaderState() );
		return "La carta Leader " + leader.title + "è nuovamente a faccia in su";
	}

	@Override
	public String discardLeader() {
		return "La carta Leader " + leader.title + "è già stata giocata e non può più essere scartata";
	}

}
