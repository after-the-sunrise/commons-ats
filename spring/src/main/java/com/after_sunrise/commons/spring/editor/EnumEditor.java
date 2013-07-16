package com.after_sunrise.commons.spring.editor;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;
import static java.lang.String.format;

import java.beans.PropertyEditorSupport;

/**
 * @author takanori.takase
 */
public abstract class EnumEditor<E extends Enum<E>> extends
		PropertyEditorSupport {

	private final Class<E> clazz;

	public EnumEditor(Class<E> clazz) {
		this.clazz = checkNotNull(clazz);
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		try {

			E type = Enum.valueOf(clazz, text);

			setValue(type);

		} catch (Exception e) {

			String msg = format("Invalid enum : %s#%s", clazz.getName(), text);

			throw new IllegalArgumentException(msg, e);

		}

	}

}
