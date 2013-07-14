package info.after_sunrise.commons.base.object;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * @author takanori.takase
 */
public final class Sounds {

	private Sounds() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
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
