package it.polimi.ingsw.ps06.model.events;

import java.io.IOException;

import it.polimi.ingsw.ps06.Client;
import it.polimi.ingsw.ps06.controller.BoardController;
import it.polimi.ingsw.ps06.controller.RoomController;
import it.polimi.ingsw.ps06.model.messages.BoardReady;

public class EventParser implements EventVisitor {
	
	@Override
	public void visit(EventClose eventClose) {
		//Client.getInstance().
		System.exit(0);
	}

	@Override
	public void visit(StoryBoard2Room storyboard) {
		
		Client.getInstance().deleteAllObservers();
		
		RoomController controller = new RoomController(Client.getInstance() ,storyboard.getView());
		
		controller.addNewObserver(Client.getInstance());
		storyboard.getView().addNewObserver(controller);
		Client.getInstance().addNewObserver(controller);
		try {storyboard.getView().show();} catch (IOException e) {e.printStackTrace();}
	}

	@Override
	public void visit(StoryBoard2Board storyboard) {
		
		Client.getInstance().deleteAllObservers();
		
		BoardController controller = new BoardController(Client.getInstance(), storyboard.getView());
		
		controller.addNewObserver(Client.getInstance());
		storyboard.getView().addNewObserver(controller);
		Client.getInstance().addNewObserver(controller);
		try {storyboard.getView().show();} catch (IOException e) {e.printStackTrace();}	
	}

	@Override
	public void visit(StoryBoard2PersonalView storyboard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EventMemberPlaced memberPlaced) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EventLeaderDiscarded leaderDiscarded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EventLeaderActivated leaderActivated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EventLeaderPlayed leaderPlayed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RoomHasLoaded roomHasLoaded) {
		
        try {
			Client.getInstance().init();
			(new Thread(Client.getInstance())).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void visit(BoardHasLoaded boardHasLoaded) {
		BoardReady br = new BoardReady();
		Client.getInstance().asyncSend(br);
	}
}