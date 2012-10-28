package client.lensmoor.com;

public class RollingBuffer {
	private int bufferChunk;
	private int lowChunk;
	private int readOffset;
	private int highChunk;
	private int writeOffset;
	private boolean fragment;
	private String bufferList[][];
	private String stringFragment;

// A rolling buffer is a list of strings that will be used
// It can handle any number of strings, allocating string 
// pointers in "buffer_chunk"-sized blocks.  It will not 
// do any allocation at start.  All strings are identified
// by chunk:offset pairs.  When the write buffer gets to
// the end of the bufferList, it resets to 0.
// When the write offset at 0 is written to, another
// chunk is  allocated.
	
	public RollingBuffer() {
		initializeRollingBuffer(1000);
	}

	public RollingBuffer(int buffer_chunk) {
		if (buffer_chunk <= 0) {
			buffer_chunk = 1000;
		}
		initializeRollingBuffer(buffer_chunk);
	}

	private void initializeRollingBuffer(int buffer_chunk) {
		// Size of buffer to allocate
		bufferChunk = buffer_chunk;
		// Current read location (starts at 0:0)
		lowChunk = 0;
		readOffset = 0;
		// We don't have any buffer allocated yet
		highChunk = -1;
		writeOffset = 0;
		bufferList = null;
		// Fragment Support
		stringFragment = "";
		fragment = false;
	}

	public String readString() {
		return readString(false);
	}

	public String peekString() {
		if((lowChunk < highChunk) || ((lowChunk == highChunk) && (readOffset < writeOffset))) {
			return bufferList[lowChunk][readOffset];
		} else if (fragment) {
			return (stringFragment);
		} else {
			return new String("");
		}
	}
	
	public String readString(boolean read_fragment) {
		String read_string;
		// Do not read fragments unless specified
		if((lowChunk < highChunk) || ((lowChunk == highChunk) && (readOffset < writeOffset))) {
			read_string = bufferList[lowChunk][readOffset];
			bufferList[lowChunk][readOffset] = null;
			readOffset++;
			if (readOffset == bufferChunk) {
				bufferList[lowChunk] = null;
				lowChunk++;
				readOffset = 0;
			}
		} else if (fragment && read_fragment) {
			read_string = stringFragment;
			stringFragment = "";
			fragment = false;
		} else {
			read_string = "";
		}
		return read_string;
	}

	public void writeStringFragment(String new_string, boolean last_fragment) {
		stringFragment = stringFragment + new_string;
		fragment = true;
		if (last_fragment) {
			fragment = false;
			writeString(stringFragment);
			stringFragment = "";
		}
	}
	
	public void writeString(String new_string) {
		String newList[][];
		int i;

		// If at the first string in the list, allocate a new chunk.
		if (writeOffset == 0) {
			highChunk++;
			newList = new String[highChunk + 1][];
			// If we have an existing list, copy it.
			for(i = 0; i < highChunk; i++) {
				newList[i] = bufferList[i];
			}
			// Set the new list
			newList[highChunk] = new String[bufferChunk];
			bufferList = newList;			
		}

		bufferList[highChunk][writeOffset++] = new_string;
		
		if (writeOffset == bufferChunk) {
			writeOffset = 0;
		}
	}
	
	public boolean isEmpty() {
		if ((lowChunk > highChunk) || ((lowChunk == highChunk) && (readOffset == writeOffset))) {
			return true;
		}
		return false;
	}
	
	public boolean hasFragment() {
		return fragment;
	}
	
	public void insertString(String new_string) {
		String newList[][];
		int i;

		if (isEmpty()) {
			writeString(new_string);
		} else {
			if (readOffset == 0) {
				readOffset = bufferChunk;
				if (lowChunk == 0) {
					highChunk++;
					newList = new String[highChunk + 1][];
					// If we have an existing list, copy it.
					for(i = 0; i + 1 < highChunk; i++) {
						newList[i + 1] = bufferList[i];
					}
					// Set the new list
					newList[lowChunk] = new String[bufferChunk];
					bufferList = newList;
				} else {
					lowChunk--;
					bufferList[lowChunk] = new String[bufferChunk];
				}
			}
			readOffset--;
			bufferList[lowChunk][readOffset] = new_string;
		}
	}
}
