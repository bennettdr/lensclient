package client.lensmoor.com;

public class RollingBuffer {
	private int bufferChunk;
	private int readOffset;
	private int writeOffset;
	private String bufferList[][];
	private int lowChunk;
	private int highChunk;

	RollingBuffer() {
		initializeRollingBuffer(1000);
	}

	RollingBuffer(int buffer_chunk) {
		if (buffer_chunk <= 0) {
			buffer_chunk = 1000;
		}
		initializeRollingBuffer(buffer_chunk);
	}

	private void initializeRollingBuffer(int buffer_chunk) {
		bufferChunk = buffer_chunk;
		lowChunk = 0;
		highChunk = 0;
		readOffset = 0;
		writeOffset = 0;
		bufferList = new String[highChunk + 1][];
		bufferList[lowChunk] = new String[bufferChunk];		
	}

	String readString() {
		String read_string;
		if((lowChunk < highChunk) || ((lowChunk == highChunk) && (readOffset < writeOffset))) {
			read_string = bufferList[lowChunk][readOffset];
			bufferList[lowChunk][readOffset] = null;
			readOffset++;
			if (readOffset == bufferChunk) {
				bufferList[lowChunk] = null;
				lowChunk++;
				readOffset = 0;
			}
		} else {
			read_string = "";
		}
		return read_string;
	}
	
	void writeString(String new_string) {
		String newList[][];
		int i;

		bufferList[highChunk][writeOffset++] = new_string;
		
		if (writeOffset == bufferChunk) {
			highChunk++;
			newList = new String[highChunk + 1][];
			for(i = 0; i < highChunk; i++) {
				newList[i] = bufferList[i];
			}
			newList[highChunk] = new String[bufferChunk];
			bufferList = newList;
			writeOffset = 0;
		}
	}
	
	boolean isEmpty() {
		if ((lowChunk == highChunk) && (readOffset == writeOffset)) {
			return true;
		}
		return false;
	}
}
