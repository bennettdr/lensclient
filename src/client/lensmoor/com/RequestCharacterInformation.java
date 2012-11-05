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
	static final int CHARINFO_CONTINUE_LINE = CHARINFO_SKILLCAP_LINE + 1;
	static final int CHARINFO_PARSER_SIZE = CHARINFO_CONTINUE_LINE + 1; 
	
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
		new ParseMatch(CHARINFO_SKILLCAP_LINE, "You have ", EnumParseType.WHITESPACE),
		new ParseMatch(CHARINFO_CONTINUE_LINE, "[Hit Return to continue, Q to exit]]", EnumParseType.WHITESPACE)
	};

	private Character character;

	public RequestCharacterInformation (LensClientTelnetHelper telnetHelper, LensClientDBHelper dbHelper, Character character) {
		super(telnetHelper, dbHelper);
		this.character = character;
		setParser(parser, CHARINFO_PARSER_SIZE);
	}

	@Override
	public boolean parseLine(LensClientTelnetHelper telnetHelper, RollingBuffer buffer) {
		String raw_input_line = buffer.readString();
		String input_line = stripAnsiCodes(raw_input_line);
		String month;
		int lineCode;
		int exp;
		int exp_to_level;
		String [] quest_difficulty;

		lineCode = matchLine(input_line);
		switch (lineCode) {
			case CHARINFO_NAME_LINE:
				character.setCharacterName(getField(input_line, lineCode, 1));
				character.setLevel(getIntField(input_line, lineCode, 3));
				character.setSex(EnumSex.getSex(getField(input_line, lineCode, 5)));
				character.setRace(EnumRace.getRace(getField(input_line, lineCode, 7), false));
				break;
			case CHARINFO_SIZE_LINE:
				character.setSize(EnumSize.getSize(getField(input_line, lineCode, 1)));
				character.setSpecialization(EnumSpecialization.getSpecialization(getField(input_line, lineCode, 3)));
				character.setClan(getField(input_line, lineCode, 5));
				break;
			case CHARINFO_BIRTHDAY_LINE:
				// Strip off comma in month name
				month =  getField(input_line, lineCode, 13);
				if(month.charAt(month.length()-1) == ',') {
					month = month.substring(0, month.length() - 1);
				}
				character.setBirthday(new CalendarDate(getIntField(input_line, lineCode, 15),
						EnumCalendarMonths.getCalendarMonth(month),
						getIntField(input_line, lineCode, 7)));
				break;
			case CHARINFO_STR_LINE:
				character.setAttribute(EnumAttribute.STRENGTH, getIntField(input_line, lineCode, 1));
				character.setPractices(getIntField(input_line, lineCode, 6));
				break;
			case CHARINFO_INT_LINE:
				character.setAttribute(EnumAttribute.INTELLIGENCE, getIntField(input_line, lineCode, 1));
				character.setTrains(getIntField(input_line, lineCode, 6));
				break;
			case CHARINFO_WIS_LINE:
				character.setAttribute(EnumAttribute.WISDOM, getIntField(input_line, lineCode, 1));
				character.setAlign(EnumAlign.getAlign(getField(input_line, lineCode, 6)));
				break;
			case CHARINFO_DEX_LINE:
				character.setAttribute(EnumAttribute.DEXTERITY, getIntField(input_line, lineCode, 1));
				break;
			case CHARINFO_CON_LINE:
				character.setAttribute(EnumAttribute.CONSTITUTION, getIntField(input_line, lineCode, 1));
				break;
			case CHARINFO_CHA_LINE:
				character.setAttribute(EnumAttribute.CHARISMA, getIntField(input_line, lineCode, 1));
				break;
			case CHARINFO_EXP_LINE:
				exp =  getIntField(input_line, lineCode, 6);
				exp_to_level = getIntField(input_line, lineCode, 3);
				exp_to_level = (exp + exp_to_level)/(character.getLevel() + 1);
				character.setExperience(exp);				
				character.setExperienceToLevel(exp_to_level);				
				break;
			case CHARINFO_HP_LINE:
				character.setBaseHitPoints(getIntField(input_line, lineCode, 2));
				character.setBaseManaPoints(getIntField(input_line, lineCode, 5));
				break;
			case CHARINFO_WEALTH_LINE:
			case CHARINFO_WEIGHT_LINE:
				break;
			case CHARINFO_AGE_LINE:
				character.setAge(getIntField(input_line, lineCode, 1));
				character.setHometown(getField(input_line, lineCode, 5));
				break;				
			case CHARINFO_KILLS_LINE:
				character.setKills(getIntField(input_line, lineCode, 1));
				character.setDeaths(getIntField(input_line, lineCode, 3));
				character.setFleeAt(getIntField(input_line, lineCode, 5));
				break;				
			case CHARINFO_PK_LINE:
				character.setPlayerKills(getIntField(input_line, lineCode, 2));
				character.setPlayerDeaths(getIntField(input_line, lineCode, 5));
				break;				
			case CHARINFO_QP_LINE:
				character.setQuestPoints(getIntField(input_line, lineCode, 2));
				quest_difficulty = getField(input_line, lineCode, 6).split("\\D+");
				character.setQuestDifficulty(10 * Integer.parseInt(quest_difficulty[0]) + Integer.parseInt(quest_difficulty[1]));
				break;				
			case CHARINFO_LANGUAGES_LINE:
			case CHARINFO_ADVANTAGES_LINE:
			case CHARINFO_RP_LINE:
				break;
			case CHARINFO_SKILLCAP_LINE:
				character.setSkillCap(getIntField(input_line, lineCode, 9));
				setComplete();
				getDBHelper().SaveCharacter(character);
				break;				
			case CHARINFO_CONTINUE_LINE:
				telnetHelper.addOutputString("\n");
				break;
			default:
				buffer.insertString(raw_input_line);
				return false;
		}
		return true;
	}

	@Override
	void OutboundRequest() {
		write("score");
	}

}
