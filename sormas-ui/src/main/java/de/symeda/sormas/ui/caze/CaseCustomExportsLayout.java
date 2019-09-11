package de.symeda.sormas.ui.caze;

import java.util.function.Consumer;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.symeda.sormas.api.i18n.Captions;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.i18n.Strings;
import de.symeda.sormas.api.importexport.ExportConfigurationDto;
import de.symeda.sormas.ui.ControllerProvider;
import de.symeda.sormas.ui.UserProvider;

@SuppressWarnings("serial")
public class CaseCustomExportsLayout extends VerticalLayout {

	private Label lblDescription;
	private Button btnNewExportConfiguration;
	private Button btnExport;
	private CaseCustomExportsGrid grid;

	public CaseCustomExportsLayout(Runnable closeCallback) {	
		lblDescription = new Label(I18nProperties.getString(Strings.infoCustomCaseExport));
		lblDescription.setWidth(100, Unit.PERCENTAGE);
		addComponent(lblDescription);

		btnNewExportConfiguration = new Button(I18nProperties.getCaption(Captions.exportNewExportConfiguration));
		btnNewExportConfiguration.setIcon(VaadinIcons.PLUS);
		btnNewExportConfiguration.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addComponent(btnNewExportConfiguration);
		setComponentAlignment(btnNewExportConfiguration, Alignment.MIDDLE_RIGHT);

		btnNewExportConfiguration.addClickListener(e -> {
			ControllerProvider.getCaseController().openEditExportConfigurationWindow(grid, null);
		});

		grid = new CaseCustomExportsGrid(UserProvider.getCurrent().getUuid());
		grid.setWidth(100, Unit.PERCENTAGE);
		addComponent(grid);
		
		HorizontalLayout buttonLayout = buildButtonLayout(closeCallback);
		addComponent(buttonLayout);
		setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
	}

	public void setSelectionChangeCallback(Consumer<Boolean> selectionChangeCallback) {
		grid.setSelectionChangeCallback(selectionChangeCallback);
	}

	private HorizontalLayout buildButtonLayout(Runnable closeCallback) {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setMargin(false);

		Button btnCancel = new Button(I18nProperties.getCaption(Captions.actionCancel));
		btnCancel.addClickListener(e -> closeCallback.run());
		buttonLayout.addComponent(btnCancel);

		btnExport = new Button(I18nProperties.getCaption(Captions.export));
		btnExport.setStyleName(ValoTheme.BUTTON_PRIMARY);
		btnExport.setEnabled(false);
		buttonLayout.addComponent(btnExport);
		
		grid.setSelectionChangeCallback((rowSelected) -> {
			btnExport.setEnabled(Boolean.TRUE == rowSelected);
		});

		return buttonLayout;
	}
	
	public Button getExportButton() {
		return btnExport;
	}
	
	public void setExportCallback(Consumer<ExportConfigurationDto> exportCallback) {
		btnExport.addClickListener(e -> exportCallback.accept(grid.getSelectedExportConfiguration()));
	}
	
}
