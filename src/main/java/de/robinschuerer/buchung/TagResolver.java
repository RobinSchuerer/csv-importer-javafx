package de.robinschuerer.buchung;

import java.util.List;

import de.robinschuerer.buchung.diba.DibaExportEntry;

public interface TagResolver {

    List<String> resolveTags(final DibaExportEntry exportEntry);
}
