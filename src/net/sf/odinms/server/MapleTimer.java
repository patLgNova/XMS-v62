package net.sf.odinms.server;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Rahul
 * A simplified type of TimerManager class composed of divided timers to handle specific tasks. <br>
 * This implementation provides distribution of work and thus improves the stability of the scheduling system as a whole.
 */
public abstract class MapleTimer {
    protected String name;
    protected AtomicInteger threadWorkerInc = new AtomicInteger(1);
    protected ScheduledThreadPoolExecutor stpe;

    /**
     * Private constructor of the abstract base class
     */
    private MapleTimer() {
    }

    /**
     * initializes the thread pool executor, "starts" the instance
     */
    public void start() {
        if (stpe != null && !stpe.isShutdown() && !stpe.isTerminated() && !stpe.isTerminating()) {
            return;
        }
        final ThreadFactory t = new ThreadFactory() {
            @Override public Thread newThread(Runnable r) {
                final Thread t = new Thread(r);
                final int inc = threadWorkerInc.getAndIncrement();
                t.setName(name + "-Worker-" + inc);
                return t;
            }
        };
        stpe = new ScheduledThreadPoolExecutor(4, t);
        stpe.setKeepAliveTime(5, TimeUnit.MINUTES);
        stpe.allowCoreThreadTimeOut(true);
        stpe.setMaximumPoolSize(8);
        stpe.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
    }

    /**
     * A wrapper class to catch exceptions when the scheduled Runnable executes.<br>
     * By default, no exception handling is implemented.
     */
    private static class LoggingSaveRunnable implements Runnable {
        Runnable r;

        public LoggingSaveRunnable(Runnable r) {
            this.r = r;
        }

        @Override
        public void run() {
            try {
                r.run();
            } catch (Throwable t) {
            }
        }
    }

    /**
     * Stops the Scheduled Thread Pool Executor from scheduling new tasks. <br>
     * Currently scheduled tasks will continue to be executed. <br>
     * In order to stop currently executing tasks, see: {@link ScheduledThreadPoolExecutor.shutDownNow()}
     */
    public void stop() {
        stpe.shutdown();
    }

    /**
     *
     * @param r The Runnable to be scheduled. <br>
     * @param repeatTime The time in milliseconds to re-execute the run() method. <br>
     * @param delay Delay in milliseconds before first execution. <br>
     * @return the ScheduledFuture<?> instance holding the scheduled Runnable.
     */
    public ScheduledFuture<?> register(Runnable r, long repeatTime, long delay) {
        return stpe.scheduleAtFixedRate(new LoggingSaveRunnable(r), delay, repeatTime, TimeUnit.MILLISECONDS);
    }

    /**
     *
     * @see #register(java.lang.Runnable, long)
     */
    public ScheduledFuture<?> register(Runnable r, long repeatTime) {
        return register(r, repeatTime, 0);
    }

    /**
     *
     * @param r The Runnable to be scheduled. <br>
     * @param delay Delay in milliseconds before execution. <br>
     * @return the ScheduledFuture<?> instance holding the scheduled Runnable.
     */
    public ScheduledFuture<?> schedule(Runnable r, long delay) {
        return stpe.schedule(new LoggingSaveRunnable(r), delay, TimeUnit.MILLISECONDS);
    }

    /**
     *
     * @param r The Runnable to be scheduled. <br>
     * @param timestamp The timestamp in milliseconds when the Runnable will be executed.<br>
     * example:<br>
     * <strong>
     *  long then = System.currentTimeMillis() + object.getTime();
     *  Runnable r = new Runnable() {. . .};<br>
     *  scheduleAtTimestamp(r, then);
     * </strong>
     * @return the ScheduledFuture<?> instance holding the scheduled Runnable.
     */
    public ScheduledFuture<?> scheduleAtTimestamp(Runnable r, long timestamp) {
        return schedule(r, timestamp - System.currentTimeMillis());
    }


    /**
     * Removes the Runnable r that was queued for execution.
     * @param r The Runnable to be removed.<br>
     * <strong>
     *  Note:<br>
     *  This method will not work for tasks already started, and is strongly discouraged as a cancellation technique.<br>
     * </strong>
     */
    public void remove(Runnable r) {
        stpe.remove(r);
    }

    /**
     * @see ScheduledThreadPoolExecutor.purge()
     */
    public void purge() {
        stpe.purge();
    }

    /**
     * A MapleTimer implementation to handle Miscellaneous server activities such as warping, etc.
     */
    public static class MiscTimer extends MapleTimer {
        private static MiscTimer instance = new MiscTimer();

        private MiscTimer() {
            super();
            this.name = "MiscTimer";
        }

        public static MiscTimer getInstance() {
            return instance;
        }
    }
    
     /**
     * A MapleTimer implementation to handle Miscellaneous server activities such as warping, etc.
     */
    public static class ClientTimer extends MapleTimer {
        private static ClientTimer instance = new ClientTimer();

        private ClientTimer() {
            super();
            this.name = "ClientTimer";
        }

        public static ClientTimer getInstance() {
            return instance;
        }
    }
    
    /**
     * A MapleTimer implementation to handle Miscellaneous server activities such as warping, etc.
     */
    public static class MountTimer extends MapleTimer {
        private static MountTimer instance = new MountTimer();

        private MountTimer() {
            super();
            this.name = "MountTimer";
        }

        public static MountTimer getInstance() {
            return instance;
        }
    }

    /**
     * A MapleTimer implementation to handle monster spawning and all monster-related server activities.
     */
    public static class MonsterTimer extends MapleTimer {
        private static MonsterTimer instance = new MonsterTimer();

        private MonsterTimer() {
            super();
            this.name = "MonsterTimer";
        }

        public static MonsterTimer getInstance() {
            return instance;
        }
    }

    /**
     * A MapleTimer implementation to handle all item-related server activities.
     */
    public static class ItemTimer extends MapleTimer {
        private static ItemTimer instance = new ItemTimer();

        private ItemTimer() {
            super();
            this.name = "ItemTimer";
        }

        public static ItemTimer getInstance() {
            return instance;
        }
    }
    
     /**
     * A MapleTimer implementation to handle all events server activities.
     */
    public static class MapTimer extends MapleTimer {
        private static MapTimer instance = new MapTimer();

        private MapTimer() {
            super();
            this.name = "MapTimer";
        }

        public static MapTimer getInstance() {
            return instance;
        }
    }
    
    /**
     * A MapleTimer implementation to handle all events server activities.
     */
    public static class EventTimer extends MapleTimer {
        private static EventTimer instance = new EventTimer();

        private EventTimer() {
            super();
            this.name = "EventTimer";
        }

        public static EventTimer getInstance() {
            return instance;
        }
    }
    
    /**
     * A MapleTimer implementation to handle all events server activities.
     */
    public static class WLCTimer extends MapleTimer {
        private static WLCTimer instance = new WLCTimer();

        private WLCTimer() {
            super();
            this.name = "WLCTimer";
        }

        public static WLCTimer getInstance() {
            return instance;
        }
    }
    
    /**
     * A MapleTimer implementation to handle all events server activities.
     */
    public static class AntiCheatTimer extends MapleTimer {
        private static AntiCheatTimer instance = new AntiCheatTimer();

        private AntiCheatTimer() {
            super();
            this.name = "AntiCheatTimer";
        }

        public static AntiCheatTimer getInstance() {
            return instance;
        }
    }
    
     /**
     * A MapleTimer implementation to handle all events server activities.
     */
    public static class NPCTimer extends MapleTimer {
        private static NPCTimer instance = new NPCTimer();

        private NPCTimer() {
            super();
            this.name = "NPCTimer";
        }

        public static NPCTimer getInstance() {
            return instance;
        }
    }
    
    /**
     * A MapleTimer implementation to handle all events server activities.
     */
    public static class CharacterTimer extends MapleTimer {
        private static CharacterTimer instance = new CharacterTimer();

        private CharacterTimer() {
            super();
            this.name = "CharacterTimer";
        }

        public static CharacterTimer getInstance() {
            return instance;
        }
    }

    /**
     * A MapleTimer implementation to handle all skill (buff, summon, etc)-related server activities.
     */
    public static class SkillTimer extends MapleTimer {
        private static SkillTimer instance = new SkillTimer();

        private SkillTimer() {
            super();
            this.name = "SkillTimer";
        }

        public static SkillTimer getInstance() {
            return instance;
        }
    }

}
