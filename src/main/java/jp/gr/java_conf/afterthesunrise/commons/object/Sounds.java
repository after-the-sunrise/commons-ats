package jp.gr.java_conf.afterthesunrise.commons.object;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.commons.lang.ArrayUtils;

import com.google.common.io.Closeables;

/**
 * @author takanori.takase
 */
public final class Sounds {

	private Sounds() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static Clip getClip(byte[] bytes) throws IOException {

		if (ArrayUtils.isEmpty(bytes)) {
			return null;
		}

		return getClip(bytes, 0, bytes.length);

	}

	public static Clip getClip(byte[] b, int off, int len) throws IOException {

		if (ArrayUtils.isEmpty(b)) {
			return null;
		}

		Clip clip = null;

		InputStream is = null;

		AudioInputStream ais = null;

		try {

			is = new ByteArrayInputStream(b);

			ais = AudioSystem.getAudioInputStream(is);

			AudioFormat format = ais.getFormat();

			clip = AudioSystem.getClip();

			clip.open(format, b, off, len);

		} catch (Exception e) {

			if (clip != null) {
				clip.close();
			}

			throw new IOException(e);

		} finally {

			Closeables.closeQuietly(is);

			Closeables.closeQuietly(ais);

		}

		return clip;

	}

}
