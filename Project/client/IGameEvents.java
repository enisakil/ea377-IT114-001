package Project.client;

import java.util.List;

import Project.common.Phase;

public interface IGameEvents extends IClientEvents {
    /**
     * Triggered when a player marks themselves ready
     * 
     * @param clientId Use -1 to reset the list
     */
    void onReceiveReady(long clientId);

    /**
     * Triggered when client receives phase update from server
     * 
     * @param phase
     */
    void onReceivePhase(Phase phase);

    

}