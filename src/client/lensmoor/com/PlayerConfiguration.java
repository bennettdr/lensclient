package client.lensmoor.com;

import client.lensmoor.com.LensClientDBHelper;
import client.lensmoor.com.EnumOnOff;
import client.lensmoor.com.EnumColorSetting;

public class PlayerConfiguration extends LensClientSerializedObject {
	// Key
	private String playerName;
	private int configSeqNo;
	// Additional Data
	private EnumOnOff abbreviate;
	private EnumOnOff aura;
	private EnumOnOff brief;
	private EnumColorSetting color;
	private EnumOnOff combine;
	private EnumOnOff compact;
	private EnumOnOff compress;
	private String eMail;
	private EnumOnOff editor;
	private EnumYesNo followable;
	private EnumYesNo fullPK;
	private EnumYesNo gatable;
	private EnumYesNo globals;
	private EnumOnOff helper;
	private EnumOnOff lessSpam;
	private EnumYesNo lootable;
	private EnumOnOff noCeremony;
	private EnumOnOff noEffect;
	private EnumOnOff noFlags;
	private EnumOnOff noGain;
	private EnumOnOff noGames;
	private EnumOnOff noHelp;
	private EnumOnOff noInterrupt;
	private EnumOnOff noKiller;
	private EnumOnOff noMiss;
	private EnumOnOff noNotify;
	private EnumOnOff noOtherHit;
	private EnumOnOff noOtherMiss;
	private EnumOnOff noOutlaw;	
	private EnumOnOff noShortcuts;
	private EnumOnOff OOC;
	private String plan;
	private String prompt;
	private int prlines;
	private EnumOnOff saveTell;
//	private EnumScroll scroll;
	private EnumYesNo showAffects;
	private EnumYesNo summonable;
	private EnumOnOff suppress;	
	private EnumOnOff telnetGA;
	private int timeout;
	private int timezone;
	private EnumOnOff tip;
	private String title;
	private String URL;
	private EnumOnOff viewInfo;

	PlayerConfiguration(LensClientDBHelper dbHelper) {
		super(dbHelper);
	}

	public String getPlayerName() { return playerName; }
	public int getConfigSeqNo() { return configSeqNo; }
	public EnumOnOff getAbbreviate() { return abbreviate; }
	public EnumOnOff getAura() { return aura; }
	public EnumOnOff getBrief() { return brief; }
	public EnumColorSetting getColor() { return color; }
	public EnumOnOff getCombine() { return combine; }
	public EnumOnOff getCompact() { return compact; }
	public EnumOnOff getCompress() { return compress; }
	public String getEMail() { return eMail; }
	public EnumOnOff getEditor() { return editor; }
	public EnumYesNo getFollowable() { return followable; }
	public EnumYesNo getFullPK() { return fullPK; }
	public EnumYesNo getGatable() { return gatable; }
	public EnumYesNo getGlobals() { return globals; }
	public EnumOnOff getHelper() { return helper; }
	public EnumOnOff getLessSpam() { return lessSpam; }
	public EnumYesNo getLootable() { return lootable; }
	public EnumOnOff getNoCeremony() { return noCeremony; }
	public EnumOnOff getNoEffect() { return noEffect; }
	public EnumOnOff getNoFlags() { return noFlags; }
	public EnumOnOff getNoGain() { return noGain; }
	public EnumOnOff getNoGames() { return noGames; }
	public EnumOnOff getNoHelp() { return noHelp; }
	public EnumOnOff getNoInterrupt() { return noInterrupt; }
	public EnumOnOff getNoKiller() { return noKiller; }
	public EnumOnOff getNoMiss() { return noMiss; }
	public EnumOnOff getNoNotify() { return noNotify; }
	public EnumOnOff getNoOtherHit() { return noOtherHit; }
	public EnumOnOff getNoOtherMiss() { return noOtherMiss; }
	public EnumOnOff getNoOutlaw() { return noOutlaw; }
	public EnumOnOff getNoShortcuts() { return noShortcuts; }
	public EnumOnOff getOOC() { return OOC; }
	public String getPlan() { return plan; }
	public String getPrompt() { return prompt; }
	public int getPrlines() { return prlines; }
	public EnumOnOff getSaveTell() { return saveTell; }
//	public EnumSroll getScroll() { return scroll; }
	public EnumYesNo getShowAffects() { return showAffects; }
	public EnumYesNo getSummonable() { return summonable; }
	public EnumOnOff getSuppress() { return suppress; }
	public EnumOnOff getTelnetGA() { return telnetGA; }
	public int getTimeout() { return timeout; }
	public int getTimezone() { return timezone; }
	public EnumOnOff getTip() { return tip; }
	public String getTitle() { return title; }
	public String getURL() { return URL; }
	public EnumOnOff getViewInfo() { return viewInfo; }

	public void setPlayerName(String playername) { playerName = playername; }
	public void setConfigSeqNo(int config_no) { configSeqNo = config_no; }
	public void setAbbreviate(EnumOnOff abbv) { abbreviate = abbv; }
	public void setAura(EnumOnOff aura_onoff ) { aura = aura_onoff; }
	public void setBrief(EnumOnOff brief_onoff ) { brief = brief_onoff; }
	public void setColor(EnumColorSetting color_setting) { color = color_setting; }
	public void setCombine(EnumOnOff combine_onoff) { combine = combine_onoff; }
	public void EsetCompact(EnumOnOff compact_onoff) { compact = compact_onoff; }
	public void EsetCompress(EnumOnOff compress_onoff) { compress = compress_onoff; }
	public void setEMail(String email) { eMail = email; }
	public void setEditor(EnumOnOff editor_onoff) { editor = editor_onoff; }
	public void setFollowable(EnumYesNo followable_yesno) { followable = followable_yesno; }
	public void setFullPK(EnumYesNo full_pk) { fullPK = full_pk; }
	public void setGatable(EnumYesNo gatable_yesno) { gatable = gatable_yesno; }
	public void setGlobals(EnumYesNo globals_yesno) { globals = globals_yesno; }
	public void setHelper(EnumOnOff helper_onoff) { helper = helper_onoff; }
	public void setLessSpam(EnumOnOff less_spam) { lessSpam = less_spam; }
	public void setLootable(EnumYesNo lootable_yesno) { lootable = lootable_yesno; }
	public void setNoCeremony(EnumOnOff no_ceremony) { noCeremony = no_ceremony; }
	public void setNoEffect(EnumOnOff no_effect) { noEffect = no_effect; }
	public void setNoFlags(EnumOnOff no_flags) { noFlags = no_flags; }
	public void setNoGain(EnumOnOff no_gain) { noGain = no_gain; }
	public void setNoGames(EnumOnOff no_games) { noGames = no_games; }
	public void setNoHelp(EnumOnOff no_help) { noHelp = no_help; }
	public void setNoInterrupt(EnumOnOff no_interrupt) { noInterrupt = no_interrupt; }
	public void setNoKiller(EnumOnOff no_killer) { noKiller = no_killer; }
	public void setNoMiss(EnumOnOff no_miss) { noMiss = no_miss; }
	public void setNoNotify(EnumOnOff no_notify) { noNotify = no_notify; }
	public void setNoOtherHit(EnumOnOff no_other_hit) { noOtherHit = no_other_hit; }
	public void setNoOtherMiss(EnumOnOff no_other_miss) { noOtherMiss = no_other_miss; }
	public void setNoOutlaw(EnumOnOff no_outlaw) { noOutlaw = no_outlaw; }
	public void setNoShortcuts(EnumOnOff no_shortcuts) { noShortcuts = no_shortcuts; }
	public void setOOC(EnumOnOff ooc) { OOC = ooc; }
	public void setPlan(String plan_str) { plan = plan_str; }
	public void setPrompt(String prompt_str) { prompt = prompt_str; }
	public void setPrlines(int prompt_lines) { prlines = prompt_lines; }
	public void setSaveTell(EnumOnOff save_tells) { saveTell = save_tells; }
//	public void EnumSroll setScroll() { scroll = ; }
	public void setShowAffects(EnumYesNo show_affects) { showAffects = show_affects; }
	public void setSummonable(EnumYesNo summonable_yesno) { summonable = summonable_yesno; }
	public void setSuppress(EnumOnOff suppress_onoff) { suppress = suppress_onoff; }
	public void setTelnetGA(EnumOnOff telnet_ga) { telnetGA = telnet_ga; }
	public void setTimeout(int timeout_val) { timeout = timeout_val; }
	public void setTimezone(int time_zone) { timezone = time_zone; }
	public void setTip(EnumOnOff tip_onoff) { tip = tip_onoff; }
	public void setTitle(String title_str) { title = title_str; }
	public void setURL(String url) { URL = url; }
	public void setViewInfo(EnumOnOff view_info) { viewInfo = view_info; }

}
