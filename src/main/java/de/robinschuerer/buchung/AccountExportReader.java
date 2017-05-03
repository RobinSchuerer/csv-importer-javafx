package de.robinschuerer.buchung;

import java.io.File;
import java.util.List;

import de.robinschuerer.buchung.ui.AccountMovementDto;

public interface AccountExportReader {

    List<AccountMovementDto> importData(File file);

    String getType();
}
