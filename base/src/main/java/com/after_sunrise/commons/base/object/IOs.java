package com.after_sunrise.commons.base.object;

import java.io.Closeable;
import java.io.IOException;

public final class IOs {

	private IOs() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static void closeQuietly(Closeable closeable) {

		if (closeable == null) {
			return;
		}

		try {
			closeable.close();
		} catch (IOException e) {
			// Ignore
		}

	}

}
