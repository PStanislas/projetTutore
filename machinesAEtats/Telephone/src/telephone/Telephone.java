package telephone;

import com.pauware.pauware_engine._Core.*;
import com.pauware.pauware_engine._Exception.*;
import com.pauware.pauware_engine._Java_EE.*;

public class Telephone {

    protected AbstractStatechart _active;
    protected AbstractStatechart _idle;
    protected AbstractStatechart _dialtone;
    protected AbstractStatechart _timeout;
    protected AbstractStatechart _dialing;
    protected AbstractStatechart _invalid;
    protected AbstractStatechart _connecting;
    protected AbstractStatechart _ringing;
    protected AbstractStatechart _busy;
    protected AbstractStatechart _talking;
    protected AbstractStatechart _free;

    protected AbstractStatechart_monitor _telephone_stateMachine;

    private void init_behavior() throws Statechart_exception {
        _idle = new Statechart("Idle");
        _dialtone = new Statechart("Dialtone");
        _dialtone.inputState();
        _dialtone.doActivity(this, "play dial tone");
        _timeout = new Statechart("Timeout");
        _timeout.doActivity(this, "play message");
        _dialing = new Statechart("Dialing");
        _invalid = new Statechart("Invalid");
        _invalid.doActivity(this, "play message");
        _connecting = new Statechart("Connecting");
        _ringing = new Statechart("Ringing");
        _busy = new Statechart("Busy");
        _busy.doActivity(this, "play busy tone");
        _talking = new Statechart("Talking");
        _free = new Statechart("Free");

        _active = _dialtone.xor(_timeout).xor(_dialing).xor(_invalid).xor(_connecting).xor(_ringing).xor(_busy).xor(_talking).xor(_free).name("Active");
        
        //com.pauware.pauware_view.PauWare_view pv = new com.pauware.pauware_view.PauWare_view();
        //_telephone_stateMachine = new Statechart_monitor(_active.xor(_idle), "Telephone", true, pv);
            
        _telephone_stateMachine = new Statechart_monitor(_active.xor(_idle), "Telephone", true);
    }

    public void start() throws Statechart_exception {
        _telephone_stateMachine.fires("idleToActive", _idle, _active, true, this, "lift receiver /get dialton");
        _telephone_stateMachine.fires("activeToIdle", _active, _idle, true, this, "caller hangs up /disconnect");
        _telephone_stateMachine.fires("dialtonToTimeout", _dialtone, _timeout, true, this, "after (15 sec.)");
        _telephone_stateMachine.fires("dialtonToDialing", _dialtone, _dialing, true, this, "dial digit(n)");
        _telephone_stateMachine.fires("dialingToTimeout", _dialing, _timeout, true, this, "after (15 sec.)");  
        _telephone_stateMachine.fires("dialingToDialing", _dialing, _dialing, true, this, "dial digit(n) [invalid]");
        _telephone_stateMachine.fires("dialingToConnecting", _dialing, _connecting, true, this, "dial digit(n) [valid] /connect");
        _telephone_stateMachine.fires("dialingToInvalid", _dialing, _invalid);
        _telephone_stateMachine.fires("connectingToRinging", _connecting, _ringing, true, this, "connected");
        _telephone_stateMachine.fires("connectingToBusy", _connecting, _busy, true, this, "busy");
        _telephone_stateMachine.fires("ringingToTalking", _ringing, _talking, true, this, "callee answer");
        _telephone_stateMachine.fires("talkingToFree", _talking, _free, true, this, "callee hangs up");
        _telephone_stateMachine.fires("freeToTalking", _free, _talking, true, this, "callee answer");

        _telephone_stateMachine.start();
    }
    
    public void stop() throws Statechart_exception {
        _telephone_stateMachine.stop();
    }
    
    public Telephone() throws Statechart_exception {
        init_behavior();
    }
    
    public void idleToActive() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("idleToActive");
    }
    
    public void activeToIdle() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("activeToIdle");
    }
    
    public void dialtonToTimeout() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("dialtonToTimeout");
    }
    
    public void dialtonToDialing() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("dialtonToDialing");
    }

    public void dialingToTimeout() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("dialingToTimeout");
    }
    
     public void dialingToDialing() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("dialingToDialing");
    }
    
    public void dialingToConnecting() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("dialingToConnecting");
    }
    
    public void dialingToInvalid() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("dialingToInvalid");
    }
    
    public void connectingToRinging() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("connectingToRinging");
    }   

    public void connectingToBusy() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("connectingToBusy");
    }
    
    public void ringingToTalking() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("ringingToTalking");
    }
    
    public void talkingToFree() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("talkingToFree");
    }

    public void freeToTalking() throws Statechart_exception {
        _telephone_stateMachine.run_to_completion("freeToTalking");
    }

    public static void main(String[] args) {
        try {
            Telephone te = new Telephone();
            te.start();
            te.stop();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
}
