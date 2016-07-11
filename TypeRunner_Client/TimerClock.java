import javax.swing.*;

/**
 * The TimerClock handles the increasing of the player's timer
 * to increase again after it has been lowered.
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class TimerClock extends SwingWorker<Void, Void>{

    private Gui gui;
    static final int refreshRate = (Gui.TIMER_MAX/Gui.timerRefreshIncrement)*10;

    /**
     * creates a new timer
     * @param gui the player's gui
     */
    public TimerClock(Gui gui) {
        this.gui = gui;
    }

    /**
     * increase the timer after a certain wait time
     * @return
     * @throws Exception
     */
    @Override
    protected Void doInBackground() throws Exception {
        Thread.sleep(refreshRate);
        gui.increaseTimer();
        return null;
    }

    /**
     * creates the next timer
     */
    @Override
    protected void done() {
        super.done();
        SwingWorker tc = new TimerClock(gui);
        tc.execute();
    }
}

