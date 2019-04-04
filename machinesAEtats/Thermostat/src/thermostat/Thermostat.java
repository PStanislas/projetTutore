package thermostat;

import com.pauware.pauware_engine._Core.*;
import com.pauware.pauware_engine._Exception.*;
import com.pauware.pauware_engine._Java_EE.*;

public class Thermostat {

    protected AbstractStatechart _eteint;
    protected AbstractStatechart _enVeille;
    protected AbstractStatechart _enChauffe;
    
    protected AbstractStatechart_monitor _thermostat_stateMachine;
    
    private void init_behavior() throws Statechart_exception {
        _eteint = new Statechart("eteint");
        _eteint.inputState();
        _enVeille = new Statechart("enVeille");
        _enChauffe = new Statechart("enChauffe");
        
        //com.pauware.pauware_view.PauWare_view pv = new com.pauware.pauware_view.PauWare_view();
        //_thermostat_stateMachine = new Statechart_monitor(_eteint.and(_enVeille).and(_enChauffe), "Thermostat", true, pv);
        
        _thermostat_stateMachine = new Statechart_monitor(_eteint.and(_enVeille).and(_enChauffe), "Thermostat", true);
    }
    
    public void start() throws Statechart_exception {
        _thermostat_stateMachine.fires("boutonMarcheON", _eteint, _enVeille);
        _thermostat_stateMachine.fires("boutonMarcheOFF", _enVeille, _eteint);
        _thermostat_stateMachine.fires("tempInfSeuil", _enVeille, _enChauffe);
        _thermostat_stateMachine.fires("tempSupSeuil", _enChauffe, _enVeille);
        _thermostat_stateMachine.fires("boutonMarcheOFF", _enChauffe, _eteint);
        
        _thermostat_stateMachine.start();
    }
    
    public void stop() throws Statechart_exception {
        _thermostat_stateMachine.stop();
    }
    
    public void Thermostat() throws Statechart_exception {
        init_behavior();
    }
    
    
    public void boutonMarcheON() throws Statechart_exception {
        _thermostat_stateMachine.run_to_completion("boutonMarcheON");
    }
    
    public void boutonMarcheOFF() throws Statechart_exception {
        _thermostat_stateMachine.run_to_completion("boutonMarcheOFF");
    }
    
    public void tempInfSeuil() throws Statechart_exception {
        _thermostat_stateMachine.run_to_completion("tempInfSeuil");
    }
    
    public void tempSupSeuil() throws Statechart_exception {
        _thermostat_stateMachine.run_to_completion("tempSupSeuil");
    }
    
    
    public static void main(String[] args) {
        try {
            Thermostat th = new Thermostat();
            th.start();
            th.stop();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
}
