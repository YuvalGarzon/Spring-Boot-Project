package demo.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimulationController {

    private final TcpListenerService tcpListenerService;

    @Autowired
    public SimulationController(TcpListenerService tcpListenerService) {
        this.tcpListenerService = tcpListenerService;
    }

//    public void startSimulationManually() {
//        tcpListenerService.startSimulation();
//    }

    public void stopSimulationManually() {
        tcpListenerService.stopSimulation();
    }

}