package client.lensmoor.com;

public class RequestCharacterInformation extends Request {
	static final int CHARINFO_NAME_LINE = 0;
	static final int CHARINFO_SIZE_LINE = CHARINFO_NAME_LINE + 1;
	static final int CHARINFO_BIRTHDAY_LINE = CHARINFO_SIZE_LINE + 1;
	static final int CHARINFO_STR_LINE = CHARINFO_BIRTHDAY_LINE + 1;
	static final int CHARINFO_INT_LINE = CHARINFO_STR_LINE + 1;
	static final int CHARINFO_WIS_LINE = CHARINFO_INT_LINE + 1;
	static final int CHARINFO_DEX_LINE = CHARINFO_WIS_LINE + 1;
	static final int CHARINFO_CON_LINE = CHARINFO_DEX_LINE + 1;
	static final int CHARINFO_CHA_LINE = CHARINFO_CON_LINE + 1;
	static final int CHARINFO_EXP_LINE = CHARINFO_CHA_LINE + 1;
	static final int CHARINFO_HP_LINE = CHARINFO_EXP_LINE + 1;
	static final int CHARINFO_WEALTH_LINE = CHARINFO_HP_LINE + 1;
	static final int CHARINFO_WEIGHT_LINE = CHARINFO_WEALTH_LINE + 1;
	static final int CHARINFO_AGE_LINE = CHARINFO_WEIGHT_LINE + 1;
	static final int CHARINFO_KILLS_LINE = CHARINFO_AGE_LINE + 1;
	static final int CHARINFO_PK_LINE = CHARINFO_KILLS_LINE + 1;
	static final int CHARINFO_QP_LINE = CHARINFO_PK_LINE + 1;
	static final int CHARINFO_LANGUAGES_LINE = CHARINFO_QP_LINE + 1;
	static final int CHARINFO_ADVANTAGES_LINE = CHARINFO_LANGUAGES_LINE + 1;
	static final int CHARINFO_RP_LINE = CHARINFO_ADVANTAGES_LINE + 1;
	static final int CHARINFO_SKILLCAP_LINE = CHARINFO_RP_LINE + 1;
	static final int CHARINFO_PARSER_SIZE = CHARINFO_SKILLCAP_LINE + 1; 
	
	private ParseMatch parser[] = {
		new ParseMatch(CHARINFO_NAME_LINE, "Name:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_SIZE_LINE, "Size:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_BIRTHDAY_LINE, "Your birthday is", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_STR_LINE, "Str:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_INT_LINE, "Int:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_WIS_LINE, "Wis", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_DEX_LINE, "Dex:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_CON_LINE, "Con:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_CHA_LINE, "Cha:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_EXP_LINE, "Exp for level:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_HP_LINE, "Base HP:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_WEALTH_LINE, "Platinum:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_WEIGHT_LINE, "Weight Carried:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_AGE_LINE, "Age:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_KILLS_LINE, "Kills:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_PK_LINE, "Player Kills:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_QP_LINE, "Quest Points:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_LANGUAGES_LINE, "Languages known:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_ADVANTAGES_LINE, "Advantages/Disadvantages:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_RP_LINE, "RP points:", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_SKILLCAP_LINE, "You have ", EnumParseType.WHITESPACE)
	};

	private Character character;

	public RequestCharacterInformation (LensClientTelnetHelper telnetHelper, Character character) {
		super(telnetHelper);
		this.character = character;
		setParser(parser, CHARINFO_PARSER_SIZE);
	}

	@Override
	public boolean parseLine(LensClientTelnetHelper telnetHelper, RollingBuffer buffer) {
		String input_line = buffer.readString();
		int lineCode;
		int exp;
		int exp_to_level;
		String [] quest_difficulty;

		lineCode = matchLine(input_line);
		switch (lineCode) {
			case CHARINFO_NAME_LINE:
				character.setCharacterName(getField(input_line, lineCode, 2));
				character.setLevel(Integer.parseInt(getField(input_line, lineCode, 4)));
				character.setSex(EnumSex.getSex(getField(input_line, lineCode, 6)));
				character.setRace(EnumRace.getRace(getField(input_line, lineCode, 8), false));
				break;
			case CHARINFO_SIZE_LINE:
				character.setSize(EnumSize.getSize(getField(input_line, lineCode, 2)));
				character.setSpecialization(EnumSpecialization.getSpecialization(getField(input_line, lineCode, 4)));
				character.setClan(getField(input_line, lineCode, 6));
				break;
			case CHARINFO_BIRTHDAY_LINE:
				character.setBirthday(new CalendarDate(Integer.parseInt(getField(input_line, lineCode, 16)),
						EnumCalendarMonths.getCalendarMonth(getField(input_line, lineCode, 14)),
						Integer.parseInt(getField(input_line, lineCode, 8))));
				break;
			case CHARINFO_STR_LINE:
				character.setAttribute(EnumAttribute.STRENGTH, Integer.parseInt(getField(input_line, lineCode, 2)));
				character.setPractices(Integer.parseInt(getField(input_line, lineCode, 7)));
				break;
			case CHARINFO_INT_LINE:
				character.setAttribute(EnumAttribute.INTELLIGENCE, Integer.parseInt(getField(input_line, lineCode, 2)));
				character.setTrains(Integer.parseInt(getField(input_line, lineCode, 7)));
				break;
			case CHARINFO_WIS_LINE:
				character.setAttribute(EnumAttribute.WISDOM, Integer.parseInt(getField(input_line, lineCode, 2)));
				character.setAlign(EnumAlign.getAlign(getField(input_line, lineCode, 7)));
				break;
			case CHARINFO_DEX_LINE:
				character.setAttribute(EnumAttribute.DEXTERITY, Integer.parseInt(getField(input_line, lineCode, 2)));
				break;
			case CHARINFO_CON_LINE:
				character.setAttribute(EnumAttribute.CONSTITUTION, Integer.parseInt(getField(input_line, lineCode, 2)));
				break;
			case CHARINFO_CHA_LINE:
				character.setAttribute(EnumAttribute.CHARISMA, Integer.parseInt(getField(input_line, lineCode, 2)));
				break;
			case CHARINFO_EXP_LINE:
				exp =  Integer.parseInt(getField(input_line, lineCode, 7));
				exp_to_level = Integer.parseInt(getField(input_line, lineCode, 7));
				exp_to_level = (exp - exp_to_level)/character.getLevel();
				character.setExperience(exp);				
				character.setExperienceToLevel(exp_to_level);				
				break;
			case CHARINFO_HP_LINE:
				character.setBaseHitPoints(Integer.parseInt(getField(input_line, lineCode, 3)));
				character.setBaseManaPoints(Integer.parseInt(getField(input_line, lineCode, 6)));
				break;
			case CHARINFO_WEALTH_LINE:
			case CHARINFO_WEIGHT_LINE:
				break;
			case CHARINFO_AGE_LINE:
				character.setAge(Integer.parseInt(getField(input_line, lineCode, 2)));
				character.setHometown(getField(input_line, lineCode, 2));
				break;				
			case CHARINFO_KILLS_LINE:
				character.setKills(Integer.parseInt(getField(input_line, lineCode, 2)));
				character.setDeaths(Integer.parseInt(getField(input_line, lineCode, 4)));
				character.setFleeAt(Integer.parseInt(getField(input_line, lineCode, 6)));
				break;				
			case CHARINFO_PK_LINE:
				character.setPlayerKills(Integer.parseInt(getField(input_line, lineCode, 3)));
				character.setPlayerDeaths(Integer.parseInt(getField(input_line, lineCode, 6)));
				break;				
			case CHARINFO_QP_LINE:
				character.setQuestPoints(Integer.parseInt(getField(input_line, lineCode, 3)));
				quest_difficulty = getField(input_line, lineCode, 7).split(".");
				character.setQuestDifficulty(10 * Integer.parseInt(quest_difficulty[0]) + Integer.parseInt(quest_difficulty[1]));
				break;				
			case CHARINFO_LANGUAGES_LINE:
			case CHARINFO_ADVANTAGES_LINE:
			case CHARINFO_RP_LINE:
				break;				
			case CHARINFO_SKILLCAP_LINE:
				character.setSkillCap(Integer.parseInt(getField(input_line, lineCode, 10)));
				setComplete();
				break;				
			default:
				buffer.insertString(input_line);
				return false;
		}
		return true;
	}

	@Override
	void OutboundRequest() {
		write("score");
	}

}
