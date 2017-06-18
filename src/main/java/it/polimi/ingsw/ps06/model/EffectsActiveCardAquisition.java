package it.polimi.ingsw.ps06.model;

import java.util.Observable;

public class EffectsActiveCardAquisition extends EffectsActive {

	private int bonusDiceValue;
	
	public EffectsActiveCardAquisition(Observable toStalk, int bonusDiceValue) {
		super(toStalk);
		
		this.bonusDiceValue = bonusDiceValue;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(!(o instanceof CardAcquisition)){
			throw new IllegalArgumentException();			
		}
		/*
		if ( owner.equals( ((CardAcquisition) o).getPlayer()) ) 
		{
			
			
			((Gathering) o).setValue( ((Gathering) o).getValue() + bonusDiceValue );
		}
		*/
	}
}