var setupTask;

function init() {
    scheduleNew();
}

function scheduleNew() {
	setupTask = em.scheduleAtFixedRate("start", 1000 * 60 * 10);
}

function cancelSchedule() {
    setupTask.cancel(true);
}

function start() {
    var Message = new Array("Vote every 12 hours so we can get more players,","@commands will show a list of commands!");
    em.getChannelServer().broadcastPacket(net.sf.odinms.tools.MaplePacketCreator.sendYellowTip("[AeroStory] " + Message[Math.floor(Math.random() * Message.length)]));
}