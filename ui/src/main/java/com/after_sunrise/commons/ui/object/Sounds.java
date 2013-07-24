package com.after_sunrise.commons.ui.object;

import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.after_sunrise.commons.base.object.IOs;

/**
 * @author takanori.takase
 */
public final class Sounds {

	private Sounds() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static final String ENABLE_TOOLKIT = "sounds.toolkit.enabled";

	static AtomicReference<Toolkit> TOOLKIT;

	static Toolkit getToolkit() {

		AtomicReference<Toolkit> ref = TOOLKIT;

		if (ref == null) {

			String enabled = System.getProperty(ENABLE_TOOLKIT);

			if (Boolean.valueOf(enabled)) {
				ref = new AtomicReference<Toolkit>(Toolkit.getDefaultToolkit());
			} else {
				ref = new AtomicReference<Toolkit>(null);
			}

			TOOLKIT = ref;

		}

		return ref.get();

	}

	public static void beep() {

		Toolkit toolkit = getToolkit();

		if (toolkit == null) {
			return;
		}

		toolkit.beep();

	}

	public static Clip getClip(byte[] bytes) throws IOException {

		if (bytes == null || bytes.length == 0) {
			return null;
		}

		Clip clip = null;

		InputStream is = null;

		AudioInputStream ais = null;

		try {

			is = new ByteArrayInputStream(bytes);

			ais = AudioSystem.getAudioInputStream(is);

			AudioFormat format = ais.getFormat();

			clip = AudioSystem.getClip();

			clip.open(format, bytes, 0, bytes.length);

		} catch (Exception e) {

			closeQuietly(clip);

			throw new IOException(e);

		} finally {

			IOs.closeQuietly(is);

			IOs.closeQuietly(ais);

		}

		return clip;

	}

	public static void closeQuietly(Clip clip) {

		if (clip == null) {
			return;
		}

		clip.close();

	}

}
