/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as
 published by the Free Software Foundation version 3 as published by
 the Free Software Foundation. You may not use, modify or distribute
 this program under any other version of the GNU Affero General Public
 License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.odinms.server.quest.actions;

import net.sf.odinms.client.MapleCharacter;
import net.sf.odinms.client.MapleQuestStatus;
import net.sf.odinms.client.constants.ItemConstants;
import net.sf.odinms.provider.MapleData;
import net.sf.odinms.provider.MapleDataTool;
import net.sf.odinms.server.quest.MapleQuest;
import net.sf.odinms.server.quest.MapleQuestActionType;

/**
 *
 * @author Tyler (Twdtwd)
 */
public class PetSkillAction extends MapleQuestAction {
	int flag;
	
	public PetSkillAction(MapleQuest quest, MapleData data) {
		super(MapleQuestActionType.PETSKILL, quest);
		questID = quest.getId();
		processData(data);
	}
	
	
	@Override
	public void processData(MapleData data) {
		flag = MapleDataTool.getInt("petskill", data);
	}
	
	@Override
	public boolean check(MapleCharacter chr, Integer extSelection) {
		MapleQuestStatus status = chr.getQuest(MapleQuest.getInstance(questID));
		if(!(status.getStatus() == MapleQuestStatus.Status.NOT_STARTED && status.getForfeited() > 0))
			return false;
		
		return chr.getPet(0) != null; 
	}
	
	@Override
	public void run(MapleCharacter chr, Integer extSelection) {
            //chr.dropMessage("Please report this to forums.");
		//chr.getPet(0).setFlag((byte) ItemConstants.getFlagByInt(flag)); 
	}
} 
