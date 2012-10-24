package client.lensmoor.com;

//Android Imports
import android.database.sqlite.*;

public class TableConfig extends Table {
	private static final int CONFIG_CHARNAME_COL = 0;
	private static final int CONFIG_SEQ_COL = CONFIG_CHARNAME_COL + 1;
	private static final int CONFIG_ABBREVIATE_COL = CONFIG_SEQ_COL + 1;
	private static final int CONFIG_ARUA_COL= CONFIG_ABBREVIATE_COL + 1;
	private static final int CONFIG_BRIEF_COL = CONFIG_ARUA_COL + 1;
	private static final int CONFIG_COLOR_COL = CONFIG_BRIEF_COL + 1;
	private static final int CONFIG_COMBINE_COL = CONFIG_COLOR_COL + 1;
	private static final int CONFIG_COMPACT_COL = CONFIG_COMBINE_COL + 1;
	private static final int CONFIG_COMPRESS_COL = CONFIG_COMPACT_COL + 1;
	private static final int CONFIG_EDITOR_COL = CONFIG_COMPRESS_COL + 1;
	private static final int CONFIG_EMAIL_COL = CONFIG_EDITOR_COL + 1;
	private static final int CONFIG_FOLLOWABLE_COL = CONFIG_EMAIL_COL + 1;
	private static final int CONFIG_FULLPK_COL = CONFIG_FOLLOWABLE_COL + 1;
	private static final int CONFIG_GATABLE_COL = CONFIG_FULLPK_COL + 1;
	private static final int CONFIG_GLOBAL_COL = CONFIG_GATABLE_COL + 1;
	private static final int CONFIG_HELPER_COL = CONFIG_GLOBAL_COL + 1;
	private static final int CONFIG_LESSSPAM_COL = CONFIG_HELPER_COL + 1;
	private static final int CONFIG_LOOTABLE_COL = CONFIG_LESSSPAM_COL + 1;
	private static final int CONFIG_NOCEREMONY_COL = CONFIG_LOOTABLE_COL + 1;
	private static final int CONFIG_NOINTERRUPT_COL = CONFIG_NOCEREMONY_COL + 1;
	private static final int CONFIG_NOKILLER_COL = CONFIG_NOINTERRUPT_COL + 1;
	private static final int CONFIG_NOMISS_COL = CONFIG_NOKILLER_COL + 1;
	private static final int CONFIG_NOOTHERHIT_COL = CONFIG_NOMISS_COL + 1;
	private static final int CONFIG_NOOTHERMISS_COL = CONFIG_NOOTHERHIT_COL + 1;
	private static final int CONFIG_NOOUTLAW_COL = CONFIG_NOOTHERMISS_COL + 1;
	private static final int CONFIG_NONOTIFY_COL = CONFIG_NOOUTLAW_COL + 1;
	private static final int CONFIG_NOSHORTCUTS_COL = CONFIG_NONOTIFY_COL + 1;
	private static final int CONFIG_OCC_COL = CONFIG_NOSHORTCUTS_COL + 1;
	private static final int CONFIG_PLAN_COL = CONFIG_OCC_COL + 1;
	private static final int CONFIG_PROMPT_COL = CONFIG_PLAN_COL + 1;
	private static final int CONFIG_PRLINES_COL = CONFIG_PROMPT_COL + 1;
	private static final int CONFIG_PUBLIC_COL = CONFIG_PRLINES_COL + 1;
	private static final int CONFIG_SAVETELL_COL = CONFIG_PUBLIC_COL + 1;
	private static final int CONFIG_SCROLL_COL = CONFIG_SAVETELL_COL + 1;
	private static final int CONFIG_SHOWAFFECTS_COL = CONFIG_SCROLL_COL + 1;
	private static final int CONFIG_SUMMONABLE_COL = CONFIG_SHOWAFFECTS_COL + 1;
	private static final int CONFIG_SUPPRESS_COL = CONFIG_SUMMONABLE_COL + 1;
	private static final int CONFIG_TELENTGA_COL = CONFIG_SUPPRESS_COL + 1;
	private static final int CONFIG_TIMEOUT_COL = CONFIG_TELENTGA_COL + 1;
	private static final int CONFIG_TIMEZONE_COL = CONFIG_TIMEOUT_COL + 1;
	private static final int CONFIG_TIP_COL = CONFIG_TIMEZONE_COL + 1;
	private static final int CONFIG_TITLE_COL = CONFIG_TIP_COL + 1;
	private static final int CONFIG_URL_COL = CONFIG_TITLE_COL + 1;
	private static final int CONFIG_VIEWINFO_COL = CONFIG_URL_COL + 1;
	private static final int CONFIG_DATE_CREATED_COL = CONFIG_VIEWINFO_COL + 1;
	private static final int CONFIG_USER_CREATED_COL = CONFIG_DATE_CREATED_COL + 1;
	private static final int CONFIG_DATE_UPDATED_COL = CONFIG_USER_CREATED_COL + 1;
	private static final int CONFIG_USER_UPDATED_COL = CONFIG_DATE_UPDATED_COL + 1;
	private static final int CONFIG_NUMBER_COLUMNS = CONFIG_USER_UPDATED_COL + 1;

	
	private static final String columnAttr[] = {
			"PlayerName", "TEXT", null,
			"ConfigSeqNo", "INTEGER", null,
			"Abbreviate", "INTEGER", null,
			"Aura", "INTEGER", null,
			"Brief", "INTEGER", null,
			"Color", "INTEGER", null,
			"Combine", "INTEGER", null,
			"Compact", "INTEGER", null,
			"Compress", "INTEGER", null,
			"Editor", "INTEGER", null,
			"Email", "TEXT", null,
			"Followable", "INTEGER", null,
			"FullPK", "INTEGER", null,
			"Gatable", "INTEGER", null,
			"Globals", "INTEGER", null,
			"Helper", "INTEGER", null,
			"LessSpam", "INTEGER", null,
			"Lootable", "INTEGER", null,
			"NoCeremony", "INTEGER", null,
			"NoEffect", "INTEGER", null,
			"NoFlags", "INTEGER", null,
			"NoGain", "NUMBER", null,
			"NoGames", "INTEGER", null,
			"NoHelp", "INTEGER", null,
			"NoInterrupt", "INTEGER", null,
			"NoKiller", "INTEGER", null,
			"NoMiss", "INTEGER", null,
			"NoOtherHit", "INTEGER", null,
			"NoOtherMiss", "INTEGER", null,
			"NoOutlaw", "INTEGER", null,
			"NoNotify", "INTEGER", null,
			"NoShortcuts", "INTEGER", null,
			"OOC", "INTEGER", null,
			"Plan", "TEXT", null,
			"Prompt", "TEXT", null,
			"Prlines", "INTEGER", null,
			"SaveTell", "INTEGER", null,
			"Scroll", "INTEGER", null,
			"ShowAffects", "INTEGER", null,
			"Summonable", "INTEGER", null,
			"Suppress", "INTEGER", null,
			"TelnetGA", "INTEGER", null,
			"Timeout", "INTEGER", null,
			"Timezone", "INTEGER", null,
			"Tip", "INTEGER", null,
			"Title", "TEXT", null,
			"URL", "TEXT", null,
			"ViewInfo", "INTEGER", null,
			"DateCreated", "INTEGER", null,
			"UserCreated", "TEXT", null,
			"DateUpdated", "INTEGER", null,
			"UserUpdated", "TEXT", null,
			null
	};
	private static final String keyList[] = {
			"PlayerName",
			"ConfigSeqNo",
			null
	};
	private static final String indexList[] [] = {
		null
	};
	private static final String tableName = "ConfigInformation";
		
	public TableConfig(LensClientDBHelper currentDBHelper) {
		super(currentDBHelper, tableName, columnAttr);
	}

	public static void onCreate (SQLiteDatabase db) {
		LensClientDBHelper.initializeTable(db, tableName, columnAttr, keyList, indexList);
	}
		
	public static void onUpgrade (SQLiteDatabase db, int newVersion, int oldVersion) {
		if (newVersion > oldVersion) {
		//	onCreate(db);
		}
	}
}
